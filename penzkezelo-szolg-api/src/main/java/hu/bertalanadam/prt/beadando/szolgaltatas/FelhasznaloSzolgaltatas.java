package hu.bertalanadam.prt.beadando.szolgaltatas;

import java.util.List;
import java.util.Map;

import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

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
	 * Ez a szolgáltatás létrehozza a paraméterül kapott felhasználót az adatbázisban.
	 * Az implementációban szereplő adatbázis modulban lévő {@link hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo FelhasznaloTarolo}
	 * segítségével perzistenssé alakítjuk a felhasználót.
	 * @param felhasznalo Az a felhasználó amelyet perzisztálni kell az adatbázisba.
	 * @return Az immár perzisztált adatbáziselem.
	 */
	FelhasznaloVo ujFelhasznaloLetrehozas( FelhasznaloVo felhasznalo );
	
	/**
	 * Ez a szolgáltatás egy adatbázsiban már szereplő felhasználó adatait frissíti, azaz
	 * módosítja az adatbázisban úgy, hogy az megfeleljen a paraméterül kapott felhasználó adataival.
	 * @param felhasznalo Az a módosított adatokkal rendelkező felhasználó, amelyet szeretnénk perzisztálni.
	 * @return A frissített felhasználó amely már szerepel az adatbázisban.
	 */
	FelhasznaloVo frissitFelhasznalot( FelhasznaloVo felhasznalo );
	
	/**
	 * Kiszámolja a felhasználó összes eddigi bevételét és visszaadja eredményül.
	 * @param felhasznalo A felhasználó akinek az eddigi összes bevételére kíváncsiak vagyunk.
	 * @return A felhasználó eddigi összes bevétele.
	 */
	long osszesBevetelAFelhasznalohoz( FelhasznaloVo felhasznalo );
	
	/**
	 * Kiszámolja a felhasználó összes eddigi kiadását és visszaadja eredményül.
	 * @param felhasznalo A felhasználó akinek az eddigi összes kiadására kíváncsiak vagyunk.
	 * @return A felhasználó eddigi összes kiadása.
	 */
	long osszesKiadasAFelhasznalohoz( FelhasznaloVo felhasznalo );
	
	/**
	 * @param felhasznalo
	 * @return
	 */
	List<TranzakcioVo> osszesTranzakcioAFelhasznalohoz( FelhasznaloVo felhasznalo );
	
	/**
	 * @param felhasznalo
	 * @return
	 */
	Map<String,Long> bevDiagramAdatokSzamitasaFelhasznalohoz( FelhasznaloVo felhasznalo );
	
	/**
	 * @param felhasznalo
	 * @return
	 */
	Map<String,Long> kiadDiagramAdatokSzamitasaFelhasznalohoz( FelhasznaloVo felhasznalo );
	
	/**
	 * @param felhasznalo
	 * @return
	 */
	List<KategoriaVo> osszesKategoriaAFelhasznalohoz( FelhasznaloVo felhasznalo );
	
}