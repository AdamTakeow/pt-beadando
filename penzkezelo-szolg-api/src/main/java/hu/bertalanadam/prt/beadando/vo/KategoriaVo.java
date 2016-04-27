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
	 * A kategória megnevezése.
	 */
	private String nev;

	/**
	 * Az a tranzakciók amelyek ehhez a kategóriához tartoznak. Egy kategóriába
	 * több tranzakció is sorolható.
	 */
	private List<TranzakcioVo> tranzakciok;

	/**
	 * Visszaadja a kategória nevét.
	 * 
	 * @return A kategória neve.
	 */
	public String getNev() {
		return nev;
	}

	/**
	 * Beállítja a kategória nevét.
	 * 
	 * @param nev
	 *            A beállítandó kategórianév.
	 */
	public void setNev(String nev) {
		this.nev = nev;
	}

	/**
	 * Visszaadja azt a listát amelyben azok a tranzakciók vannak amik ebbe a
	 * kategóriába sorolhatók.
	 * 
	 * @return Egy {@link java.util.List} amiben az azonos kategóriájú
	 *         tranzakciók vannak.
	 */
	public List<TranzakcioVo> getTranzakciok() {
		return tranzakciok;
	}

	/**
	 * Beállítja hogy ebbe a kategóriába melyik tranzakciók tartozzanak.
	 * 
	 * @param tranzakciok
	 *            A {@link java.util.List} amiben az azonos kategóriájú
	 *            tranzakciók vannak.
	 */
	public void setTranzakciok(List<TranzakcioVo> tranzakciok) {
		this.tranzakciok = tranzakciok;
	}

	@Override
	public String toString() {
		return "KategoriaVo [nev=" + nev + ", tranzakciok=" + tranzakciok + "]";
	}

}
