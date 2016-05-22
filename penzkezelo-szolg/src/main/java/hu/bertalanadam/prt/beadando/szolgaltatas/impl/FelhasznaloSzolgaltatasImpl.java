package hu.bertalanadam.prt.beadando.szolgaltatas.impl;



import java.time.LocalDate;
import java.util.ArrayList;
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
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
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

	/**
	 * {@inheritDoc}
	 * A metódusban a {@link hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo#findByFelhasznalonev(String) FelhasznaloTarolo.findByFelhasznalonev}
	 * segítségével lekérdezzük az adatbázisból a felhasználót a felhasználóneve alapján.
	 * Sikeres lekérdezés esetén a szolgáltatás visszaadja az átmappelt felhasználót eredményül.
	 * */
	@Override
	public FelhasznaloVo keresFelhasznalot(String felhasznalonev) {
		
		// a felhasználótároló segítségével megkeressük az adatbázisban a felhasználót
		Felhasznalo f = felhasznaloTarolo.findByFelhasznalonev(felhasznalonev);

		// logoljuk a történteket
		if( f == null ){
			logolo.warn("Nem talalhato a(z) " + felhasznalonev + " felhasznalonevu felhasznalo!");			
		} else {
			logolo.debug("A(z) " + felhasznalonev + " nevu felhasznalo sikeresen lekerdezve!");
		}
		
		// visszaadjuk a mappelt felhasználót
		return FelhasznaloMapper.toVo( f );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban 
	 * a metódusban a {@link org.springframework.data.repository.CrudRepository#save(Object) }
	 * metódusának segítségével eltároljuk az adatbázisban a DTO-vá mappelt felhasználót, ami ezáltal egy generált azonosítót is kap.
	 * A metódus visszatérési értékként visszaadja az adatbázisban jelen lévő már ID-vel rendelkező felhasználót,
	 * amelyet a szolgáltatás eredményül visszaad átmappelve.
	 * */
	@Override
	public FelhasznaloVo letrehozFelhasznalot(FelhasznaloVo felhasznalo) {
		
		felhasznalo.setEgyenleg(0L);
		felhasznalo.setKiadasraSzantPenz(0L);
		
		LocalDate innentol = LocalDate.of(1970, 1, 1);
		LocalDate idaig = LocalDate.now();
		felhasznalo.setKezdoIdopont(innentol);
		felhasznalo.setVegIdopont(idaig);
		
		felhasznalo.setTranzakciok(new ArrayList<TranzakcioVo>());
		felhasznalo.setKategoriak(new ArrayList<KategoriaVo>());

		// átmappeljük Felhasznalo-ra a Vo-t
		Felhasznalo ujfelhasznalo = FelhasznaloMapper.toDto(felhasznalo);
		
		// elmentjük az adatbázisba a felhasználót
		Felhasznalo mentett_fh = felhasznaloTarolo.save(ujfelhasznalo);
		if( mentett_fh == null ){
			logolo.warn("Nem sikerult menteni a(z) " + ujfelhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalot!");
		} else {
			logolo.debug("Sikeresen elmentesre kerult a(z) " + ujfelhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalot!");
		}
		
		// visszaadjuk a mostmár elmentett felhasználót átmappelve
		return FelhasznaloMapper.toVo(mentett_fh);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus a {@link org.springframework.data.repository.CrudRepository#save(Object) }
	 * metódussal elmentjük az adatbázisba a felhasználót, ami mivel már rendelkezik generált azonosítóval, így nem hoz
	 * létre új példányt az adatbázisban, hanem a meglévő ID-val megegyező felhasználót fogja frissíteni.
	 * Mielőtt még ez megtörténne, a friss felhasználó egyenelgét kiszámoljuk a tranzakcióiból, a tranzakciók
	 * összegeinek összegzésével. Az alkalmazás funkciójának megvalósítása érdekében ezt az összegzést
	 * csak a megadott intervallumon belül hajtjuk végre, ez a megadott intervallum pedig az adott felhasználó 
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getKezdoIdopont() Felhasznalo.getKezdoIdopont()} metódusa
	 * által visszaadott dátum valamint a {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getVegIdopont() Felhasznalo.getVegIdopont()}
	 * között lévő időintervallum.
	 * */
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
		logolo.debug("Felhasznalo frissitesenel a felhasznalo uj egyenlege: " + egyenleg);
		
		// újramentjük az adatbázisba, de mivel már létezik, frissíteni fogja
		Felhasznalo mentett_fh = felhasznaloTarolo.save(FelhasznaloMapper.toDto(felhasznalo));
		if( mentett_fh == null ){
			logolo.warn("Nem sikerult frissiteni a(z) " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalot!");
		} else {
			logolo.debug("Sikeresen frissult a(z) " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalot!");
		}
		
		// visszaadjuk a frissített felhasználót mappelve
		return FelhasznaloMapper.toVo(mentett_fh);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus elkéri a paraméterül kapott felhasználó tranzakcióit, majd ezek alapján kiszámolja a felhasználó
	 * összes megadott intervallumon belül lévő tranzakciókból származó bevételét. Ez az intervallum,
	 * amin belül számoljuk az összeget, a felhasználó
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getKezdoIdopont() Felhasznalo.getKezdoIdopont()} metódusa
	 * által visszaadott dátum valamint a {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getVegIdopont() Felhasznalo.getVegIdopont()}
	 * között lévő időintervallum.
	 * Fontos hogy itt csak azokat a tranzakciókat vesszük figyelembe, amelyek címletei pozitívak, azaz bevételekről szólnak.
	 * */
	@Override
	public long felhasznaloOsszesBevetele(FelhasznaloVo felhasznalo) {

		// elkérjük a tranzakcióit
		List<TranzakcioVo> tranzakciok = felhasznalo.getTranzakciok();
				
		// összeszámoljuk az összese bevételét a megadott intervallumon belül
		long sum = tranzakciok.stream()
				.filter( t -> t.getDatum().isAfter(felhasznalo.getKezdoIdopont().minusDays(1)) && 
							  t.getDatum().isBefore(felhasznalo.getVegIdopont().plusDays(1)) )
				.mapToLong( t -> t.getOsszeg() )
				.filter( o -> o > 0 ? true : false )
				.sum();
		
		logolo.debug("A(z) " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalo osszes bevetele: " + sum);
		
		return sum;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus elkéri a paraméterül kapott felhasználó tranzakcióit, majd ezek alapján kiszámolja a felhasználó
	 * összes megadott intervallumon belül lévő tranzakciókból származó kiadását. Ez az intervallum,
	 * amin belül számoljuk az összeget, a felhasználó
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getKezdoIdopont() Felhasznalo.getKezdoIdopont()} metódusa
	 * által visszaadott dátum valamint a {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getVegIdopont() Felhasznalo.getVegIdopont()}
	 * között lévő időintervallum.
	 * Fontos hogy itt csak azokat a tranzakciókat vesszük figyelembe, amelyek címletei negatívak, azaz kiadásokról szólnak.
	 * */
	@Override
	public long felhasznaloOsszesKiadasa(FelhasznaloVo felhasznalo) {
		 
		// elkérjük a tranzakcióit
		List<TranzakcioVo> tranzakciok = felhasznalo.getTranzakciok();
				
		// összeszámoljuk a kiadásait a megfelelő intervallumban
		long sum = Math.abs(tranzakciok.stream()
					.filter( t -> t.getDatum().isAfter(felhasznalo.getKezdoIdopont().minusDays(1)) && 
								  t.getDatum().isBefore(felhasznalo.getVegIdopont().plusDays(1)) )
					.mapToLong( t -> t.getOsszeg() )
					.filter( o -> o < 0 ? true : false )
					.sum());
		
		logolo.debug("A(z) " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalo osszes kiadasa: " + sum);
		
		return sum;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus kiszámolja azokat az adatokat, amelyekkel a bevételeket csoportosítva megjelenítő diagrammot töltjük fel.
	 * Ez a következőképpen történik: elkérjük a felhasználó összes tranzakcióját,
	 * ezek közül kiválogatjuk azokat, amelyek a felhaszáló {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getKezdoIdopont() Felhasznalo.getKezdoIdopont()} metódusa
	 * által visszaadott dátum valamint a {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getVegIdopont() Felhasznalo.getVegIdopont()}
	 * között lévő időintervallumba esnek, valamint fontos hogy ezek pozitívak, hiszen a bevételeket szeretnénk csoportosítani.
	 * Miután ez megtörtént, csoportosítjuk kategóriák szerint a tranzakciókat úgy, hogy minden kategóriához az abba a kategóriába
	 * tartozó összes bevétel összege szerepeljen. 
	 * Ezt egy olyan {@link java.util.Map Map}-ben tároljuk, amelyben a kulcsok a kategória nevei, az értékek pedig
	 * az összes ilyen kategóriába tartozó bevételek összegei.
	 * A szolgáltatás a fentebb említett immár a megfelelő adatokkal feltöltött {@link java.util.Map Map}-et adja vissza. 
	 * */
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
		
		logolo.debug("Kategoriankenti bevetelek: " + res);
				
		return res; 
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus kiszámolja azokat az adatokat, amelyekkel a kiadásokat csoportosítva megjelenítő diagrammot töltjük fel.
	 * Ez a következőképpen történik: elkérjük a felhasználó összes tranzakcióját,
	 * ezek közül kiválogatjuk azokat, amelyek a felhaszáló 
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getKezdoIdopont() Felhasznalo.getKezdoIdopont()} metódusa
	 * által visszaadott dátum valamint a {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getVegIdopont() Felhasznalo.getVegIdopont()}
	 * között lévő időintervallumba esnek, valamint fontos hogy ezek negatívak, hiszen a kiadásokat szeretnénk csoportosítani.
	 * Miután ez megtörtént, csoportosítjuk kategóriák szerint a tranzakciókat úgy, hogy minden kategóriához az abba a kategóriába
	 * tartozó összes kiadás összege szerepeljen. 
	 * Ezt egy olyan {@link java.util.Map Map}-ben tároljuk, amelyben a kulcsok a kategória nevei, az értékek pedig
	 * az összes ilyen kategóriába tartozó kiadások összegei.
	 * A szolgáltatás a fentebb említett immár a megfelelő adatokkal feltöltött {@link java.util.Map Map}-et adja vissza. 
	 * */
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
		
		logolo.debug("Kategoriankenti kiadasok: " + res );
				
		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus kiszámolja hogy a paraméterként kapott felhasználó mennyit költhet még az aktuális hónapban.
	 * Először elkérjük a felhasználónál beállított kiadásra szánt összeget a 
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getKiadasraSzantPenz() Felhasznalo.getKiadasraSzantPenz()} metódusával,
	 * majd amennyiben ez az érték 0 tehát nem állított be a felhasználó kiadásra szánt pénzt, akkor automatikusan annyit ad vissza
	 * a szolgáltatás amennyi a felhasználó egyenlege, hiszen annyit költhet amennyije van.
	 * Amennyiben a felhasználó egyenelge 0 vagy alatta van, akkor is nullát ad vissza a szolgáltatás, hiszen ha nincs pénze,
	 * akkor nem költhet belőle semennyit.
	 * Amennyiben kevesebb az egyenlege mint amennyit beállított kiadásra szántnak, akkor is annyit ad vissza a szolgáltatás
	 * amennyi a felhasználó egyenlege, az egyenlegénél nem tanácsolt(!) többet költeni.
	 * Egyébként, ha a felhasználónak több pénze van mint amennyit beállított hogy költeni szeretne az aktuális hónapban,
	 * akkor kiválogatjuk azokat a kiadásokat amelyek az aktuális hónapban történtek,
	 * majd ezeket levonva a kiadásra szánt összegből a felhasználó pontosan nyomon tudja követni, hogy ebben a hónapban
	 * a kiadásra szánt pénzéből mennyit költhet még.
	 * Megjegyzés: ez a funkció kizárólag tájékoztató jellegű!
	 * */
	@Override
	public Long szamolMennyitKolthetMegAFelhasznalo(FelhasznaloVo felhasznalo) {
		
		// ennyit szán a felhasználó egy hónapban kiadásra
		long kiadasra = felhasznalo.getKiadasraSzantPenz();
		// ha a felhasználó egyenlege kisebb egyenlő mint 0
		if( felhasznalo.getEgyenleg() <= 0 ){
			logolo.debug("A felhasznalo egyenlege 0 vagy negatív, nem kolthet tobbet.");
			return 0L; // akkor semennyit nem költhet
		} else
		if( kiadasra == 0 || felhasznalo.getEgyenleg() < kiadasra){ 
			logolo.debug("A felhasznalo egyenlege kevesebb mint amennyit kolteni szeretne, vagy nem adta meg mennyit kolthet, ezert a teljes"
					+ "egyenlege rendelkezesre all.");
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
		
		if( (kiadasra - Math.abs(eztvondki)) <= 0 ){
			logolo.debug("A felhasznalo mar nem kolthet tobbet ebben a honapban.");
			return 0L;
		} else {
			logolo.debug("A felhasznalo " + (kiadasra - Math.abs(eztvondki)) + " Ft-ot kolthet meg a honapban.");
			return kiadasra - Math.abs(eztvondki);
		}
	}
}
