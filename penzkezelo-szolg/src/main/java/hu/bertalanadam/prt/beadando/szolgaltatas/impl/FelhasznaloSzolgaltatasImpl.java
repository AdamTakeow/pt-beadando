package hu.bertalanadam.prt.beadando.szolgaltatas.impl;

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
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FelhasznaloSzolgaltatasImpl implements FelhasznaloSzolgaltatas {

	/**
	 * 
	 */
	@Autowired
	FelhasznaloTarolo felhasznaloTarolo;
	
	/* (non-Javadoc)
	 * @see hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas#findByFelhasznalonev(java.lang.String)
	 */
	@Override
	public FelhasznaloVo findByFelhasznalonev(String felhasznalonev) {
		Felhasznalo f = null;
		try {
			f = felhasznaloTarolo.findByFelhasznalonev(felhasznalonev);
		} catch (Exception e) {
			// logolás
			e.printStackTrace();
		}
		
		return FelhasznaloMapper.toVo( f );
	}

	
}
