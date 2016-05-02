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
	 * az adatbázis rétegben lévő {@link hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo KategoriaTarolo}
	 * nyújt segítséget.
	 * @param ujKategoria Az adatbázisba lementeni kívánt kategória.
	 * @return Az immár adatbázisban szereplő kategória.
	 */
	KategoriaVo ujKategoriaLetrehozas( KategoriaVo ujKategoria );
	
	/**
	 * Megkeresi az adatbázisban azt a kategóriát, amelynek megegyezik a neve a paraméterül kapott
	 * String értékével, majd visszaadja. A művelet végrehajtásához az adatbázis modul-beli 
	 * {@link hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo KategoriaTarolo} nyújt segítséget.
	 * @param kategoriaNev Az a kategórianév amely megegyezik a keresett kategória nevével.
	 * @return A megtalált kategória.
	 */
	KategoriaVo getKategoriaByNev( String kategoriaNev );
	
	/**
	 * Visszaadja az adatbázisban szereplő összes kategróiát egy listában.
	 * @return Egy lista az adatbázisban lévő összes kategóriáról.
	 */
	List<KategoriaVo> getAllKategoria();
	
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
	List<KategoriaVo> osszesKategoriaAFelhasznalohoz( FelhasznaloVo felhasznalo );

}
