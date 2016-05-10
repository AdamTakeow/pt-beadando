package hu.bertalanadam.prt.beadando.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Egyszerű osztály a kategóriához tartozó adatok szállítására az egyes rétegek között (POJO). 
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

	@Override
	public String toString() {
		return "KategoriaVo [id=" + id + ", nev=" + nev + ", felhasznalok=" + felhasznalok + "]";
	}

}
