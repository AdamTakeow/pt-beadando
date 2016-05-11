package hu.bertalanadam.prt.beadando.szolgaltatas.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Lekotes;
import hu.bertalanadam.prt.beadando.db.tarolo.LekotesTarolo;
import hu.bertalanadam.prt.beadando.mapper.LekotesMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.LekotesSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.LekotesVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

/**
 * A lekötések kezelését támogató osztály.
 * Ez az osztály a {@link org.springframework.stereotype.Service Service} annotációval van ellátva,
 * azaz ez egy {@link org.springframework.stereotype.Component Component} csak specifikáltabb.
 * A {@link org.springframework.transaction.annotation.Transactional Transactional} annotáció révén
 * Az itt végzett tranzakciók bekapcsolódnak a meglévő tranzakcióba, vagy létrehoznak egyet ha
 * nincs még.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class LekotesSzolgaltatasImpl implements LekotesSzolgaltatas {
	
	/**
	 * A logoláshoz szükséges {@link org.slf4j.Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(LekotesSzolgaltatasImpl.class);
	
	/**
	 * A penzkezelo-db modulból származó {@link hu.bertalanadam.prt.beadando.db.tarolo.LekotesTarolo LekotesTarolo}.
	 * Ezt az adattagot az {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI injektálja be. Ezen az adattagon keresztül érhetőek el egy lekötéshez
	 * szükséges adatbázis műveletek.
	 */
	@Autowired
	LekotesTarolo lekotesTarolo;
	
	/**
	 * A lekötések szolgáltatásaihoz szükségünk van a tranzakciók szolgáltatására,
	 * ezért ahhoz hogy tranzakciós szolgáltatásokat tudjunk hívni, a
	 * {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI segítségével beinjektáljuk a
	 * {@link hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas TranzakcioSzolgaltatas} interfészt,
	 * amin keresztül az implementáció szolgáltatásait tudjuk hívni. 
	 */
	@Autowired
	TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	/**
	 * A lekötések szolgáltatásaihoz szükségünk van a kategóriák szolgáltatására,
	 * ezért ahhoz hogy kategóriás szolgáltatásokat tudjunk hívni, a
	 * {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI segítségével beinjektáljuk a
	 * {@link hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas KategoriaSzolgaltatas} interfészt,
	 * amin keresztül az implementáció szolgáltatásait tudjuk hívni. 
	 */
	@Autowired
	KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	/**
	 * A lekötések szolgáltatásaihoz szükségünk van a felhasználók szolgáltatására,
	 * ezért ahhoz hogy felhasználós szolgáltatásokat tudjunk hívni, a
	 * {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI segítségével beinjektáljuk a
	 * {@link hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas FelhasznaloSzolgaltatas} interfészt,
	 * amin keresztül az implementáció szolgáltatásait tudjuk hívni. 
	 */
	@Autowired
	FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus eldönti hogy van-e a paraméterül kapott felhasználónak aktív (le nem járt) lekötése.
	 * Ehhez felhasználja a szintén paraméterül kapott tranzakciólistát, a felhasználó tranzakcióit.
	 * A meghatározáshoz végighalad a tranzakciókon és megnézi hogy a hozzájuk tartozó lekötések között van-e olyan
	 * amely nem null. Azonban ez nem elég ahhoz hogy el tudjuk dönteni, meg kell vizsgálni a
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes#isTeljesitett() Lekotes.isTeljesitett()} metódus eredményét is.
	 * Ha mindkét feltétel teljesül, akkor a szolgáltatás visszatérési eredménye {@code true}, ellentkező esetben
	 * {@code false}.
	 * */
	@Override
	public boolean vanAktivLekoteseAFelhasznalonak(FelhasznaloVo felhasznalo, List<TranzakcioVo> tranzakciok) {
		
		// végigmegyünk az összes tranzakción
		for (TranzakcioVo tranzakcioVo : tranzakciok) {
			// ha van lekötés hozzá
			if( tranzakcioVo.getLekotes() != null && !tranzakcioVo.getLekotes().isTeljesitett() ){
				logolo.debug("Van lekotés, ami nincs teljesitve!");
				return true;
			}
		}
		logolo.debug("Nincs lekotés, vagy mindegyik teljesitve van!");
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus létrehozza az adatbázisban a paraméterül kapott lekötést.
	 * A műveletet a {@link hu.bertalanadam.prt.beadando.db.tarolo.LekotesTarolo#save(Lekotes) LekotesTarolo.save}
	 * metódus segítségével hajtja végre, aminek paraméterül az átmappelt lekötés objektumot adjuk. A metódus visszaadja
	 * eredményül az adatbázisban immár generált ID-val rendelkező lekötés objektumot, amelyet a szolgáltatás visszamappelve
	 * ad vissza eredményül.
	 * */
	@Override
	public LekotesVo letrehozLekotest(LekotesVo lekotes) {
		Lekotes ujLekotes = LekotesMapper.toDto(lekotes);
		
		Lekotes mentett = lekotesTarolo.save(ujLekotes);
		if( mentett == null ){
			logolo.warn("Nem sikerult menteni az adatbazisba a lekotest: " + ujLekotes);
		} else {
			logolo.debug("Sikeresen letrehozva az adatbazisban a kovetkezo lekotes: " + mentett);
		}
		
		return LekotesMapper.toVo(mentett);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus frissít egy már az adatbázisban szereplő lekötést. Mivel már létezik ez az elem az
	 * adatbázisban, ezért a frissítéshez használt 
	 * {@link hu.bertalanadam.prt.beadando.db.tarolo.LekotesTarolo#save(Lekotes) LekotesTarolo.save} metódus
	 * ahelyett hogy létrehozná újra az adatbázisban az elemet, a meglévő ID alapján csak frissíti a meglévőt.
	 * A metódusnak paraméterül az átmappelt lekötést adjuk, amely eredményül az immár frissített lekötést adja vissza.
	 * Ezt a lekötés objektumot visszamappelve adja eredményül a szolgáltatás.
	 * */
	@Override
	public LekotesVo frissitLekotest(LekotesVo lekotes) {
		Lekotes ujLekotes = LekotesMapper.toDto(lekotes);
		
		Lekotes mentett = lekotesTarolo.save(ujLekotes);
		if( mentett == null ){
			logolo.warn("Nem sikerult frissiteni a lekotest: " + ujLekotes);
		} else {
			logolo.debug("Sikeresen frissitve a kovetkezo lekotes: " + mentett);
		}
		
		return LekotesMapper.toVo(mentett);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus ellenőrzi a tranzakcióhoz tartozó lekötéseket. Az ellenőrzés abból áll, hogy megnézi a lekötés létrehozásának dátumát
	 * a hozzá tartozó tranzakció segítségével, azaz a 
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio#getDatum() Tranzakcio.getDatum()} metódusát hívja annak a tranzakciónak amelyhez
	 * a lekötés tartozik. Ha az eredményül kapott dátum a mai dátummal összehasonlítva azt adja eredményül hogy a mai dátum pontosan 
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes#getFutamido() Lekotes.getFutamido()}-nyivel
	 * későbbi mint a tranzakció létrehozásának (és ezzel a lekötés kezdetének) dátuma, akkor ez azt jelenti hogy a lekötésünk lejárt,
	 * tehát be kell szúrni a tranzakciót amely elkönyveli a lekötés teljesítéséért járó összeget, amelyet a 
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes#getVarhato() Lekotes.getVarhato()} metódus eredménye határoz meg.
	 * Amennyiben ez megtörtént, be kell állítani a lekötést teljesítettre, a 
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes#setTeljesitett(boolean) Lekotes.setTeljesitett} metódussal.
	 * Előfordulhat hogy a felhasználó a lekötés lejárta előtt feltöri azt, azonban akkor a lekötés már teljesítettként szerepel
	 * az adatbázisban, ezesetben az ellenőrzés nem jár újabb tranzakció beszúrással.
	 * */
	@Override
	public void lekotesEllenorzes(FelhasznaloVo felhasznalo, List<TranzakcioVo> felh_tranzakcioi) {
		
		// bejárjuk a tranzakcióit a felhasználónak
		for (TranzakcioVo tranzakcioVo : felh_tranzakcioi) {
					
			LekotesVo lek = tranzakcioVo.getLekotes();
			// ha van lekötése
			if( lek != null && !lek.isTeljesitett() ){
				logolo.info("A " + tranzakcioVo.getId() + " azonositoju tranzakcio rendelkezik teljesitetlen lekotessel!");
				
				LocalDate ma = LocalDate.now();
						
				if ( ma.isAfter( tranzakcioVo.getDatum().plus(lek.getFutamido(), ChronoUnit.YEARS).minus(1, ChronoUnit.DAYS) ) ){
						
					// beállítjuk a lejárati dátumot
					LocalDate lejarat = tranzakcioVo.getDatum().plus(lek.getFutamido(), ChronoUnit.YEARS);
					lek.setTeljesitett(true);
						
					// lefrissítjük a lekötést mivel módosítottuk
					lek = frissitLekotest(lek);
							
					// és be kell szúrni egy új tranzakciót mert lejárt a lekötés
					TranzakcioVo ujTr = new TranzakcioVo();
					ujTr.setOsszeg(lek.getVarhato());
					ujTr.setLeiras("Lekötés lejárt");
					ujTr.setDatum(lejarat);
							
					KategoriaVo trz_kategoriaja = kategoriaSzolgaltatas.keresKategoriat("Lekötés");
							
					TranzakcioVo letezo_tr = tranzakcioSzolgaltatas.letrehozTranzakciot(ujTr);
							
					felhasznalo.getTranzakciok().add(letezo_tr);
							
							
					// beállítom a tranzakciónak a frissített kategóriát
					letezo_tr.setKategoria(trz_kategoriaja);
					// beállítom a tranzakciónak a felhasználót
					letezo_tr.setFelhasznalo(felhasznalo);
							
					tranzakcioSzolgaltatas.frissitTranzakciot(letezo_tr);
						
					felhasznaloSzolgaltatas.frissitFelhasznalot(felhasznalo);
					}
			}
		}
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus megkeresi a paraméterül kapott felhasználó egyetlen aktív lekötését, ha van ilyen neki.
	 * Ehhez elkéri a felhasználó tranzakcióit, majd leszűri azokat figyelve hogy melyik tranzakcióhoz tartozik lekötés
	 * amely létezik és nem teljesített még. A szolgáltatás eredményül adja a felhasználó egyetlen aktív lekötét, ha van neki.
	 * Egyébként {@code null}-t ad vissza.
	 * */
	@Override
	public LekotesVo felhasznaloLekotese(FelhasznaloVo felhasznalo) {
		
		LekotesVo res = felhasznalo.getTranzakciok().stream()
									.filter( t -> t.getLekotes() != null && !t.getLekotes().isTeljesitett() )
									.map( t -> t.getLekotes() )
									.findAny()
//									.get();
									.orElse(null);
		
		if( res == null ){
			logolo.warn("Nincs a " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalohoz lekotes!");
		} else {
			logolo.debug("A " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalo lekotese: " + res);
		}
		
		return res;
	}

}
