package hu.bertalanadam.prt.beadando.szolgaltatas;

import java.util.List;

import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

/**
 * A tranzakciókon értelmezett műveleteket leíró interfész. Definiálja hogy milyen
 * műveleteket hajthatunk végre a tranzakciókon és ezek eredményeit valamint az ezekhez 
 * szükséges paramétereket.
 */
public interface TranzakcioSzolgaltatas {

	/**
	 * Létrehozza a paraméterül kapott tranzakciónak megfelelő elemet az adatbázisban.
	 * @param ujTranzakcio Az adatbázisba elmenteni kívánt tranzakció.
	 * @return Az immár adatbázisban jelen lévő tranzakció.
	 */
	TranzakcioVo ujTranzakcioLetrehozas( TranzakcioVo ujTranzakcio );
	
	/**
	 * Egy módosított tranzakció változásait vezeti át az adatbázisba.
	 * @param tranzakcio A módosításokat tartalmazó tranzakció.
	 * @return Az adatbázisban módosításokkal jelen lévő tranzakció.
	 */
	TranzakcioVo frissitTranzakciot( TranzakcioVo tranzakcio );
	
	/**
	 * Visszaadja azt a tranzakciót az adatbázisból, amelynek megegyezik az azonosító száma
	 * a paraméterül kapott azonosítószámmal.
	 * @param id A keresett tranzakció azonosítója.
	 * @return A paraméterül kapott azonosítóval rendelkező tranzakció.
	 */
	TranzakcioVo findById( Long id );
	
	/**
	 * Visszaadja azokat a tranzakciókat az adatbázisból, melyek a paraméterül kapott felhasználóhoz
	 * tartoznak.
	 * @param felhasznalo Az a felhasználó amely tranzakcióit szeretnénk visszakapni.
	 * @return Egy lista a paraméterül kapott felhasználó tranzakcióiról.
	 */
	List<TranzakcioVo> osszesTranzakcioAFelhasznalohoz( FelhasznaloVo felhasznalo );
	
	/**
	 * @param felhasznalo
	 * @return
	 */
	TranzakcioVo getLegkorabbiTranzakcioFelhasznalohoz( FelhasznaloVo felhasznalo );
	
	/**
	 * @param tranzakcio
	 */
	void tranzakcioTorles( TranzakcioVo tranzakcio );

}
