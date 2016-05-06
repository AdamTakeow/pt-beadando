package hu.bertalanadam.prt.beadando.szolgaltatas.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.tarolo.LekotesTarolo;
import hu.bertalanadam.prt.beadando.szolgaltatas.LekotesSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class LekotesSzolgaltatasImpl implements LekotesSzolgaltatas {
	
	@Autowired
	LekotesTarolo lekotesTarolo;
	
	@Autowired
	TranzakcioSzolgaltatas tranzakcioSzolgaltatas;

	@Override
	public boolean vanLekotesAFelhasznalohoz(FelhasznaloVo felhasznalo, List<TranzakcioVo> tranzakciok) {
		
		// végigmegyünk az összes tranzakción
		for (TranzakcioVo tranzakcioVo : tranzakciok) {
			// ha van lekötés hozzá
			if( tranzakcioVo.getLekotes() != null ){
				return true;
			}
		}
		return false;
	}

}
