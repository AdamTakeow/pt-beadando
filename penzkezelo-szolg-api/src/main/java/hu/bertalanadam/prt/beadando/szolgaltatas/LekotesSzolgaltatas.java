package hu.bertalanadam.prt.beadando.szolgaltatas;

import java.time.Period;
import java.util.List;

import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.LekotesVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

/**
 * A Tranzakcióhoz tartozó lekötések kezelését leíró interfész.
 * Leírja hogy milyen szolgáltatásokat kell definiálni
 * a lekötések kezeléséhez.
 */
public interface LekotesSzolgaltatas {

	/**
	 * Létrehozza az adatbázisban a paraméterül kapott lekötést. A perzisztáláshoz
	 * az adatbázis rétegben lévő LekotesTarolo
	 * nyújt segítséget.
	 * @param lekotes Az adatbázisba lementeni kívánt lekötés.
	 * @return Az immár adatbázisban szereplő lekötés.
	 */
	LekotesVo letrehozLekotest( LekotesVo lekotes );
	
	/**
	 * Egy adatbázisban jelen lévő lekötés értékeit módosítja úgy, hogy megfeleljen a 
	 * paraméterül kapott kategória értékeinek.
	 * @param lekotes Az a lekötés amely tartalmazza azokat a módosított értékeket, amelyeket
	 * szeretnénk az adatbázisba rögzíteni.
	 * @return Az immár frissített lekötés.
	 */
	LekotesVo frissitLekotest( LekotesVo lekotes );

	/**
	 * Meghatározza, hogy a paraméterül kapott felhasználónak van-e aktív lekötése jelenleg.
	 * Aktív lekötésnek számít az a lekötés amely még nem járt le valamilyen módon.
	 * @param felhasznalo A felhasználó akinek kíváncsiak vagyunk arra hogy van-e aktív lekötése.
	 * @param tranzakciok A felhasználó tranzakciói, akinek kérdéses hogy van-e aktív lekötése.
	 * @return {@code true} ha van aktív lekötése a felhasználónak, {@code false} ha nincs.
	 */
	boolean vanAktivLekoteseAFelhasznalonak( FelhasznaloVo felhasznalo, List<TranzakcioVo> tranzakciok );
	
	/**
	 * Ez a metódus felügyeli a lekötéseket.
	 * Végrehajtásakor ellenőrzi, hogy lejárt-e már a felhasználó lekötése és be lett-e szúrva már az eredménye, 
	 * ha szükséges újabb tranzakciókat szúr be.
	 * @param felhasznalo A felhasználó akinek az lekötéseit ellenőrizzük.
	 * @param felh_tranzakcioi A felhasználó összes tranzakciójának listája.
	 */
	void lekotesEllenorzes( FelhasznaloVo felhasznalo, List<TranzakcioVo> felh_tranzakcioi );
	
	/**
	 * Megkeresi a felhasználó aktív lekötését és visszaadja.
	 * @param felhasznalo A felhasználó akinek a lekötését keressük.
	 * @return A felhasználó lekötése, ha van aktív, ha nincs akkor {@code null};
	 */
	LekotesVo felhasznaloLekotese( FelhasznaloVo felhasznalo );
	
	/**
	 * Kiszámolja hogy a felhasználó lekötésének lejártáig mennyi idő van még hátra.
	 * @param felhasznalo A felhasználó akinek a lekötéséről szó van.
	 * @param lekotes A lekötés aminek kiszámoljuk a hátra lévő idejét.
	 * @return Egy {@link java.time.Period Period} objektum amelyben a hátra lévő idő van.
	 */
	Period mennyiIdoVanHatra(FelhasznaloVo felhasznalo, LekotesVo lekotes );
	
	/**
	 * @param osszeg
	 * @param kamat
	 * @param futamido
	 * @return
	 */
	Long kiszamolVarhatoOsszeget(Long osszeg, Double kamat, Long futamido);
}
