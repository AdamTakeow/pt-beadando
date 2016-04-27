package hu.bertalanadam.prt.beadando.szolgaltatas;

import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;

/**
 * A felhasználók kezelését leíró interfész. Megmondja hogy milyen szolgáltatásokat kell definiálni
 * a felhasználók kezeléséhez.
 */
public interface FelhasznaloSzolgaltatas {

	/**
	 * Ez a szolgáltatás végzi a felhasználók megtalálását az adatbázisban.
	 * A kapott felhasználónevet továbbítja az adatbázis rétegnek a megfelelő metódussal,
	 * ami lekérdezi az adatbázisból azt a felhasználót amelyiknek megeggyezik a felhasználóneve
	 * a paraméterben szereplővel.
	 * @param felhasznalonev A keresendő felhasználónév
	 * @return Az adatbázisból lekérdezett {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo}
	 * ami {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo}-vá lett alakítva a {@link hu.bertalanadam.prt.beadando.mapper.FelhasznaloMapper}
	 * segítségével.
	 */
	FelhasznaloVo findByFelhasznalonev( String felhasznalonev );
	
	/**
	 * @param felhasznalo
	 */
	void ujFelhasznaloLetrehozas( FelhasznaloVo felhasznalo );
	
}