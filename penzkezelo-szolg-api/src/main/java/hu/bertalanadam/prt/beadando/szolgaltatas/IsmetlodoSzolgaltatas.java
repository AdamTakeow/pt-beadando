package hu.bertalanadam.prt.beadando.szolgaltatas;

import java.util.List;

import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.IsmetlodoVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

/**
 * Az ismétlődő tranzakcióhoz tartozó ismétlődő informáicók kezelését leíró interfész.
 * Leírja hogy milyen szolgáltatásokat kell definiálni
 * az ismétlődők kezeléséhez.
 */
public interface IsmetlodoSzolgaltatas {

	/**
	 * Létrehozza az adatbázisban a paraméterül kapott ismétlődőt.
	 * Az ismétlődőt továbbítja az adatbázis réteg beli megfelelő metódusnak amely
	 * perzisztenssé teszi az adatbázisban.
	 * @param ismetlodo Az új perzisztálandó ismétlődő.
	 * @return Az immár perszisztens adatbázisban szereplő ismétlődő.
	 */
	IsmetlodoVo letrehozIsmetlodot( IsmetlodoVo ismetlodo );
	
	/**
	 * Frissíti az adatbázisban már szereplő ismétlődőt, ezáltal az
	 * adatbázisban is jelen lesznek az objektum módosított adattagjai.
	 * @param ismetlodo A frissítendő ismétlődő.
	 * @return A frissített adatbázisban szereplő ismétlődő.
	 */
	IsmetlodoVo frissitIsmetlodot( IsmetlodoVo ismetlodo );

	/**
	 * Ez a metódus felügyeli az ismétlődőket.
	 * Végrehajtásakor ellenőrzi, hogy a megfelelő ismétlődő tranzakciók be lettek-e szúrva már és ha 
	 * szükséges újabb tranzakciókat szúr be.
	 * @param felhasznalo A felhasználó akinek az ismétlődőit ellenőrizzük.
	 * @param felh_tranzakcioi A felhasználó összes tranzakciójának listája.
	 */
	void ismetlodoEllenorzes( FelhasznaloVo felhasznalo, List<TranzakcioVo> felh_tranzakcioi  );
}
