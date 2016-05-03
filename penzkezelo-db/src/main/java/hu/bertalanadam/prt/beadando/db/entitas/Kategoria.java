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
	// TEMPOR
//	/**
//	 * Az a tranzakciók amelyek ehhez a kategóriához tartoznak.
//	 * Egy kategóriába több tranzakció is sorolható.
//	 */
//	@OneToMany(mappedBy="kategoria", cascade={CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE},
//			fetch=FetchType.LAZY)
//	private List<Tranzakcio> tranzakciok;
	
	/**
	 * A kategóriát birtokló felhasználók listája.
	 * Egy kategóriát több felhasználó is birtokolhat, valamint
	 * egy felhasználó több kategóriával is rendelkezhet.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "felhasznalo_to_kategoria", joinColumns = 
	@JoinColumn(name = "kategoria_id", referencedColumnName = "id"), 
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
	// TEMPOR
//	/**
//	 * Visszaadja azt a listát amelyben azok a tranzakciók vannak amik ebbe a kategóriába sorolhatók.
//	 * @return Egy {@link java.util.List} amiben az azonos kategóriájú tranzakciók vannak.
//	 */
//	public List<Tranzakcio> getTranzakciok() {
//		return tranzakciok;
//	}
	// TEMPOR
//	/**
//	 * Beállítja hogy ebbe a kategóriába melyik tranzakciók tartozzanak.
//	 * @param tranzakciok A {@link java.util.List} amiben az azonos kategóriájú tranzakciók vannak.
//	 */
//	public void setTranzakciok(List<Tranzakcio> tranzakciok) {
//		this.tranzakciok = tranzakciok;
//	}
	
	/**
	 * Visszaadja a felhasználót akié a kategória.
	 * @return A kategória tulajdonosa.
	 */
	public List<Felhasznalo> getFelhasznalok() {
		return felhasznalok;
	}

	/**
	 * Beállítja hogy a kategória melyik felhasználóhoz tartozzon.
	 * @param felhasznalo Az a felhasználó akié a kategória.
	 */
	public void setFelhasznalok(List<Felhasznalo> felhasznalok) {
		this.felhasznalok = felhasznalok;
	}

	// TEMPOR
	@Override
	public String toString() {
		return "Kategoria [nev=" + nev + /*", tranzakciok=" + tranzakciok +*/ ", felhasznalo=" + felhasznalok + "]";
	}
}
