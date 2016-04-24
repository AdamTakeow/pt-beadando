package hu.bertalanadam.prt.beadando.db.entitas;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Az adatbázisban egy felhasználót reprezentáló osztály.
 * Ez egy entitás amit az adatbázisba leképezve egy felhasznalo nevű táblát kapunk a megfelelő oszlopokkal.
 * */
@Entity
@Table(name = "Felhasznalo")
public class Felhasznalo extends FoEntitas {

	/**
	 * Alapértelmezett szerializációs azonosító.
	 * */
	private static final long serialVersionUID = 1L;

	/**
	 * Üres konstruktor a szerializálhatóság miatt.
	 * */
	public Felhasznalo() {
	}

	/**
	 * Egy felhasználó felhasználóneve.
	 * */
	private String felhasznalonev;
	
	/**
	 * Egy felhasználó jelszava.
	 * */
	private String jelszo;
	
	/**
	 * Egy felhasználó egyenlege, azaz az a pénzösszeg ami a felhasználónak rendelkezésére áll.
	 * */
	private Long egyenleg;
	
	/**
	 * Az az érték ami meghatározza hogy a felhasználó milyen lebontásban szeretné követni a kiadásait
	 * és bevételeit (0 - heti, 1 - havi, 2 - éves).
	 * */
	private int lebontas;
	
	/**
	 * A felhasználóhoz tartozó tranzakciók (azaz a kiadásai, bevételei és lekötései).
	 * Minden egyes bevételt, kiadást és lekötést egy-egy tranzakcióként kezelünk, így 
	 * minden felhasználó rendelkezik egy listáról amelyben a tranzakciói szerepelnek.
	 * */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="tranzakcio_id")
	private List<Tranzakcio> tranzakciok;
	

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
	 * @param jelszo A beállítadnó jelszó.
	 */
	public void setJelszo(String jelszo) {
		this.jelszo = jelszo;
	}

	/**
	 * Visszaadja a felhasználó jelenlegi egyenlegét.
	 * @return A felhasználó aktuális egyenlege.
	 */
	public Long getEgyenleg() {
		return egyenleg;
	}

	/**
	 * Beállítja a felhasználó egyenlegét.
	 * @param egyenleg A beállítandó összeg.
	 */
	public void setEgyenleg(Long egyenleg) {
		this.egyenleg = egyenleg;
	}

	/**
	 * Visszaadja hogy a felhasználó milyen időközönként szeretné követni a tranzakcióit.
	 * @return 0, ha a felhasználó heti lebontásban látja a tranzakcióit, 1 ha 
	 * a felhasználó havi lebontásban látja a tranzakcióit és 2 ha a felhasználó
	 * éves lebontásban látja a tranzakcióit.
	 */
	public int getLebontas() {
		return lebontas;
	}

	/**
	 * Beállítja hogy a felhasználó milyen lebontásban lássa a tranzakcióit.
	 * @param lebontas 0, ha a azt akarjuk hogy a felhasználó heti lebontásban lássa a tranzakcióit, 1 ha
	 * azt akarjuk hogy a felhasználó havi lebontásban lássa a tranzakcióit és 2 ha a azt akarjuk hogy a
	 * felhasználó éves lebontásban lássa a tranzakcióit.
	 */
	public void setLebontas(int lebontas) {
		this.lebontas = lebontas;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Felhasznalo [felasznalonev=" + felhasznalonev + ", jelszo=" + jelszo + ", egyenleg=" + egyenleg
				+ ", lebontas=" + lebontas + "]";
	}

	/**
	 * Visszaadja a felhasználó tranzakcióit.
	 * @return Egy {@link java.util.List} amiben a felhasználó tranzakciói szerepelnek.
	 */
	public List<Tranzakcio> getTranzakciok() {
		return tranzakciok;
	}
	
	/**
	 * Beállítja a felhasználó tranzakcióit.
	 * @param tranzakciok A {@link java.util.List} amiben a felhasználó tranzakciói vannak.
	 */
	public void setTranzakciok(List<Tranzakcio> tranzakciok) {
		this.tranzakciok = tranzakciok;
	}

}
