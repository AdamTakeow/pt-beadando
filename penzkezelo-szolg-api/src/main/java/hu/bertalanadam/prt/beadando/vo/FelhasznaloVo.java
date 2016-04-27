package hu.bertalanadam.prt.beadando.vo;

import java.io.Serializable;
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
	 * A felhasználó pereferált megjelenítési módja. 
	 */
	private int lebontas;
	
	/**
	 * Lista a felhasználó tranzakcióiról.
	 */
	private List<TranzakcioVo> tranzakciok;

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
	 * Visszaadja a felhasználó preferált lebontási módját.
	 * @return A felhasználó preferált lebontási módja.
	 */
	public int getLebontas() {
		return lebontas;
	}

	/**
	 * Beállítja a felhasználó preferált lebontási módját.
	 * @param lebontas A beállítandó lebontási mód.
	 */
	public void setLebontas(int lebontas) {
		this.lebontas = lebontas;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FelhasznaloVo [id=" + id + ", felasznalonev=" + felhasznalonev + ", jelszo=" + jelszo + ", egyenleg="
				+ egyenleg + ", lebontas=" + lebontas + ", tranzakciok=" + tranzakciok + "]";
	}

}
