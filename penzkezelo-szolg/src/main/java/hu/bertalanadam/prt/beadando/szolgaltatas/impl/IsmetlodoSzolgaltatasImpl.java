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

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class IsmetlodoSzolgaltatasImpl implements IsmetlodoSzolgaltatas {
	
	@Autowired
	IsmetlodoTarolo ismetlodoTarolo;
	
	@Autowired
	TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	@Autowired
	FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;
	
	@Autowired
	KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	private static Logger logolo = LoggerFactory.getLogger(IsmetlodoSzolgaltatasImpl.class);

	@Override
	public IsmetlodoVo ujIsmetlodoLetrehozas(IsmetlodoVo ismetlodo) {
		// átmappeljük az ismétlődőt
		Ismetlodo ism = IsmetlodoMapper.toDto( ismetlodo );
		
		// elmentjük az adatbázisba
		Ismetlodo mentett_ism = ismetlodoTarolo.save( ism );
		
		// visszaadjuk az mentett ismétlődőt átmappelve
		return IsmetlodoMapper.toVo( mentett_ism );
	}

	@Override
	public void ismetlodoEllenorzes( FelhasznaloVo felhasznalo, List<TranzakcioVo> felh_tranzakcioi ) {

		// ez a metódus ellenőrzi minden indításkor, hogy kell-e új tranzakciót beszúrni,
		// vagy sem az ismétlődő alapján
		
		// bejárjuk a tranzakcióit a felhasználónak
		for (TranzakcioVo tranzakcioVo : felh_tranzakcioi) {
			
			IsmetlodoVo ism = tranzakcioVo.getIsmetlodo();
			// ha van ismétlődője
			if( ism != null ){
				logolo.info("HOPP EGY ISMETLODO");
				
				// ellenőrizzük hogy kell-e újat beszúrni
				// megnézzük az ismétlődő utolsó beszúrásának időpontját
				// ha még nem volt beszúrva egyszer sem, akkor meg fog eggyezni a tranzakció
				// létrehozásának dátumával
				
				// összehasonlítjuk az aktuális dátummal
				// ha eltelt köztük az ismétlődőben szereplő mennyiség, akkor beszúrunk egy új
				// tranzakciót ami ugyanolyan mint ez a tranzakcioVO
				LocalDate ma = LocalDate.now();
				
				// amíg az ismétlődő utolsó beszúrásának időpontja az ismétlődés gyakoriságával régebbi
				// mint a mai dátum
				while( ma.isAfter( ism.getUtolsoBeszuras().plus(ism.getIdo()-1L, ChronoUnit.DAYS) ) ){
					
					logolo.info(" MA: " + ma.toString() + " IS AFTER " + tranzakcioVo.getDatum().plus(ism.getIdo()-1L, ChronoUnit.DAYS)  );
					
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
					
					// magic
					KategoriaVo trz_kategoriaja = kategoriaSzolgaltatas.getKategoriaByNev(tranzakcioVo.getKategoria().getNev());
					
					TranzakcioVo letezo_tr = tranzakcioSzolgaltatas.ujTranzakcioLetrehozas(ujTr);
					
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

	@Override
	public IsmetlodoVo frissitIsmetlodot(IsmetlodoVo ismetlodo) {
		// átmappeljük az ismétlődőt
		Ismetlodo ism = IsmetlodoMapper.toDto( ismetlodo );
				
		// elmentjük az adatbázisba, de mivel már létezik, frissülni fog
		Ismetlodo mentett_ism = ismetlodoTarolo.save( ism );
				
		// visszaadjuk az mentett ismétlődőt átmappelve
		return IsmetlodoMapper.toVo( mentett_ism );
	}

}
