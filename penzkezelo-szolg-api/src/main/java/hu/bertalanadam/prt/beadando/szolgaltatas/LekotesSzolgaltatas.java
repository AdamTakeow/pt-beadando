package hu.bertalanadam.prt.beadando.szolgaltatas;

import java.util.List;

import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

public interface LekotesSzolgaltatas {

	/**
	 * @param felhasznalo
	 * @param tranzakciok
	 * @return
	 */
	boolean vanLekotesAFelhasznalohoz( FelhasznaloVo felhasznalo, List<TranzakcioVo> tranzakciok );
}
