package hu.bertalanadam.prt.beadando.db.tarolo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;

/**
 * A felhasználókhoz tartozó DAO aminek segítségével műveleteket hajthatunk végre a felhasználókon.
 * Ez az osztály egy DAO amit a {@link org.springframework.stereotype.Repository Repository} annotációval jelzünk.
 * A {@link org.springframework.transaction.annotation.Transactional Transactional} annotáció Propagation.SUPPORTS konfigurációjával
 * elérjük hogy egy éppen futó tranzakcióba képes legyen egy művelet bekapcsolódni.
 * Ez az osztály a {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository} leszármazottja,
 * ezáltal az alapvető CRUD műveletek előre definiáltak.
 * */
@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public interface FelhasznaloTarolo extends JpaRepository<Felhasznalo, Long> {

	/**
	 * Egy felhasználót keres az adatbázsiban a felhasználóneve alapján.
	 * A metódushoz tartozó lekérdezést a spring készíti el a metódus neve alapján, ezért a 
	 * findBy használata szükséges a metódus nevében, valamint ezt követően az a mező, ami a {@code where}
	 * feltételben szerepelni fog.
	 * @param felhasznalonev Az a felhasználónév aminek meg kell egyeznie a talált felhasználó felhasználónevével.
	 * Ezt a felhasználónevet helyettesíti be a {@code where felhasznalonev = ?} kifejezésben a ? helyére.
	 * @return Pontosan egy {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo Felhasznalo} amelynek
	 * megegyezik a felhasználóneve a metódus paraméteréül adott Stringben szereplővel.
	 */
	Felhasznalo findByFelhasznalonev( String felhasznalonev );
}
