package hu.bertalanadam.prt.beadando.db.entitas;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
	 * Az a tranzakciók amelyek ehhez a kategóriához tartoznak.
	 * Egy kategóriába több tranzakció is sorolható.
	 */
	@OneToMany(mappedBy="kategoria", cascade={CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
	private List<Tranzakcio> tranzakciok;
	
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
	 * Visszaadja azt a listát amelyben azok a tranzakciók vannak amik ebbe a kategóriába sorolhatók.
	 * @return Egy {@link java.util.List} amiben az azonos kategóriájú tranzakciók vannak.
	 */
	public List<Tranzakcio> getTranzakciok() {
		return tranzakciok;
	}

	/**
	 * Beállítja hogy ebbe a kategóriába melyik tranzakciók tartozzanak.
	 * @param tranzakciok A {@link java.util.List} amiben az azonos kategóriájú tranzakciók vannak.
	 */
	public void setTranzakciok(List<Tranzakcio> tranzakciok) {
		this.tranzakciok = tranzakciok;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Kategoria [nev=" + nev + ", tranzakciok=" + tranzakciok + "]";
	}
}
