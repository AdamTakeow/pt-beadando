package hu.bertalanadam.prt.beadando.szolgaltatas;

import java.util.List;

import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.LekotesVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

public interface LekotesSzolgaltatas {

	/**
	 * @param felhasznalo
	 * @param tranzakciok
	 * @return
	 */
	boolean vanLekotesAFelhasznalohoz( FelhasznaloVo felhasznalo, List<TranzakcioVo> tranzakciok );
	
	/**
	 * @param lekotes
	 * @return
	 */
	LekotesVo ujLekotesLetrehozas( LekotesVo lekotes );
	
	/**
	 * @param felhasznalo
	 * @param felh_tranzakcioi
	 */
	void lekotesEllenorzes( FelhasznaloVo felhasznalo, List<TranzakcioVo> felh_tranzakcioi );
	
	/**
	 * @param lekotes
	 * @return
	 */
	LekotesVo frissitLekotest( LekotesVo lekotes );
	
	/**
	 * @param felhasznalo
	 * @return
	 */
	LekotesVo getLekotesAFelhasznalohoz( FelhasznaloVo felhasznalo );
}
