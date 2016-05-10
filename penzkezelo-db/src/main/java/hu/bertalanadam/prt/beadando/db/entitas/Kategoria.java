package hu.bertalanadam.prt.beadando.db.entitas;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Az adatbázisban egy kategóriát reprezentáló osztály.
 * Ez egy entitás amit az adatbázisba leképezve egy kategoria nevű táblát kapunk a megfelelő oszlopokkal.
 */
@Entity
@Table(name = "Kategoria")
public class Kategoria extends FoEntitas {

	/**
	 * Alapértelmezett szerializációs azonosító.
	 * */
	private static final long serialVersionUID = 1L;

	/**
	 * Üres konstruktor a szerializálhatóság miatt.
	 * */
	public Kategoria() {
	}

	/**
	 * A kategória megnevezése. 
	 */
	@Column(unique=true)
	private String nev;
	
	/**
	 * A kategóriát birtokló felhasználók listája.
	 * Egy kategóriát több felhasználó is birtokolhat, valamint
	 * egy felhasználó több kategóriával is rendelkezhet.
	 * Ezért a {@link javax.persistence.ManyToMany ManyToMany} annotációval van ellátva.
	 * A fetch = FetchType.LAZY konfigurációnak köszönhetően a felhasználókat csak akkor kérdezi
	 * le az adatbázisból, ha ténylegesen használja a kód.
	 * A {@link javax.persistence.JoinTable JoinTable} annotáció segítségével megadhatjuk a
	 * kapcsolótáblához tartozó információkat.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "felhasznalo_to_kategoria",
		joinColumns = @JoinColumn(name = "kategoria_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "felhasznalo_id", referencedColumnName = "id"))
	private List<Felhasznalo> felhasznalok;

	/**
	 * Visszaadja a kategória nevét.
	 * @return A kategória neve.
	 */
	public String getNev() {
		return nev;
	}

	/**
	 * Beállítja a kategória nevét.
	 * @param nev A beállítandó kategórianév.
	 */
	public void setNev(String nev) {
		this.nev = nev;
	}
	
	/**
	 * Visszaadja a felhasználót akié a kategória.
	 * @return A kategória tulajdonosa.
	 */
	public List<Felhasznalo> getFelhasznalok() {
		return felhasznalok;
	}

	/**
	 * Beállítja hogy a kategória melyik felhasználóhoz tartozzon.
	 * @param felhasznalok Az a felhasználó akié a kategória.
	 */
	public void setFelhasznalok(List<Felhasznalo> felhasznalok) {
		this.felhasznalok = felhasznalok;
	}

	@Override
	public String toString() {
		return "Kategoria [nev=" + nev + ", felhasznalok=" + felhasznalok + "]";
	}

}
