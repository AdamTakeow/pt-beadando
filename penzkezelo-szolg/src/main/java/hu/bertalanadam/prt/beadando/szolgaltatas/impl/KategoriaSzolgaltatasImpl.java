package hu.bertalanadam.prt.beadando.szolgaltatas.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.db.entitas.Kategoria;
import hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo;
import hu.bertalanadam.prt.beadando.mapper.FelhasznaloMapper;
import hu.bertalanadam.prt.beadando.mapper.KategoriaMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;

/**
 * A kategóriák kezelését támogató osztály.
 * Ez az osztály a {@link org.springframework.stereotype.Service Service} annotációval van ellátva,
 * azaz ez egy {@link org.springframework.stereotype.Component Component} csak specifikáltabb.
 * A {@link org.springframework.transaction.annotation.Transactional Transactional} annotáció révén
 * Az itt végzett tranzakciók bekapcsolódnak a meglévő tranzakcióba, vagy létrehoznak egyet ha
 * nincs még.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class KategoriaSzolgaltatasImpl implements KategoriaSzolgaltatas {
	
	/**
	 * A logoláshoz szükséges {@link org.slf4j.Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(KategoriaSzolgaltatasImpl.class);
	
	/**
	 * A penzkezelo-db modulból származó {@link hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo KategoriaTarolo}.
	 * Ezt az adattagot az {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI injektálja be. Ezen az adattagon keresztül érhetőek el egy kategóriához
	 * szükséges adatbázis műveletek.
	 */
	@Autowired
	KategoriaTarolo kategoriaTarolo;

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus létrehoz egy kategóriát az adatbázisban.
	 * Ehhez a {@link hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo#save(Kategoria) KategoriaTarolo.save} metódust 
	 * használja, aminek paraméterül az átmappelt elmentendő kategóriát adjuk és a létrehozott immár generált ID-vel rendelkező
	 * kategóriát adja eredményül. Ezt visszamappelve adja vissza a szolgáltatás.
	 * */
	@Override
	public KategoriaVo letrehozKategoriat(KategoriaVo ujKategoria) {
		
		Kategoria uj = KategoriaMapper.toDto(ujKategoria);
		
		Kategoria letezo = kategoriaTarolo.save(uj);
		if( letezo == null ){
			logolo.warn("Nem sikerult menteni az adatbazisba a " + ujKategoria.getNev() + " nevu kategoriat." );
		} else {
			logolo.debug("Kategoria sikeresen elmentve az adatbazisba: " + letezo.getNev() );
		}
		
		return KategoriaMapper.toVo(letezo);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus frissít egy már adatbázisban jelen lévő kategóriát.
	 * A kategória mivel már létezik az adatbázisban, rendelkezik azonosítóval, így a 
	 * {@link hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo#save(Kategoria) KategoriaTarolo.save} metódus
	 * ahelyett hogy újból létrehozná az adatbázisban az elemet, a meglévő azonosítójú elemet frissíti.
	 * A metódusnak az átmappelt kategóriát adjuk, amely az immár adatbázisban is frissített kategóriát adja vissza.
	 * A szolgáltatás ezt a kategóriát adja vissza visszamappelve.
	 * */
	@Override
	public KategoriaVo frissitKategoriat(KategoriaVo kategoria) {
		
		Kategoria kateg = KategoriaMapper.toDto(kategoria);
		
		Kategoria frissitett = kategoriaTarolo.save(kateg);
		if( frissitett == null ){
			logolo.warn("Nem sikerult frissiteni a " + kategoria.getNev() + " nevu kategoriat!" );
		} else {
			logolo.debug("Kategoria sikeresen frissult: " + frissitett.getNev() );
		}
		
		return KategoriaMapper.toVo(frissitett);
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban 
	 * a metódus egy megadott név alapján keresi az adatbázisban a kategóriát.
	 * a keresést a {@link hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo#findByNev(String) KategoriaTarolo.findByNev}
	 * metódus segítségével hajtja végre. Ennek a metódusnak az alacsonyabb szintre mappelt kategóriát adjuk, majd eredményül
	 * az adatbázisban megtalált kategóriát adja, ha megtalálja, egyébként null-t.
	 * A szolgáltatás ezt az eredményt átmappelve adja vissza.
	 * */
	@Override
	public KategoriaVo keresKategoriat(String kategoriaNev) {
		
		logolo.info("Kategoria keresese az adatbazisban ilyen nevvel: " + kategoriaNev);
		
		Kategoria found = kategoriaTarolo.findByNev(kategoriaNev);
		
		if( found == null ){
			logolo.warn("Nem talalhato kategoria ilyen nevvel: " + kategoriaNev );
		} else {
			logolo.debug("Kategoria talalat ilyen nevvel: " + kategoriaNev );
		}
		
		return KategoriaMapper.toVo(found);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus megkeresi az adatbázisban az összes kategóriát, majd visszaadja egy listában.
	 * A műveletet a {@link hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo#findAll() KategoriaTarolo.findAll()} 
	 * metódus segítségével hajtja végre. Ennek eredményeképp kapunk egy listát amely tartalmazza az összes adatbázisban szereplő
	 * kategóriát. A szolgáltatás ezen lista elemeit átmappelve egy listában adja vissza eredményül.
	 * */
	@Override
	public List<KategoriaVo> osszesKategoria() {
		logolo.info("Összes kategória lekérése");
		
		List<Kategoria> kategoriak = kategoriaTarolo.findAll();
		if( kategoriak == null ){
			logolo.warn("Nem talalhato kategoria az adatbazisban!");
		} else {
			logolo.debug( kategoriak.size() + " db kategoria talalhato az adatbazisban!" );
		}
		
		return KategoriaMapper.toVo(kategoriak);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus megkeresi az összes adatbázisban található összes (a paraméterül kapott felhasználóhoz tartozó) kategóriát.
	 * Ezt a {@link hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo#findByFelhasznaloIn(Felhasznalo) KategoriaTarolo.findByFelhasznaloIn}
	 * metódus segítségével hajtja végre. A felhasználót átmappelve adjuk a metódusnak, amely eredményül egy listát ad a felhasználóhoz 
	 * tartozó kategóriákkal benne. A szolgáltatás ezt a listát átmappelve adja eredményül vissza.
	 * */
	@Override
	public List<KategoriaVo> felhasznaloOsszesKategoriaja(FelhasznaloVo felhasznalo) {
		
		Felhasznalo felh = FelhasznaloMapper.toDto(felhasznalo);
		
		List<Kategoria> felhasznalo_kategoriai = kategoriaTarolo.findByFelhasznaloIn(felh);
		if( felhasznalo_kategoriai == null ){
			logolo.warn("Nem talalhato kategoria a kovetkezo felhasznalohoz: " + felhasznalo.getFelhasznalonev() );
		} else {
			logolo.debug( felhasznalo_kategoriai.size() + " db kategoria talhato a kovetkezo felhasznalohoz: " + felhasznalo.getFelhasznalonev() );
		}
		
		return KategoriaMapper.toVo(felhasznalo_kategoriai);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus eldönti hogy a paraméterül kapott felhasználóhoz hozzá tartozik-e a szintént paraméterül kapott kategória.
	 * Ezt úgy hajtja végre hogy elkéri a kategóriához tartozó felhasználókat, majd ezeket leszűrjük a felhasználót keresve köztük.
	 * Majd ha az eredmény egy üres lista, akkor az azt jelenti hogy nincs ilyen kategóriája a felhasználónak, azonban ha nem üres lista,
	 * akkor a felhasználó rendelkezik ezzel a kategóriával. Előbbi esetben a visszatérési érték {@code true}, utóbbiban {@code false}.
	 * */
	@Override
	public boolean vanIlyenKategoriajaAFelhasznalonak(FelhasznaloVo felhasznalo, KategoriaVo kategoria) {

		// elkérem a felhasználóit
		List<FelhasznaloVo> fhk = kategoria.getFelhasznalok();
		// megnézem hogy az éppen bejelentkezett felhasználó birtokolja-e már ezt a kategóriát
		boolean isEmpty = fhk.stream()
					    .filter( f -> f.getFelhasznalonev().equals( felhasznalo.getFelhasznalonev() ))
						.collect(Collectors.toList()).isEmpty();
		
		if( isEmpty ){
			logolo.debug("Nincs a " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalonak " + kategoria.getNev() + " nevu kategoriaja!");
		} else {
			logolo.debug("A " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalonak van " + kategoria.getNev() + " nevu kategoriaja!");
		}
		
		return isEmpty;
	}

}
