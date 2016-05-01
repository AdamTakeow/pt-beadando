package hu.bertalanadam.prt.beadando.szolgaltatas.impl;



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
	
	/* (non-Javadoc)
	 * @see hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas#findByFelhasznalonev(java.lang.String)
	 */
	@Override
	public FelhasznaloVo findByFelhasznalonev(String felhasznalonev) {
		
		Felhasznalo f = felhasznaloTarolo.findByFelhasznalonev(felhasznalonev);

		if( f == null ){
			logolo.error("FelhasznaloSzolgaltatasImpl: Nem sikerült lekérdezni a(z) " + felhasznalonev + " felhasználónevű felhasználót!");			
		} else {
//			logolo.info("FelhasznaloSzolgaltatasImpl: Sikeres lekérdezés: a következő felhasználónévvel: " + felhasznalonev +
//					" a kapott eredmény(Felhasznalo): " + f );			
		}

		return FelhasznaloMapper.toVo( f );
	}

	/* (non-Javadoc)
	 * @see hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas#ujFelhasznaloLetrehozas(hu.bertalanadam.prt.beadando.vo.FelhasznaloVo)
	 */
	@Override
	public FelhasznaloVo ujFelhasznaloLetrehozas(FelhasznaloVo felhasznalo) {
//		logolo.info("Felhasználó mentése: " + felhasznalo);
		
		Felhasznalo ujfelhasznalo = FelhasznaloMapper.toDto(felhasznalo);
		
		// TESZTELD KI (old: saveAndFlush)
		return FelhasznaloMapper.toVo(felhasznaloTarolo.save(ujfelhasznalo));
	}

	@Override
	public FelhasznaloVo frissitFelhasznalot(FelhasznaloVo felhasznalo) {
		
		// egyelőre csak az egyenleget állítom be
		long egyenleg = felhasznalo.getTranzakciok().stream()
													.mapToLong( t -> t.getOsszeg() )
													.sum();
		
		felhasznalo.setEgyenleg(egyenleg);
		
		// TESZTELD KI (old: saveAndFlush)
		Felhasznalo ret = felhasznaloTarolo.save(FelhasznaloMapper.toDto(felhasznalo));
		return FelhasznaloMapper.toVo(ret);
	}	
}
