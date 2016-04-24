package hu.bertalanadam.prt.beadando.szolgaltatas;

import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;

public interface FelhasznaloSzolgaltatas {

	FelhasznaloVo findByFelhasznalonev( String felhasznalonev ) throws Exception;
	
}
