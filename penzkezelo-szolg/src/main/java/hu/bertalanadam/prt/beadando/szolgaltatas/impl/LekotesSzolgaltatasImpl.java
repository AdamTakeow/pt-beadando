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
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class LekotesSzolgaltatasImpl implements LekotesSzolgaltatas {
	
	/**
	 * 
	 */
	private static Logger logolo = LoggerFactory.getLogger(LekotesSzolgaltatasImpl.class);
	
	/**
	 * 
	 */
	@Autowired
	LekotesTarolo lekotesTarolo;
	
	/**
	 * 
	 */
	@Autowired
	TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	/**
	 * 
	 */
	@Autowired
	KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	/**
	 * 
	 */
	@Autowired
	FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;

	@Override
	public boolean vanAktivLekoteseAFelhasznalonak(FelhasznaloVo felhasznalo, List<TranzakcioVo> tranzakciok) {
		
		// végigmegyünk az összes tranzakción
		for (TranzakcioVo tranzakcioVo : tranzakciok) {
			// ha van lekötés hozzá
			if( tranzakcioVo.getLekotes() != null && !tranzakcioVo.getLekotes().isTeljesitett() ){
				logolo.info("Van lekötés, ami nincs teljesítve!!");
				return true;
			}
		}
		logolo.info("Nincs lekötés, vagy mindegyik teljesítve van!");
		return false;
	}

	@Override
	public LekotesVo letrehozLekotest(LekotesVo lekotes) {
		Lekotes ujLekotes = LekotesMapper.toDto(lekotes);
		
		Lekotes mentett = lekotesTarolo.save(ujLekotes);
		
		return LekotesMapper.toVo(mentett);
	}
	
	@Override
	public LekotesVo frissitLekotest(LekotesVo lekotes) {
		Lekotes ujLekotes = LekotesMapper.toDto(lekotes);
		
		Lekotes mentett = lekotesTarolo.save(ujLekotes);
		
		return LekotesMapper.toVo(mentett);
	}

	@Override
	public void lekotesEllenorzes(FelhasznaloVo felhasznalo, List<TranzakcioVo> felh_tranzakcioi) {
		
		// ez a metódus ellenőrzi minden indításkor, hogy lejárt-e már a meglévő lekötés, vagy sem
		// és ha lejárt akkor be kell szúrni a tranzakciót
				
		// bejárjuk a tranzakcióit a felhasználónak
		for (TranzakcioVo tranzakcioVo : felh_tranzakcioi) {
					
			LekotesVo lek = tranzakcioVo.getLekotes();
			// ha van lekötése
			if( lek != null && !lek.isTeljesitett() ){
//			logolo.info("HOPP EGY LEKOTES");
						
				// ellenőrizzük hogy kell-e lekötéshez tranzakciót beszúrni
				// megnézzük az lekötés dátumát ( azaz a lekötés kiadás tranzakciójának dátumát )
				// ha eltelt már ettől a dátumtól számítva "futamido"-nyi év, akkor lejárt
						
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
							
					// magic
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

	@Override
	public LekotesVo felhasznaloLekotese(FelhasznaloVo felhasznalo) {
		
		return felhasznalo.getTranzakciok().stream()
									.filter( t -> t.getLekotes() != null && !t.getLekotes().isTeljesitett() )
									.map( t -> t.getLekotes() )
									.findAny()
									.get();
	}

}
