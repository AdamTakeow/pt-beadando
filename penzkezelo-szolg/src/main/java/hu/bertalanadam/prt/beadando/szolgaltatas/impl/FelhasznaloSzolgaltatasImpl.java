package hu.bertalanadam.prt.beadando.szolgaltatas.impl;



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
import hu.bertalanadam.prt.beadando.db.entitas.Kategoria;
import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;
import hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo;
import hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo;
import hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo;
import hu.bertalanadam.prt.beadando.mapper.FelhasznaloMapper;
import hu.bertalanadam.prt.beadando.mapper.KategoriaMapper;
import hu.bertalanadam.prt.beadando.mapper.TranzakcioMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.IsmetlodoSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;


/**
 * A felhasználók kezelését támogató osztály.
 * Ez az osztály a {@link org.springframework.stereotype.Service} annotációval van ellátva,
 * azaz ez egy {@link org.springframework.stereotype.Component} csak specifikáltabb.
 * A {@link org.springframework.transaction.annotation.Transactional} annotáció révén
 * Az itt végzett tranzakciók bekapcsolódnak a meglévő tranzakcióba, vagy létrehoznak egyet ha
 * nincs még.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FelhasznaloSzolgaltatasImpl implements FelhasznaloSzolgaltatas {

	/**
	 * A logoláshoz szükséges {@link org.slf4j.Logger}.
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
	
	@Autowired 
	private KategoriaTarolo kategoriaTarolo;
	
	@Autowired
	private TranzakcioTarolo tranzakcioTarolo;
	
	@Autowired
	private TranzakcioSzolgaltatas tranzakcioSzolgaltatas;

	@Autowired
	private IsmetlodoSzolgaltatas ismetlodoSzolgaltatas;
	

	@Override
	public FelhasznaloVo findByFelhasznalonev(String felhasznalonev) {
		
		// a felhasználótároló segítségével megkeressük az adatbázisban a felhasználót
		Felhasznalo f = felhasznaloTarolo.findByFelhasznalonev(felhasznalonev);

		// logoljuk a történteket
		if( f == null ){
			logolo.error("FelhasznaloSzolgaltatasImpl: Nem sikerült lekérdezni a(z) " + felhasznalonev + " felhasználónevű felhasználót!");			
		} else {

		}
		
		// visszaadjuk a mappelt felhasználót
		return FelhasznaloMapper.toVo( f );
	}

	@Override
	public FelhasznaloVo ujFelhasznaloLetrehozas(FelhasznaloVo felhasznalo) {

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
	public long osszesBevetelAFelhasznalohoz(FelhasznaloVo felhasznalo) {
		
		// megkeressük a felhasználót az adatbázisban
		Felhasznalo felh = felhasznaloTarolo.findByFelhasznalonev(felhasznalo.getFelhasznalonev()); // TODO nem szükséges felhozni újból szerintem
		
		// elkérjük a tranzakcióit
		List<Tranzakcio> tranzakciok = felh.getTranzakciok();
		
		// összeszámoljuk az összese bevételét a megadott intervallumon belül
		return tranzakciok.stream()
				.filter( t -> t.getDatum().isAfter(felhasznalo.getKezdoIdopont().minusDays(1)) && 
							  t.getDatum().isBefore(felhasznalo.getVegIdopont().plusDays(1)) )
				.mapToLong( t -> t.getOsszeg() )
				.filter( o -> o > 0 ? true : false )
				.sum();
		
	}

	@Override
	public long osszesKiadasAFelhasznalohoz(FelhasznaloVo felhasznalo) {
		
		// megkeressük a felhasználót az adatbázisban
		Felhasznalo felh = felhasznaloTarolo.findByFelhasznalonev(felhasznalo.getFelhasznalonev()); // TODO nem szükséges felhozni újból szerintem
		
		// elkérjük a tranzakcióit
		List<Tranzakcio> tranzakciok = felh.getTranzakciok();
		
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
		
		// felhozzuk az összes tranzakcióját a felhasználónak
		List<TranzakcioVo> felh_tranzakcioi = tranzakcioSzolgaltatas.osszesTranzakcioAFelhasznalohoz(felhasznalo); 
		// TODO kipróbálni adatbázis nélkül ^ felhasznalo.getTranzakciok()
		
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
		
		// felhozzuk az összes tranzakcióját a felhasználónak
		List<TranzakcioVo> felh_tranzakcioi = tranzakcioSzolgaltatas.osszesTranzakcioAFelhasznalohoz(felhasznalo);
		// TODO kipróbálni adatbázis nélkül ^ felhasznalo.getTranzakciok()
		
		// ezeket bekategorizáljuk úgy hogy a kategóriáknak a nevei szerint összegyűjtjük az összes 
		// ilyen kategóriabeli tranzakció összegét
		Map<String, Long> res = felh_tranzakcioi.stream()
						.filter( t -> t.getOsszeg() < 0 &&
									  t.getDatum().isAfter(felhasznalo.getKezdoIdopont().minusDays(1)) && 
								      t.getDatum().isBefore(felhasznalo.getVegIdopont().plusDays(1)) )
						.collect(Collectors.groupingBy( t -> t.getKategoria().getNev(),
															Collectors.summingLong( t -> t.getOsszeg() ) )
				);
		
		return res;
	}

	// TODO a felhasználónak nem kellene legyen ilyen szolgáltatása pakold át a tranzakciósba
	@Override
	public List<TranzakcioVo> osszesTranzakcioAFelhasznalohoz(FelhasznaloVo felhasznalo) {

		// elkérjük a felhasználó összes tranzakcióját
		List<Tranzakcio> findByFelhasznalo = tranzakcioTarolo.findByFelhasznalo(FelhasznaloMapper.toDto(felhasznalo));
		
		// ellenőrizzük hogy van-e ismétlődője és ha van, akkor kezeljük ( új tranzakciók adódhatnak hozzá )
		ismetlodoSzolgaltatas.ismetlodoEllenorzes(felhasznalo, TranzakcioMapper.toVo(findByFelhasznalo));

		// azért kell újra felhozni,mert ha közben egy ismétlődő hozzáadódott, akkor legyen benne
		List<Tranzakcio> felhasznalo_tranzakcioi = tranzakcioTarolo.findByFelhasznalo( FelhasznaloMapper.toDto(felhasznalo) );

		// megszűrjük a tranzakciókat a megfelelő időpontra
		felhasznalo_tranzakcioi = felhasznalo_tranzakcioi.stream()
													     .filter( t -> t.getDatum().isAfter(felhasznalo.getKezdoIdopont().minusDays(1)) && 
													                   t.getDatum().isBefore(felhasznalo.getVegIdopont().plusDays(1)) )
													     .collect(Collectors.toList());
		
		return TranzakcioMapper.toVo(felhasznalo_tranzakcioi);
	}

	// TODO nem kellene itt kategóriatárolóval operálni, dobjuk át a kategóriaszolgáltatásba és azt használjuk
	@Override
	public List<KategoriaVo> osszesKategoriaAFelhasznalohoz(FelhasznaloVo felhasznalo) {
		
		Felhasznalo felh = FelhasznaloMapper.toDto(felhasznalo);
		
		List<Kategoria> felhasznalo_kategoriai = kategoriaTarolo.findByFelhasznaloIn(felh);
		
		return KategoriaMapper.toVo(felhasznalo_kategoriai);
	}
}
