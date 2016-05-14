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
import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;
import hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo;
import hu.bertalanadam.prt.beadando.mapper.FelhasznaloMapper;
import hu.bertalanadam.prt.beadando.mapper.TranzakcioMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.IsmetlodoSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.LekotesSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

/**
 * A tranzakciók kezelését támogató osztály.
 * Ez az osztály a {@link org.springframework.stereotype.Service Service} annotációval van ellátva,
 * azaz ez egy {@link org.springframework.stereotype.Component Component} csak specifikáltabb.
 * A {@link org.springframework.transaction.annotation.Transactional Transactional} annotáció révén
 * Az itt végzett tranzakciók bekapcsolódnak a meglévő tranzakcióba, vagy létrehoznak egyet ha
 * nincs még.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TranzakcioSzolgaltatasImpl implements TranzakcioSzolgaltatas {
	
	/**
	 * A logoláshoz szükséges {@link org.slf4j.Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(TranzakcioSzolgaltatasImpl.class);
	
	/**
	 * A penzkezelo-db modulból származó {@link hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo TranzakcioTarolo}.
	 * Ezt az adattagot az {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI injektálja be. Ezen az adattagon keresztül érhetőek el egy tranzakcióhoz
	 * szükséges adatbázis műveletek.
	 */
	@Autowired
	private TranzakcioTarolo tranzakcioTarolo;
	
	/**
	 * A tranzakciók szolgáltatásaihoz szükségünk van a felhasználók szolgáltatására,
	 * ezért ahhoz hogy felhasználós szolgáltatásokat tudjunk hívni, a
	 * {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI segítségével beinjektáljuk a
	 * {@link hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas FelhasznaloSzolgaltatas} interfészt,
	 * amin keresztül az implementáció szolgáltatásait tudjuk hívni. 
	 */
	@Autowired
	private FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;
	
	/**
	 * A tranzakciók szolgáltatásaihoz szükségünk van az ismétlődők szolgáltatására,
	 * ezért ahhoz hogy ismétlődős szolgáltatásokat tudjunk hívni, a
	 * {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI segítségével beinjektáljuk a
	 * {@link hu.bertalanadam.prt.beadando.szolgaltatas.IsmetlodoSzolgaltatas IsmetlodoSzolgaltatas} interfészt,
	 * amin keresztül az implementáció szolgáltatásait tudjuk hívni. 
	 */
	@Autowired
	private IsmetlodoSzolgaltatas ismetlodoSzolgaltatas;
	
	/**
	 * A tranzakciók szolgáltatásaihoz szükségünk van a lekötések szolgáltatására,
	 * ezért ahhoz hogy lekötéses szolgáltatásokat tudjunk hívni, a
	 * {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI segítségével beinjektáljuk a
	 * {@link hu.bertalanadam.prt.beadando.szolgaltatas.LekotesSzolgaltatas LekotesSzolgaltatas} interfészt,
	 * amin keresztül az implementáció szolgáltatásait tudjuk hívni. 
	 */
	@Autowired
	private LekotesSzolgaltatas lekotesSzolgaltatas;

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban 
	 * a metódusban a {@link hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo#save(Tranzakcio) TranzakcioTarolo.save}
	 * metódusának segítségével eltároljuk az adatbázisban a DTO-vá mappelt felhasználót, ami ezáltal egy generált azonosítót is kap.
	 * A metódus visszatérési értékként visszaadja az adatbázisban jelen lévő már ID-vel rendelkező felhasználót,
	 * amelyet a szolgáltatás eredményül visszaad átmappelve.
	 * */
	@Override
	public TranzakcioVo letrehozTranzakciot(TranzakcioVo ujTranzakcio) {
		
		Tranzakcio uj = TranzakcioMapper.toDto(ujTranzakcio);
		
		Tranzakcio letezo = tranzakcioTarolo.save(uj);
		if( letezo == null ){
			logolo.warn("Nem sikerult menteni az uj tranzakciot!");
		} else {
			logolo.debug("Sikeresen elmentesre kerult a(z) " + ujTranzakcio.getId() + " azonositoju tranzakcio!");
		}
		
		return TranzakcioMapper.toVo(letezo);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus lekérdezi az adatbázisból a paraméterül kapott felhasználó összes tranzakcióját.
	 * Ehhez a {@link hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo#findByFelhasznalo(Felhasznalo) TranzakcioTarolo.findByFelhasznalo}
	 * metódust használja amelynek a DTO-vá mappelt felhasználót adjuk paraméterül.
	 * Miután lekérdezte a felhasználó tranzakcióit, ellenőrzi az ismétlődő tranzakciókat a
	 * {@link hu.bertalanadam.prt.beadando.szolgaltatas.IsmetlodoSzolgaltatas#ismetlodoEllenorzes(FelhasznaloVo, List) IsmetlodoSzolgaltatas.ismetlodoEllenorzes}
	 * metódussal, hogy ha esetleg újabb tranzakciót kellene hozzáadni a felhasználó tranzakcióihoz, az megtörténjen.
	 * Hasonlóképpen jár el a
	 * {@link hu.bertalanadam.prt.beadando.szolgaltatas.IsmetlodoSzolgaltatas#ismetlodoEllenorzes(FelhasznaloVo, List) IsmetlodoSzolgaltatas.ismetlodoEllenorzes }
	 * esetében, így a szükséges tranzakciók hozzáadódnak ha szükséges.
	 * Ezután ismételten lekérdezzük a felhasználóhoz tartozó tranzakciókat hogy a friss tranzakciólista legyen a birtokunkban.
	 * A meglévő tranzakciós listát megszűrjük úgy, hogy a {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getKezdoIdopont() Felhasznalo.getKezdoIdopont()} és a
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo#getVegIdopont() Felhasznalo.getVegIdopont()} metódusok által visszaadott dátumok közé eső
	 * tranzakciók maradjanak csak meg.
	 * A szolgáltatás ezt a tranzakciólistát adja vissza eredményül átmappelve.
	 * */
	@Override
	public List<TranzakcioVo> felhasznaloOsszesTranzakcioja( FelhasznaloVo felhasznalo ) {
		
		// elkérjük a felhasználó összes tranzakcióját
		List<Tranzakcio> findByFelhasznalo = tranzakcioTarolo.findByFelhasznalo(FelhasznaloMapper.toDto(felhasznalo));
		if( findByFelhasznalo == null ){
			logolo.warn("A " + felhasznalo.getFelhasznalonev() + " felhasznalónevu felhasznalonak nincsenek tranzakcioi!");
		} else {
			logolo.debug("A " + felhasznalo.getFelhasznalonev() + " felhasznalónevu felhasznalonak " + findByFelhasznalo.size() + " db tranzakcioja van.");
		}
				
		// ellenőrizzük hogy van-e ismétlődője és ha van, akkor kezeljük ( új tranzakciók adódhatnak hozzá )
		ismetlodoSzolgaltatas.ismetlodoEllenorzes(felhasznalo, TranzakcioMapper.toVo(findByFelhasznalo));
		
		// ellenőrizzük a lekötéseket ( új tranzakciók adódhatnak hozzá )
		lekotesSzolgaltatas.lekotesEllenorzes(felhasznalo, TranzakcioMapper.toVo(findByFelhasznalo) );
		// itt már nincs aktiv lekötése

		// azért kell újra felhozni,mert ha közben egy ismétlődő vagy lekötés hozzáadódott, akkor legyen benne
		List<Tranzakcio> felhasznalo_tranzakcioi = tranzakcioTarolo.findByFelhasznalo( FelhasznaloMapper.toDto(felhasznalo) );
		logolo.debug("Az ellenorzesek utan:");
		if( felhasznalo_tranzakcioi == null ){
			logolo.warn("A " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalonak nincsenek tranzakcioi!");
		} else {
			logolo.debug("A " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalonak " + felhasznalo_tranzakcioi.size() + " db tranzakcioja van.");
		}
				
		// megszűrjük a tranzakciókat a megfelelő időpontra
		felhasznalo_tranzakcioi = felhasznalo_tranzakcioi.stream()
														 .filter( t -> t.getDatum().isAfter(felhasznalo.getKezdoIdopont().minusDays(1)) && 
															           t.getDatum().isBefore(felhasznalo.getVegIdopont().plusDays(1)) )
														 .collect(Collectors.toList());
		
		logolo.debug("A " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalo tranzakcioi a megadott idointervallumon belul: ");
		for (Tranzakcio tranzakcio : felhasznalo_tranzakcioi) {
			logolo.debug("Tranzakcio id: " + tranzakcio.getId());
		}
				
		return TranzakcioMapper.toVo(felhasznalo_tranzakcioi);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus frissít egy már adatbázisban jelen lévő tranzakciót.
	 * A tranzakció mivel már létezik az adatbázisban, rendelkezik azonosítóval, így a 
	 * {@link hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo#save(Tranzakcio) TranzakcioTarolo.save} metódus
	 * ahelyett hogy újból létrehozná az adatbázisban az elemet, a meglévő azonosítójú elemet frissíti.
	 * A metódusnak az átmappelt tranzakciót adjuk, amely az immár adatbázisban is frissített tranzakciót adja vissza.
	 * A szolgáltatás ezt a tranzakciót adja vissza visszamappelve.
	 * */
	@Override
	public TranzakcioVo frissitTranzakciot(TranzakcioVo tranzakcio) {
		
		Tranzakcio uj = TranzakcioMapper.toDto(tranzakcio);
		
		Tranzakcio mentett = tranzakcioTarolo.save(uj);
		if( mentett == null ){
			logolo.warn("Nem sikerult frissiteni a " + tranzakcio.getId() + " azonositoju tranzakciot!" );
		} else {
			logolo.debug("Tranzakcio sikeresen frissult: " + mentett.getId() );
		}

		return TranzakcioMapper.toVo(mentett);
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus az adatbázisból egy paraméterül megadott azonosítójú tranzakciót keres.
	 * Ezt a műveletet a {@link hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo#findOne(Long) TranzakcioTarolo.findOne}
	 * metódus segítségével hajtja végre, amely egy Long típusú azonosítót vár paraméterül.
	 * A szolgáltatás eredményül a megtalált tranzakciót adja vissza átmappelve.
	 * */
	@Override
	public TranzakcioVo keresTranzakciot( Long id ) {
		
		Tranzakcio found = tranzakcioTarolo.findOne(id);
		if( found == null ){
			logolo.warn("Nem sikerult lekerdezni a " + id + " azonositoju tranzakciot!" );
		} else {
			logolo.debug("Tranzakcio sikeresen lekerzedve: " + found.getId() );
		}
		
		return TranzakcioMapper.toVo(found);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus kitöröl az adatbázisból egy létező tranzakciót, amelyet paraméterként kap meg a metódus.
	 * Ezt a műveletet a {@link hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo#delete(Long) TranzakcioTarolo.delete}
	 * metódus segítségével hajtja végre, amely paraméterül a tranzakció azonosítóját várja.
	 * Mielőtt kitörölnénk a tranzakciót az adatbázisból, kitöröljük annak a felhasználónak a tranzakciói közül,
	 * amely birtokolja a tranzakciót.
	 * */
	@Override
	public void tranzakcioTorles( TranzakcioVo tranzakcio ) {
		
		// elkérjük a tranzakcióhoz tartozó felhasználót
		FelhasznaloVo felh = tranzakcio.getFelhasznalo();
		// annak a felhasználónak elkérjük a tranzakcióit
		
		List<TranzakcioVo> tranzakciok = felh.getTranzakciok();
		// ebből a listából kivesszük ezt a tranzakciót
		tranzakciok.remove(tranzakcio);
		
		// frissítjük a felhasználót az új listájával
		felhasznaloSzolgaltatas.frissitFelhasznalot(felh);
		
		logolo.debug("Tranzakcio torlese: " + tranzakcio.getId());
		// töröljük az aktuálist tranzakciót
		tranzakcioTarolo.delete(tranzakcio.getId());
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus megkeresi a paraméterül kapott felhasználó tranzakciói közül azt a tranzakciót amely a legkorábbi.
	 * Ezt a műveletet a 
	 * {@link hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo#findFirstByFelhasznaloOrderByDatumAsc(Felhasznalo) TranzakcioTarolo.findFirstByFelhasznaloOrderByDatumAsc}
	 * metódus segítségével hajtja végre. A metódus paraméteréül az átmappelt felhasználót adjuk, eredményül pedig a legkorábbi tranzakcióját
	 * adja.
	 * A szolgáltatás ezt a legkorábbi tranzakciót adja eredményül.
	 * */
	@Override
	public TranzakcioVo felhasznaloLegkorabbiTranzakcioja(FelhasznaloVo felhasznalo) {
		
		Felhasznalo felh = FelhasznaloMapper.toDto(felhasznalo);
		
		Tranzakcio legkorabbi = tranzakcioTarolo.findFirstByFelhasznaloOrderByDatumAsc(felh);		
		if( legkorabbi == null ){
			logolo.warn("Nem sikerult lekerdezni a " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalo legkorabbi tranzakciojat!" );
		} else {
			logolo.debug("A " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalo legkorabbi tranzakcioja: " + legkorabbi.getId());
		}
		
		return TranzakcioMapper.toVo(legkorabbi);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus megkeresi azt a tranzakcióját a paraméterül kapott felhasználónak, amely a felhasználó aktív lekötéséhez tartozik,
	 * feltéve hogy van ilyen lekötése.
	 * Ezt a műveletet úgy hajta végre hogy a felhasználó összes tranzakciója közül megszűri azokat amelykhez aktív lekötés tartozik,
	 * majd ezt a szolgáltatás eredményéül visszaadja.
	 * */
	@Override
	public TranzakcioVo felhasznaloLekotesiTranzakcioja(FelhasznaloVo felhasznalo) {
		
		List<TranzakcioVo> felh_tranzakcioi = felhasznaloOsszesTranzakcioja(felhasznalo);
		
		TranzakcioVo res = felh_tranzakcioi.stream()
						.filter( t -> t.getLekotes() != null && !t.getLekotes().isTeljesitett() )
						.findAny()
//						.get();
						.orElse(null);
		if( res == null ){
			logolo.warn("Nincs a " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalonak lekoteses tranzakcioja!");
		} else {
			logolo.debug("A " + felhasznalo.getFelhasznalonev() + " felhasznalonevu felhasznalo lekoteses tranzakcioja: " + res.getId());
		}
		
		return res;
	}
}
