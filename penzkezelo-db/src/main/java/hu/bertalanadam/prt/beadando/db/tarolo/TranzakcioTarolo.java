package hu.bertalanadam.prt.beadando.db.tarolo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;

/**
 * A tranzakciókhoz tartozó DAO aminek segítségével műveleteket hajthatunk végre a tranzakciókon.
 * Ez az osztály egy DAO amit a {@link org.springframework.stereotype.Repository} annotációval jelzünk.
 * A {@link org.springframework.transaction.annotation.Transactional} annotáció Propagation.SUPPORTS annotációval
 * elérjük hogy egy éppen futó tranzakcióba képes legyen egy művelet bekapcsolódni.
 * Ez az osztály a {@link org.springframework.data.jpa.repository.JpaRepository} leszármazottja,
 * ezáltal az alapvető CRUD műveletek előre definiáltak.
 * */
@Repository
@Transactional(propagation = Propagation.SUPPORTS)
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

}
