package hu.bertalanadam.prt.beadando.db.tarolo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.db.entitas.Kategoria;

/**
 * A kategóriákhoz tartozó DAO aminek segítségével műveleteket hajthatunk végre a kategóriákon.
 * Ez az osztály egy DAO amit a {@link org.springframework.stereotype.Repository Repository} annotációval jelzünk.
 * A {@link org.springframework.transaction.annotation.Transactional Transactional} annotáció Propagation.SUPPORTS konfigurációjával
 * elérjük hogy egy éppen futó tranzakcióba képes legyen egy művelet bekapcsolódni.
 * Ez az osztály a {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository} leszármazottja,
 * ezáltal az alapvető CRUD műveletek előre definiáltak.
 * */
@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public interface KategoriaTarolo extends JpaRepository<Kategoria, Long> {
	
	/**
	 * Egy kategóriát keres az adatbázsiban a kategórianeve alapján.
	 * A metódushoz tartozó lekérdezést a spring készíti el a metódus neve alapján, ezért a 
	 * findBy használata szükséges a metódus nevében, valamint ezt követően az a mező, ami a {@code where}
	 * feltételben szerepelni fog.
	 * @param nev Az a kategórianév aminek meg kell egyeznie a talált kategória kategórianevével.
	 * Ezt a kategórianevet helyettesíti be a {@code where nev = ?} kifejezésben a ? helyére.
	 * @return Pontosan egy {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria Kategoria} amelynek
	 * megegyezik a neve a metódus paraméterül adott Stringben szereplővel.
	 */
	Kategoria findByNev(String nev);
	
	/**
	 * Megkeresi az adatbázisban azokat a kategóriákat, amelyek a paraméterként kapott felhasználóhoz tartoznak.
	 * A metódushoz tartozó lekérdezést a {@link org.springframework.data.jpa.repository.Query Query} annotációban
	 * JPQL segítségével hajtom végre.
	 * @param felhasznalo Az a felhasználó akinehez a hozzátartozó kategóriáit keressük.
	 * @return Egy lista a felhasználó kategóriáiról.
	 */
	@Query("SELECT k FROM Kategoria k WHERE ?1 MEMBER OF k.felhasznalok")
	List<Kategoria> findByFelhasznaloIn( Felhasznalo felhasznalo );

}
