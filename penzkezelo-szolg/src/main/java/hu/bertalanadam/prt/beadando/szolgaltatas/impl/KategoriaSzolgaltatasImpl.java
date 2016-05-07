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
 * Ez az osztály a {@link org.springframework.stereotype.Service} annotációval van ellátva,
 * azaz ez egy {@link org.springframework.stereotype.Component} csak specifikáltabb.
 * A {@link org.springframework.transaction.annotation.Transactional} annotáció révén
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

	@Override
	public KategoriaVo ujKategoriaLetrehozas(KategoriaVo ujKategoria) {
		
		Kategoria uj = KategoriaMapper.toDto(ujKategoria);
		
		Kategoria letezo = kategoriaTarolo.save(uj);
		
		return KategoriaMapper.toVo(letezo);
	}

	@Override
	public KategoriaVo getKategoriaByNev(String kategoriaNev) {
		
		logolo.info("Kategória keresése az adatbázisban ilyen névvel: " + kategoriaNev);
		
		Kategoria found = kategoriaTarolo.findByNev(kategoriaNev);
		
		if( found == null ){
			logolo.error("Nem található kategória ilyen névvel: " + kategoriaNev );
		} else {
			logolo.info("Kategória találat ilyen névvel: " + kategoriaNev );
		}
		
		return KategoriaMapper.toVo(found);
	}

	@Override
	public List<KategoriaVo> getAllKategoria() {
		logolo.info("Összes kategória lekérése");
		
		List<Kategoria> kategoriak = kategoriaTarolo.findAll();
		
		return KategoriaMapper.toVo(kategoriak);
	}

	@Override
	public KategoriaVo frissitKategoriat(KategoriaVo kategoria) {
		
		Kategoria kateg = KategoriaMapper.toDto(kategoria);
		
		Kategoria frissitett = kategoriaTarolo.save(kateg);
		
		return KategoriaMapper.toVo(frissitett);
		
	}

	@Override
	public List<KategoriaVo> osszesKategoriaAFelhasznalohoz(FelhasznaloVo felhasznalo) {
		
		Felhasznalo felh = FelhasznaloMapper.toDto(felhasznalo);
		
		List<Kategoria> felhasznalo_kategoriai = kategoriaTarolo.findByFelhasznaloIn(felh);
		
		return KategoriaMapper.toVo(felhasznalo_kategoriai);
	}

	@Override
	public boolean vanIlyenKategoriajaAFelhasznalonak(FelhasznaloVo felhasznalo, KategoriaVo kategoria) {

		// elkérem a felhasználóit
		List<FelhasznaloVo> fhk = kategoria.getFelhasznalok();
		// megnézem hogy az éppen bejelentkezett felhasználó birtokolja-e már ezt a kategóriát
		boolean isEmpty = fhk.stream()
					    .filter( f -> f.getFelhasznalonev().equals( felhasznalo.getFelhasznalonev() ))
						.collect(Collectors.toList()).isEmpty();
		
		return isEmpty;
	}

}
