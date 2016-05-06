package hu.bertalanadam.prt.beadando.szolgaltatas.impl;

import java.util.List;

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
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

/**
 * A tranzakciók kezelését támogató osztály.
 * Ez az osztály a {@link org.springframework.stereotype.Service} annotációval van ellátva,
 * azaz ez egy {@link org.springframework.stereotype.Component} csak specifikáltabb.
 * A {@link org.springframework.transaction.annotation.Transactional} annotáció révén
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
	
	@Autowired
	private FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;

	@Override
	public TranzakcioVo ujTranzakcioLetrehozas(TranzakcioVo ujTranzakcio) {

		Tranzakcio uj = TranzakcioMapper.toDto(ujTranzakcio);
		return TranzakcioMapper.toVo(tranzakcioTarolo.save(uj));

	}

	// TODO na ide kéne az ami a felhasználóba van...
	@Override
	public List<TranzakcioVo> osszesTranzakcioAFelhasznalohoz( FelhasznaloVo felhasznalo ) {

		Felhasznalo felh = FelhasznaloMapper.toDto(felhasznalo);
		
		List<Tranzakcio> trk = tranzakcioTarolo.findByFelhasznalo(felh);
		
		return TranzakcioMapper.toVo(trk);
	}

	@Override
	public TranzakcioVo frissitTranzakciot(TranzakcioVo tranzakcio) {
		
		Tranzakcio uj = TranzakcioMapper.toDto(tranzakcio);
		
		Tranzakcio mentett = tranzakcioTarolo.save(uj);

		return TranzakcioMapper.toVo(mentett);
		
	}

	@Override
	public TranzakcioVo findById( Long id ) {
		
		Tranzakcio found = tranzakcioTarolo.findOne(id);
		
		return TranzakcioMapper.toVo(found);
	}

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
		
		// töröljük az aktuálist tranzakciót
		tranzakcioTarolo.delete(tranzakcio.getId());
		
	}

	@Override
	public TranzakcioVo getLegkorabbiTranzakcioFelhasznalohoz(FelhasznaloVo felhasznalo) {
		
		Felhasznalo felh = FelhasznaloMapper.toDto(felhasznalo);
		
		Tranzakcio legkorabbi = tranzakcioTarolo.findFirstByFelhasznaloOrderByDatumAsc(felh);
		
		return TranzakcioMapper.toVo(legkorabbi);
	}
}
