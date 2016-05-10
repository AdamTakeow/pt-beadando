package hu.bertalanadam.prt.beadando.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Egyszerű osztály a felhasználóhoz tartozó adatok szállítására az adatbázis réteg
 * és a szolgáltatás réteg között (POJO). 
 */
public class FelhasznaloVo implements Serializable {

	/**
	 * Alapértelmezet sorozat azonosító a szerializáláshoz.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Üres konstruktor a szerializáláshoz.
	 */
	public FelhasznaloVo() {
	}

	/**
	 * A felhasználó azonosítója.
	 */
	private Long id;
	
	/**
	 * A felhasználó felhasználóneve.
	 */
	private String felhasznalonev;
	
	/**
	 * A felhasználó jelszava. 
	 */
	private String jelszo;
	
	/**
	 * A felhasználó vagyonát reprezentáló egyenleg.
	 */
	private Long egyenleg;
	
	/**
	 * 
	 */
	private Long kiadasraSzantPenz;
	
	/**
	 * 
	 */
	private LocalDate kezdoIdopont;
	
	/**
	 * 
	 */
	private LocalDate vegIdopont;
	
	/**
	 * Lista a felhasználó tranzakcióiról.
	 */
	private List<TranzakcioVo> tranzakciok;
	
	/**
	 * Lista a felhasználó kategóriáiról.
	 */
	private List<KategoriaVo> kategoriak;

	/**
	 * Visszaadja a felhasználó azonosítóját.
	 * @return A felhasználó azonosítója.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Beállítja a felhasználó azonosítóját.
	 * @param id A felhasználó beállítandó azonosítója.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Visszaadja a felhasználó felhasználónevét.
	 * @return A felhasználó felhasználóneve.
	 */
	public String getFelhasznalonev() {
		return felhasznalonev;
	}

	/**
	 * Beállítja a felhasználó felhasználónevét.
	 * @param felasznalonev A beállítandó felhasználónév.
	 */
	public void setFelhasznalonev(String felasznalonev) {
		this.felhasznalonev = felasznalonev;
	}

	/**
	 * Visszaadja a felhasználó jelszavát.
	 * @return A felhasználó jelszava.
	 */
	public String getJelszo() {
		return jelszo;
	}

	/**
	 * Beállítja a felhasználó jelszavát.
	 * @param jelszo A beállítandó jelszó.
	 */
	public void setJelszo(String jelszo) {
		this.jelszo = jelszo;
	}

	/**
	 * Visszaadja a felhasználó egyenleg összegét.
	 * @return A felhasználó egyenlege.
	 */
	public Long getEgyenleg() {
		return egyenleg;
	}

	/**
	 * Beállítja a felhasználó egyenlegének összegét.
	 * @param egyenleg A beállítandó összeg.
	 */
	public void setEgyenleg(Long egyenleg) {
		this.egyenleg = egyenleg;
	}

	/**
	 * Visszaadja a felhasználó tranzakcióinak listáját.
	 * @return A felhasználó tranzakciói egy listában.
	 */
	public List<TranzakcioVo> getTranzakciok() {
		return tranzakciok;
	}

	/**
	 * Beállítja a felhasználó tranzakcióinak listáját.
	 * @param tranzakciok A beállítadnó tranzakciókat tartalmazó lista.s
	 */
	public void setTranzakciok(List<TranzakcioVo> tranzakciok) {
		this.tranzakciok = tranzakciok;
	}

	/**
	 * Visszaadja a felhasználó kategóriáit.
	 * @return A felhasználó kategórialistája.
	 */
	public List<KategoriaVo> getKategoriak() {
		return kategoriak;
	}
	
	/**
	 * Beállít a felhasználóhoz tartozó kategóriákat.
	 * @param kategoriak A beállítandó kategórialista.
	 */
	public void setKategoriak(List<KategoriaVo> kategoriak) {
		this.kategoriak = kategoriak;
	}

	/**
	 * @return
	 */
	public LocalDate getKezdoIdopont() {
		return kezdoIdopont;
	}

	/**
	 * @param kezdoIdopont
	 */
	public void setKezdoIdopont(LocalDate kezdoIdopont) {
		this.kezdoIdopont = kezdoIdopont;
	}

	/**
	 * @return
	 */
	public LocalDate getVegIdopont() {
		return vegIdopont;
	}

	/**
	 * @param vegIdopont
	 */
	public void setVegIdopont(LocalDate vegIdopont) {
		this.vegIdopont = vegIdopont;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FelhasznaloVo [id=" + id + ", felhasznalonev=" + felhasznalonev + ", jelszo=" + jelszo + ", egyenleg="
				+ egyenleg + ", kezdoIdopont=" + kezdoIdopont + ", vegIdopont=" + vegIdopont + ", tranzakciok="
				+ tranzakciok + ", kategoriak=" + kategoriak + "]";
	}

	/**
	 * @return
	 */
	public Long getKiadasraSzantPenz() {
		return kiadasraSzantPenz;
	}

	/**
	 * @param kiadasraSzantPenz
	 */
	public void setKiadasraSzantPenz(Long kiadasraSzantPenz) {
		this.kiadasraSzantPenz = kiadasraSzantPenz;
	}
	
}
