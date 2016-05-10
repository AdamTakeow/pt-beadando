package hu.bertalanadam.prt.beadando.szolgaltatas;

import java.util.List;

import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;

/**
 * A kategóriák kezelését leíró interfész, amely definiálja azokat a funkciókat amelyek
 * egy kategória kezeléséhez szükségesek.
 */
public interface KategoriaSzolgaltatas {
	
	/**
	 * Létrehozza az adatbázisban a paraméterül kapott kategóriát. A perzisztáláshoz
	 * az adatbázis rétegben lévő KategoriaTarolo
	 * nyújt segítséget.
	 * @param ujKategoria Az adatbázisba lementeni kívánt kategória.
	 * @return Az immár adatbázisban szereplő kategória.
	 */
	KategoriaVo letrehozKategoriat( KategoriaVo ujKategoria );
	
	/**
	 * Megkeresi az adatbázisban azt a kategóriát, amelynek megegyezik a neve a paraméterül kapott
	 * String értékével, majd visszaadja. A művelet végrehajtásához az adatbázis modul-beli 
	 * KategoriaTarolo nyújt segítséget.
	 * @param kategoriaNev Az a kategórianév amely megegyezik a keresett kategória nevével.
	 * @return A megtalált kategória.
	 */
	KategoriaVo keresKategoriat( String kategoriaNev );
	
	/**
	 * Visszaadja az adatbázisban szereplő összes kategróiát egy listában.
	 * @return Egy lista az adatbázisban lévő összes kategóriáról.
	 */
	List<KategoriaVo> osszesKategoria();
	
	/**
	 * Egy adatbázisban jelen lévő kategória értékeit módosítja úgy, hogy megfeleljen a 
	 * paraméterül kapott kategória értékeinek.
	 * @param kategoria Az a kategória amely tartalmazza azokat a módosított értékeket, amelyeket
	 * szeretnénk az adatbázisba rögzíteni.
	 * @return Az immár frissített kategória.
	 */
	KategoriaVo frissitKategoriat( KategoriaVo kategoria );
	
	/**
	 * Visszaadja eredményül azt a listát, amely már csak azokat a kategóriákat tartalmazza,
	 * amelyek a paraméterként megadott felhasználóhoz tartoznak.
	 * @param felhasznalo Az a felhasználó akinek a kategóriáit keressük.
	 * @return Egy lista amely csak a felhasználó kategóriáit tartalmazza.
	 */
	List<KategoriaVo> felhasznaloOsszesKategoriaja( FelhasznaloVo felhasznalo );
	
	/**
	 * Megadja, hogy a paraméterül kapott felhasználó rendelkezik-e a paraméterül kapott
	 * kategóriával, vagy sem.
	 * @param felhasznalo A felhasználó akinél kérdéses hogy birtokolja-e ezt a kategóriát.
	 * @param kategoria A kategória amire kíváncsiak vagyunk hogy hozzá tartozik-e a felhasználóhoz.
	 * @return {@code true} ha rendelkezik a felhasználó ezzel a kategóriával és {@code false} ha nem rendelkezik.
	 */
	boolean vanIlyenKategoriajaAFelhasznalonak( FelhasznaloVo felhasznalo, KategoriaVo kategoria );

}
