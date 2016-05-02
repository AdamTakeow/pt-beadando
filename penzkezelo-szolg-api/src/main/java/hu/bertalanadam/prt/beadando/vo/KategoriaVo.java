package hu.bertalanadam.prt.beadando.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Egyszerű osztály a kategóriához tartozó adatok szállítására az adatbázis réteg
 * és a szolgáltatás réteg között (POJO). 
 */
public class KategoriaVo implements Serializable {

	/**
	 * Alapértelmezett szerializációs azonosító.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Üres konstruktor a szerializálhatóság miatt.
	 */
	public KategoriaVo() {
	}
	
	/**
	 * A kategória azonosítója.
	 */
	private Long id;

	/**
	 * A kategória megnevezése.
	 */
	private String nev;

	/**
	 * Az a tranzakciók amelyek ehhez a kategóriához tartoznak. Egy kategóriába
	 * több tranzakció is sorolható.
	 */
	private List<TranzakcioVo> tranzakciok;
	
	/**
	 * A kategóriát birtokló felhasznlálók listája.
	 */
	private List<FelhasznaloVo> felhasznalok;

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
	 * Visszaadja azt a listát amelyben azok a tranzakciók vannak amik ebbe a
	 * kategóriába sorolhatók.
	 * @return Egy {@link java.util.List} amiben az azonos kategóriájú tranzakciók vannak.
	 */
	public List<TranzakcioVo> getTranzakciok() {
		return tranzakciok;
	}

	/**
	 * Beállítja hogy ebbe a kategóriába melyik tranzakciók tartozzanak.
	 * @param tranzakciok A {@link java.util.List} amiben az azonos kategóriájú 
	 * tranzakciók vannak.
	 */
	public void setTranzakciok(List<TranzakcioVo> tranzakciok) {
		this.tranzakciok = tranzakciok;
	}

	/**
	 * Visszaadja a kategória azonosítóját.
	 * @return A kategória azonosítója.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Beállítja a kategória azonosítóját.
	 * @param id A beállítandó azonosító.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Visszaadja a kategória tulajdonosainak listáját.
	 * @return A kategóriát birtokló felhasználók listája.
	 */
	public List<FelhasznaloVo> getFelhasznalok() {
		return felhasznalok;
	}
	
	/**
	 * Beállítja a kategóriát birtokló felhasználókat.
	 * @param felhasznalok A kategória tulajdonosainak listája.
	 */
	public void setFelhasznalok(List<FelhasznaloVo> felhasznalok) {
		this.felhasznalok = felhasznalok;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KategoriaVo [id=" + id + ", nev=" + nev + ", tranzakciok=" + tranzakciok + ", felhasznalok="
				+ felhasznalok + "]";
	}
}
