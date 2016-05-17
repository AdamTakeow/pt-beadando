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

import hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo;
import hu.bertalanadam.prt.beadando.db.tarolo.IsmetlodoTarolo;
import hu.bertalanadam.prt.beadando.mapper.IsmetlodoMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.IsmetlodoSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.IsmetlodoVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

/**
 * Az ismétlődő tranzakcióhoz tartozó ismétlődők kezelését támogató osztály.
 * Ez az osztály a {@link org.springframework.stereotype.Service Service} annotációval van ellátva,
 * azaz ez egy {@link org.springframework.stereotype.Component Component} csak specifikáltabb.
 * A {@link org.springframework.transaction.annotation.Transactional Transactional} annotáció révén
 * az itt végzett tranzakciók bekapcsolódnak a meglévő tranzakcióba, vagy létrehoznak egyet ha
 * nincs még.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class IsmetlodoSzolgaltatasImpl implements IsmetlodoSzolgaltatas {
	
	/**
	 * A penzkezelo-db modulból származó {@link hu.bertalanadam.prt.beadando.db.tarolo.IsmetlodoTarolo IsmetlodoTarolo}.
	 * Ezt az adattagot az {@link org.springframework.beans.factory.annotation.Autowired} annotáció
	 * segítségével a spring DI injektálja be. Ezen az adattagon keresztül érhetőek el egy ismétlődőhöz
	 * szükséges adatbázis műveletek.
	 */
	@Autowired
	IsmetlodoTarolo ismetlodoTarolo;
	
	/**
	 * Az ismétlődők szolgáltatásaihoz szükségünk van a tranzakciók szolgáltatására,
	 * ezért ahhoz hogy tranzakciós szolgáltatásokat tudjunk hívni, a
	 * {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI segítségével beinjektáljuk a
	 * {@link hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas TranzakcioSzolgaltatas} interfészt,
	 * amin keresztül az implementáció szolgáltatásait tudjuk hívni. 
	 */
	@Autowired
	TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	/**
	 * Az ismétlődők szolgáltatásaihoz szükségünk van a felhasználó szolgáltatásra,
	 * ezért ahhoz hogy felhasználós szolgáltatásokat tudjunk hívni, a
	 * {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI segítségével beinjektáljuk a
	 * {@link hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas FelhasznaloSzolgaltatas} interfészt,
	 * amin keresztül az implementáció szolgáltatásait tudjuk hívni. 
	 */
	@Autowired
	FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;
	
	/**
	 * Az ismétlődők szolgáltatásaihoz szükségünk van a kategóriák szolgáltatásra,
	 * ezért ahhoz hogy kategóriás szolgáltatásokat tudjunk hívni, a
	 * {@link org.springframework.beans.factory.annotation.Autowired Autowired} annotáció
	 * segítségével a spring DI segítségével beinjektáljuk a
	 * {@link hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas KategoriaSzolgaltatas} interfészt,
	 * amin keresztül az implementáció szolgáltatásait tudjuk hívni. 
	 */
	@Autowired
	KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	/**
	 * A logoláshoz szükséges {@link org.slf4j.Logger Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(IsmetlodoSzolgaltatasImpl.class);

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus létrehozza a paraméterül kapott ismétlődőt az adatbázisban, miután azt a megfelelő objektumra
	 * átmappelte, majd eredményül visszaadja az adatbázisban immár jelen lévő generált azonosítóval rendelkező
	 * Ismétlődő objektumot, visszamappelve. Mielőtt ez megtörténne, beállítja az utolsó beszúrás dátumát amennyiben
	 * szükséges. Ezt a műveletet a 
	 * {@link org.springframework.data.repository.CrudRepository#save(Object) } metódusával érjük el.
	 * */
	@Override
	public IsmetlodoVo letrehozIsmetlodot(IsmetlodoVo ismetlodo) {
		
		if( ismetlodo.getUtolsoBeszuras() == null ){
			ismetlodo.setUtolsoBeszuras(LocalDate.now());
		}
		// átmappeljük az ismétlődőt
		Ismetlodo ism = IsmetlodoMapper.toDto( ismetlodo );
		
		// elmentjük az adatbázisba
		Ismetlodo mentett_ism = ismetlodoTarolo.save( ism );
		if( mentett_ism == null ){
			logolo.warn("Nem sikerult menteni a(z) " + mentett_ism + " ismetlodot!");
		} else {
			logolo.debug("Sikeresen elmentesre kerult a(z) " + mentett_ism + " ismetlodot!");
		}
		
		// visszaadjuk az mentett ismétlődőt átmappelve
		return IsmetlodoMapper.toVo( mentett_ism );
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban 
	 * a metódus frissíti a paraméterül kapott ismétlődő objektumot az adatbázisban,
	 * azaz a kapott objektum adatai meg fognak egyezni az adatbázisban lévőével.
	 * Mivel a paraméterül kapott objektum már létezik az adatbázisban, ezért rendelkezik már generált
	 * azonosítóval, ezért amikor a {@link org.springframework.data.repository.CrudRepository#save(Object) } metódus
	 * segítségével perzisztáljuk, nem jön létre újabb adatbázis elem, hanem a megfelelő ID-val rendelkező elemet fogja frissíteni.
	 * */
	@Override
	public IsmetlodoVo frissitIsmetlodot(IsmetlodoVo ismetlodo) {
		// átmappeljük az ismétlődőt
		Ismetlodo ism = IsmetlodoMapper.toDto( ismetlodo );
				
		// elmentjük az adatbázisba, de mivel már létezik, frissülni fog
		Ismetlodo mentett_ism = ismetlodoTarolo.save( ism );
		if( mentett_ism == null ){
			logolo.warn("Nem sikerult frissiteni a(z) " + mentett_ism + " ismetlodot!");
		} else {
			logolo.debug("Sikeresen frissult a(z) " + mentett_ism + " ismetlodot!");
		}
				
		// visszaadjuk az mentett ismétlődőt átmappelve
		return IsmetlodoMapper.toVo( mentett_ism );
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Ebben az implementációban
	 * a metódus ellenőrzi a tranzakcióhoz tartozó ismétlődőket. Az ellenőrzés abból áll, hogy minden ismétlődőnek összeshonlítja
	 * a {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo#getUtolsoBeszuras() Ismetlodo.getUtolsoBeszuras()} metódus által visszaadott
	 * dátumát a mai dátummal. Ha az eredmény az hogy a mai dátum pontosan 
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo#getIdo() Ismetlodo.getIdo()}-nyivel
	 * későbbi mint az utolsó beszúrás dátuma, akkor ez azt jelenti hogy a tranzakciónak meg kell ismétlődnie, azaz be kell szúrnunk egy 
	 * újabb ugyanilyen tranzakciót, majd be kell állítani az utolsó beszúrás dátumát a mai napra. A metódus pontosan ezt teszi.
	 * Ha több nappal van "elmaradva" a beszúrással, akkor többször szúrja be a tranzakciót a megfelelő dátummal.
	 * */
	@Override
	public void ismetlodoEllenorzes( FelhasznaloVo felhasznalo, List<TranzakcioVo> felh_tranzakcioi ) {
		
		// bejárjuk a tranzakcióit a felhasználónak
		for (TranzakcioVo tranzakcioVo : felh_tranzakcioi) {
			
			// elkérjük a tranzakcióhoz tartozó ismétlődőt
			IsmetlodoVo ism = tranzakcioVo.getIsmetlodo();
			
			// ha van ismétlődő tranzakciója
			if( ism != null ){
				logolo.info("A " + tranzakcioVo.getId() + " azonositoju tranzakcio ismetlodo!");
				
				// a mai nap
				LocalDate ma = LocalDate.now();
				
				// amíg az ismétlődő utolsó beszúrásának időpontja az ismétlődés gyakoriságával régebbi
				// mint a mai dátum
				if( ism.getIdo() == null ){
					System.out.println("ism ido null");
				}
				while( ma.isAfter( ism.getUtolsoBeszuras().plus(ism.getIdo()-1L, ChronoUnit.DAYS) ) ){
					
					// átállítjuk egy ismétlődés gyakoriságányival előrébb a beszúrás időpontját
					LocalDate utolso_beszuras = ism.getUtolsoBeszuras().plus(ism.getIdo(), ChronoUnit.DAYS);
					ism.setUtolsoBeszuras(utolso_beszuras);
					
					// lefrissítjük az ismétlődőt mivel módosítottuk
					frissitIsmetlodot(ism);
					
					// és be kell szúrni egy új tranzakciót mert ismétlődött a tranzakció
					TranzakcioVo ujTr = new TranzakcioVo();
					ujTr.setOsszeg(tranzakcioVo.getOsszeg());
					ujTr.setLeiras(tranzakcioVo.getLeiras());
					ujTr.setDatum(utolso_beszuras);
					
					KategoriaVo trz_kategoriaja = kategoriaSzolgaltatas.keresKategoriat(tranzakcioVo.getKategoria().getNev());
					
					TranzakcioVo letezo_tr = tranzakcioSzolgaltatas.letrehozTranzakciot(ujTr);
					
					felhasznalo.getTranzakciok().add(letezo_tr);					
					
					felhasznaloSzolgaltatas.frissitFelhasznalot(felhasznalo);
					
					// beállítom a tranzakciónak a frissített kategóriát
					letezo_tr.setKategoria(trz_kategoriaja);
					// beállítom a tranzakciónak a felhasználót
					letezo_tr.setFelhasznalo(felhasznalo);
					
					tranzakcioSzolgaltatas.frissitTranzakciot(letezo_tr);
					
				}
			}
		}
	}
}
