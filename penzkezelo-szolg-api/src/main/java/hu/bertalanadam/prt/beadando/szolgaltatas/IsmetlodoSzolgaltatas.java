package hu.bertalanadam.prt.beadando.szolgaltatas;

import java.util.List;

import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.IsmetlodoVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

/**
 *
 */
public interface IsmetlodoSzolgaltatas {

	/**
	 * @param ismetlodo
	 * @return
	 */
	IsmetlodoVo ujIsmetlodoLetrehozas( IsmetlodoVo ismetlodo );
	
	/**
	 * @param felhasznalo
	 * @param felh_tranzakcioi
	 * @return
	 */
	void ismetlodoEllenorzes( FelhasznaloVo felhasznalo, List<TranzakcioVo> felh_tranzakcioi );
	
	/**
	 * @param ismetlodo
	 * @return
	 */
	IsmetlodoVo frissitIsmetlodot( IsmetlodoVo ismetlodo );
}
