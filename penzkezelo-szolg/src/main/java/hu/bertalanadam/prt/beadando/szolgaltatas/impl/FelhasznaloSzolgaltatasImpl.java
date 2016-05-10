package hu.bertalanadam.prt.beadando.szolgaltatas.impl;



import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo;
import hu.bertalanadam.prt.beadando.mapper.FelhasznaloMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;


/**
 * A felhasználók kezelését támogató osztály.
 * Ez az osztály a {@link org.springframework.stereotype.Service Service} annotációval van ellátva,
 * azaz ez egy {@link org.springframework.stereotype.Component Component} csak specifikáltabb.
 * A {@link org.springframework.transaction.annotation.Transactional Transactional} annotáció révén
 * az itt végzett tranzakciók bekapcsolódnak a meglévő tranzakcióba, vagy létrehoznak egyet ha
 * nincs még.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FelhasznaloSzolgaltatasImpl implements FelhasznaloSzolgaltatas {

	/**
	 * A logoláshoz szükséges {@link org.slf4j.Logger Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(FelhasznaloSzolgaltatasImpl.class);
	
	/**
	 * A penzkezelo-db modulból származó {@link hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo FelhasznaloTarolo}.
	 * Ezt az adattagot az {@link org.springframework.beans.factory.annotation.Autowired} annotáció
	 * segítségével a spring DI injektálja be. Ezen az adattagon keresztül érhetőek el egy felhasználóhoz
	 * szükséges adatbázis műveletek.
	 */
	@Autowired
	private FelhasznaloTarolo felhasznaloTarolo;	

	@Override
	public FelhasznaloVo keresFelhasznalot(String felhasznalonev) {
		
		// a felhasználótároló segítségével megkeressük az adatbázisban a felhasználót
		Felhasznalo f = felhasznaloTarolo.findByFelhasznalonev(felhasznalonev);

		// logoljuk a történteket
		if( f == null ){
			logolo.warn("FelhasznaloSzolgaltatasImpl: Nem sikerült lekérdezni a(z) " + felhasznalonev + " felhasználónevű felhasználót!");			
		} else {

		}
		
		// visszaadjuk a mappelt felhasználót
		return FelhasznaloMapper.toVo( f );
	}

	@Override
	public FelhasznaloVo letrehozFelhasznalot(FelhasznaloVo felhasznalo) {

		// átmappeljük Felhasznalo-ra a Vo-t
		Felhasznalo ujfelhasznalo = FelhasznaloMapper.toDto(felhasznalo);
		
		// elmentjük az adatbázisba a felhasználót
		Felhasznalo mentett_fh = felhasznaloTarolo.save(ujfelhasznalo);
		
		// visszaadjuk a mostmár elmentett felhasználót átmappelve
		return FelhasznaloMapper.toVo(mentett_fh);
	}

	@Override
	public FelhasznaloVo frissitFelhasznalot(FelhasznaloVo felhasznalo) {
		
		// újraszámoljuk a felhasználó egyenlegét
		long egyenleg = felhasznalo.getTranzakciok().stream()
												    .filter( t -> t.getDatum().isAfter(felhasznalo.getKezdoIdopont().minusDays(1)) && 
												    			  t.getDatum().isBefore(felhasznalo.getVegIdopont().plusDays(1)) )
													.mapToLong( t -> t.getOsszeg() )
													.sum();
		
		// beállítjuk neki az új értéket
		felhasznalo.setEgyenleg(egyenleg);
		
		// újramentjük az adatbázisba, de mivel már létezik, frissíteni fogja
		Felhasznalo mentett_fh = felhasznaloTarolo.save(FelhasznaloMapper.toDto(felhasznalo));
		
		// visszaadjuk a frissített felhasználót mappelve
		return FelhasznaloMapper.toVo(mentett_fh);
	}

	@Override
	public long felhasznaloOsszesBevetele(FelhasznaloVo felhasznalo) {

		// elkérjük a tranzakcióit
		List<TranzakcioVo> tranzakciok = felhasznalo.getTranzakciok();
				
		// összeszámoljuk az összese bevételét a megadott intervallumon belül
		return tranzakciok.stream()
				.filter( t -> t.getDatum().isAfter(felhasznalo.getKezdoIdopont().minusDays(1)) && 
							  t.getDatum().isBefore(felhasznalo.getVegIdopont().plusDays(1)) )
				.mapToLong( t -> t.getOsszeg() )
				.filter( o -> o > 0 ? true : false )
				.sum();
	}

	@Override
	public long felhasznaloOsszesKiadasa(FelhasznaloVo felhasznalo) {
		 
		// elkérjük a tranzakcióit
		List<TranzakcioVo> tranzakciok = felhasznalo.getTranzakciok();
				
		// összeszámoljuk a kiadásait a megfelelő intervallumban
		return Math.abs(tranzakciok.stream()
					.filter( t -> t.getDatum().isAfter(felhasznalo.getKezdoIdopont().minusDays(1)) && 
								  t.getDatum().isBefore(felhasznalo.getVegIdopont().plusDays(1)) )
					.mapToLong( t -> t.getOsszeg() )
					.filter( o -> o < 0 ? true : false )
					.sum());
	}

	@Override
	public Map<String, Long> bevDiagramAdatokSzamitasaFelhasznalohoz(FelhasznaloVo felhasznalo) {

		List<TranzakcioVo> felh_tranzakcioi = felhasznalo.getTranzakciok();
				
		// ezeket bekategorizáljuk úgy hogy a kategóriáknak a nevei szerint összegyűjtjük az összes 
		// ilyen kategóriabeli tranzakció összegét
		Map<String, Long> res = felh_tranzakcioi.stream()
						.filter( t -> t.getOsszeg() > 0 &&
									  t.getDatum().isAfter(felhasznalo.getKezdoIdopont().minusDays(1)) && 
								      t.getDatum().isBefore(felhasznalo.getVegIdopont().plusDays(1)) )
						.collect(Collectors.groupingBy( t -> t.getKategoria().getNev(),
															Collectors.summingLong( t -> t.getOsszeg() ) )
				);
				
		return res;
	}
	
	@Override
	public Map<String, Long> kiadDiagramAdatokSzamitasaFelhasznalohoz(FelhasznaloVo felhasznalo) {

		List<TranzakcioVo> felh_tranzakcioi = felhasznalo.getTranzakciok();
				
		// ezeket bekategorizáljuk úgy hogy a kategóriáknak a nevei szerint összegyűjtjük az összes 
		// ilyen kategóriabeli tranzakció összegét
		Map<String, Long> res = felh_tranzakcioi.stream()
						.filter( t -> t.getOsszeg() < 0 &&
									  t.getDatum().isAfter(felhasznalo.getKezdoIdopont().minusDays(1)) && 
								      t.getDatum().isBefore(felhasznalo.getVegIdopont().plusDays(1)) )
						.collect(Collectors.groupingBy( t -> t.getKategoria().getNev(),
															Collectors.summingLong( t -> -1L * t.getOsszeg() ) )
				);
				
		return res;
	}

	@Override
	public Long szamolMennyitKolthetMegAFelhasznalo(FelhasznaloVo felhasznalo) {
		
		// ennyit szán a felhasználó egy hónapban kiadásra
		long kiadasra = felhasznalo.getKiadasraSzantPenz();
		// ha a felhasználó egyenlege kisebb egyenlő mint 0
		if( felhasznalo.getEgyenleg() <= 0 ){
			return 0L; // akkor semennyit nem költhet
		} else
		if( kiadasra == 0 || felhasznalo.getEgyenleg() < kiadasra){ 
			// ha nem adta meg hogy mennyit költene vagy kevesebb pénze van mint amennyit megadott
			return felhasznalo.getEgyenleg(); // akkor annyit amennyi az egyenlege
		}
		// ha meg van adva hogy mennyit szeretne költeni maximum egy hónapban
		
		// ha hó eleje van akkor a költésre szánt összeget költhetjük
		int aktualisHonap = LocalDate.now().getMonthValue();
		int aktualisEv = LocalDate.now().getYear();
			
		// a kiadásra szánt összegből levonjuk az aktuális hónap kiadásait
			
		long eztvondki = felhasznalo.getTranzakciok().stream()
				.filter( t -> t.getDatum().getMonthValue() == aktualisHonap &&
				              t.getOsszeg() < 0 &&
				              t.getDatum().getYear() == aktualisEv )
				.mapToLong( t -> t.getOsszeg() )
				.sum();
		
		if( (kiadasra - Math.abs(eztvondki)) < 0 ){
			return 0L;
		} else {
			return kiadasra - Math.abs(eztvondki);
		}
	}
}
