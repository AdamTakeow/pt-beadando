package hu.bertalanadam.prt.beadando.db.tarolo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;

/**
 * A tranzakciókhoz tartozó DAO aminek segítségével műveleteket hajthatunk végre a tranzakciókon.
 * Ez az osztály egy DAO amit a {@link org.springframework.stereotype.Repository Repository} annotációval jelzünk.
 * Ez az osztály a {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository} leszármazottja,
 * ezáltal az alapvető CRUD műveletek előre definiáltak.
 * */
@Repository
public interface TranzakcioTarolo extends JpaRepository<Tranzakcio, Long> {
	
	/**
	 * Olyan tranzakciókat keres az adatbázsiban, amelyek egy bizonyos felhasználóhoz tartoznak.
	 * A metódushoz tartozó lekérdezést a spring készíti el a metódus neve alapján, ezért a 
	 * findBy használata szükséges a metódus nevében, valamint ezt követően az a mező, ami a {@code where}
	 * feltételben szerepelni fog.
	 * @param felhasznalo Az a felhasználó aminek meg kell egyeznie a talált tranzakcióhoz tartozó felhasználóval.
	 * @return Egy lista azokról a tranzakciókról amelyek a metódus paramétereként megadott felhasználóhoz tartoznak.
	 */
	List<Tranzakcio> findByFelhasznalo( Felhasznalo felhasznalo );
	
	/**
	 * Megkeresi a paraméterben kapott felhasználó legkorábbi tranzakcióját.
	 * A metódushoz tartozó lekérdezést a spring készíti el a metódus neve alapján a következőképpen:
	 * Sorba rendezi a tranzakciókat a Datum adattag szerint növekvően a metódus nevében szepelő "OrderByDatumAsc" 
	 * kifejezésnek köszönhetően, majd ennek a sorbarendezett listának az első elemét veszi ki és adja vissza. 
	 * @param felhasznalo Az a felhasználó akinek a legkorábbi tranzakciója kell.
	 * @return A felhasználó lekorábbi tranzakciója.
	 */
	Tranzakcio findFirstByFelhasznaloOrderByDatumAsc( Felhasznalo felhasznalo );

}
