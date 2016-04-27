package hu.bertalanadam.prt.beadando.szolgaltatas.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;
import hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo;
import hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo;
import hu.bertalanadam.prt.beadando.mapper.TranzakcioMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TranzakcioSzolgaltatasImpl implements TranzakcioSzolgaltatas {
	
	/**
	 * A logoláshoz szükséges {@link org.slf4j.Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(TranzakcioSzolgaltatasImpl.class);
	
	@Autowired
	private TranzakcioTarolo tranzakcioTarolo;
	
//	@Autowired
//	private FelhasznaloTarolo felhasznaloTarolo;

	@Override
	public void ujTranzakcioLetrehozas(TranzakcioVo ujTranzakcio) {

		// létrehozom az új tranzakciót
		Tranzakcio uj = TranzakcioMapper.toDto(ujTranzakcio);
		tranzakcioTarolo.save(uj);
		
		

	}

}
