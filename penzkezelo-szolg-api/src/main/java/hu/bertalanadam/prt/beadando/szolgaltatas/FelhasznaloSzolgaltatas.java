package hu.bertalanadam.prt.beadando.szolgaltatas;

import java.util.Map;

import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;

/**
 * A felhasználók kezelését leíró interfész.
 * Leírja hogy milyen szolgáltatásokat kell definiálni
 * a felhasználók kezeléséhez.
 */
public interface FelhasznaloSzolgaltatas {

	/**
	 * Ez a szolgáltatás végzi a felhasználók megtalálását az adatbázisban.
	 * A kapott felhasználónevet továbbítja az adatbázis rétegnek a megfelelő metódussal,
	 * ami lekérdezi az adatbázisból azt a felhasználót amelyiknek megeggyezik a felhasználóneve
	 * a paraméterben szereplővel.
	 * @param felhasznalonev A keresendő felhasználónév
	 * @return Az adatbázisból lekérdezett Felhasznalo
	 * ami FelhasznaloVo-vá lett alakítva a FelhasznaloMapper
	 * segítségével.
	 */
	FelhasznaloVo keresFelhasznalot( String felhasznalonev );
	
	/**
	 * Ez a szolgáltatás létrehozza a paraméterül kapott felhasználót az adatbázisban.
	 * Az implementációban szereplő adatbázis modulban lévő FelhasznaloTarolo
	 * segítségével perzistenssé alakítjuk a felhasználót.
	 * @param felhasznalo Az a felhasználó amelyet perzisztálni kell az adatbázisba.
	 * @return Az immár perzisztált adatbáziselem.
	 */
	FelhasznaloVo letrehozFelhasznalot( FelhasznaloVo felhasznalo );
	
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
	long felhasznaloOsszesBevetele( FelhasznaloVo felhasznalo );
	
	/**
	 * Kiszámolja a felhasználó összes eddigi kiadását és visszaadja eredményül.
	 * @param felhasznalo A felhasználó akinek az eddigi összes kiadására kíváncsiak vagyunk.
	 * @return A felhasználó eddigi összes kiadása.
	 */
	long felhasznaloOsszesKiadasa( FelhasznaloVo felhasznalo );
	
	/**
	 * Meghatározza a felhasználó egyes kategóriáihoz a bevételeinek összegét, majd visszaadja
	 * csoportosítva.
	 * @param felhasznalo A felhasználó akinek a kategóriánkénti bevételeire kíváncsiak vagyunk.
	 * @return Egy {@link java.util.Map Map} amiben a kulcsok a kategóriák nevei, az értékek
	 * pedig az ahhoz a kategóriához tartozó összes bevétel összege.
	 */
	Map<String,Long> bevDiagramAdatokSzamitasaFelhasznalohoz( FelhasznaloVo felhasznalo );
	
	/**
	 * Meghatározza a felhasználó egyes kategóriáihoz a kiadásainak összegét, majd visszaadja
	 * csoportosítva.
	 * @param felhasznalo A felhasználó akinek a kategóriánkénti kiadásaira kíváncsiak vagyunk.
	 * @return Egy {@link java.util.Map Map} amiben a kulcsok a kategóriák nevei, az értékek
	 * pedig az ahhoz a kategóriához tartozó összes kiadás összege.
	 */
	Map<String,Long> kiadDiagramAdatokSzamitasaFelhasznalohoz( FelhasznaloVo felhasznalo );
	
	/**
	 * Kiszámolja hogy a felhasználó a jelenlegi hónapban mennyit költhet még.
	 * Ezt az értéket a felhasználó {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo#kiadasraSzantPenz kiadasraSzantPenz}
	 * alapján számolja ki.
	 * @param felhasznalo A felhasználó akinek kiszámoljuk hogy mennyit költhet még.
	 * @return Az az összeg amit a hónap hátralévő részében a felhasználó költhet.
	 */
	Long szamolMennyitKolthetMegAFelhasznalo( FelhasznaloVo felhasznalo );
	
}