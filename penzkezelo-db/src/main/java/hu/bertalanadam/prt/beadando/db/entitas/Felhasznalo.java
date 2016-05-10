package hu.bertalanadam.prt.beadando.db.entitas;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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
	 * A felhasználó felhasználóneve.
	 * Ez egy egyedi felhasználónév, csak egyszer szerepelhet.
	 * Ezt a {@link javax.persistence.Column Column} annotáció unique=true tagja biztosítja.
	 * */
	@Column(unique=true)
	private String felhasznalonev;
	
	/**
	 * A felhasználó jelszava.
	 * */
	private String jelszo;
	
	/**
	 * A felhasználó egyenlege, azaz az a pénzösszeg ami a felhasználónak rendelkezésére áll.
	 * */
	private Long egyenleg;
	
	/**
	 * A felhasználó kiadásra szánt összege. Ezt a felhasználó adhatja meg, ezt az alkalmazás 
	 * felhasználja a kiadások követéséhez.
	 */
	private Long kiadasraSzantPenz;
	
	
	/**
	 * A felhasználó számára ettől a dátumtól kezdődően fognak látszódni a tranzakciói.
	 */
	private LocalDate kezdoIdopont;
	
	/**
	 * A felhasználó számára ezzel a dátummal befejezőleg fognak látszódni a tranzakciói.
	 */
	private LocalDate vegIdopont;
	
	/**
	 * A felhasználóhoz tartozó tranzakciók (azaz a kiadásai, bevételei és lekötései).
	 * Minden egyes bevételt, kiadást és lekötést egy-egy tranzakcióként kezelünk, így 
	 * minden felhasználó rendelkezik egy listával, amelyben a tranzakciói szerepelnek.
	 * A {@link javax.persistence.OneToMany OneToMany} annotáció megmondja, hogy
	 * egy felhasználóhoz több tranzakció tartozik. A mappedBy taggal megadjuk hogy hol
	 * találja a Hibernate a konfigurációt ehhez az adattaghoz, ezesetben a kapcsolat másik
	 * oldalán a "felhasznalo" nevű adattagon lesz megadva a konfiguráció.
	 * */
	@OneToMany(mappedBy="felhasznalo")
	private List<Tranzakcio> tranzakciok;
	
	/**
	 * A felhasználóhoz tartozó kategóriák listája.
	 * Egy felhasználóhoz több kategória is tartozhat és egy kategória
	 * több felhasználóhoz is tartozhat, ezért ez az adattag a {@link javax.persistence.ManyToMany ManyToMany }
	 * annotációval van ellátva, aminek a konfigurációját a kapcsolat másik oldalán a "felhasznalok" adattagon
	 * találja meg a Hibernate. A fetch=FetchType.LAZY konfigurációval megmondjuk hogy az adatbázisból a felhasználóhoz
	 * a kategóriákat csak akkor kérdezze le, ha ténylegesen használja a kód.
	 */
	@ManyToMany(mappedBy="felhasznalok", fetch=FetchType.LAZY)
	private List<Kategoria> kategoriak;
	

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
	 * Visszaadja a felhasználó tranzakcióit.
	 * @return Egy {@link java.util.List List} amiben a felhasználó tranzakciói szerepelnek.
	 */
	public List<Tranzakcio> getTranzakciok() {
		return tranzakciok;
	}
	
	/**
	 * Beállítja a felhasználó tranzakcióit.
	 * @param tranzakciok A {@link java.util.List List} amiben a felhasználó tranzakciói vannak.
	 */
	public void setTranzakciok(List<Tranzakcio> tranzakciok) {
		this.tranzakciok = tranzakciok;
	}

	/**
	 * Visszaadja a felhasználó kategóriáit.
	 * @return A felhasználó kategóriái.
	 */
	public List<Kategoria> getKategoriak() {
		return kategoriak;
	}
	
	/**
	 * Beállítja a felhasználó kategóriáit.
	 * @param kategoriak A felhasználó kategóriái.
	 */
	public void setKategoriak(List<Kategoria> kategoriak) {
		this.kategoriak = kategoriak;
	}

	/**
	 * Visszaadja azt a dátumot amitől kezdődően látszanak a felhasználó tranzakciói.
	 * @return Az a dátum ahol a tranzakciók követése kezdődik.
	 */
	public LocalDate getKezdoIdopont() {
		return kezdoIdopont;
	}

	/**
	 * Beállítja azt a dátumot amitől kezdődően a felhasználó a tranzakcióit látni fogja.
	 * @param kezdoIdopont Az a dátum amitől számítva látszanak a tranzakciók.
	 */
	public void setKezdoIdopont(LocalDate kezdoIdopont) {
		this.kezdoIdopont = kezdoIdopont;
	}

	/**
	 * Visszaadja azt a dátumot ameddig a felhasználó tranzakciói látszanak.
	 * @return Az a dátum ameddig a felhasználó tranzakciói még látszanak.
	 */
	public LocalDate getVegIdopont() {
		return vegIdopont;
	}

	/**
	 * Beállítja hogy meddig lássa a felhasználó a tranzakcióit.
	 * @param vegIdopont Az a dátum ameddig a felhasználó a tranzakcióit még látja.
	 */
	public void setVegIdopont(LocalDate vegIdopont) {
		this.vegIdopont = vegIdopont;
	}
	
	/**
	 * Visszaadja a felhasználó havi kiadásra szánt pénzösszegét.
	 * @return A felhasználó havi kiadásra szánt pénzösszege.
	 */
	public Long getKiadasraSzantPenz() {
		return kiadasraSzantPenz;
	}

	/**
	 * Beállítja a felhasználó havi kiadásra szánt összegét.
	 * @param kiadasraSzantPenz A beállítandó pénzösszeg.
	 */
	public void setKiadasraSzantPenz(Long kiadasraSzantPenz) {
		this.kiadasraSzantPenz = kiadasraSzantPenz;
	}

	@Override
	public String toString() {
		return "Felhasznalo [felhasznalonev=" + felhasznalonev + ", jelszo=" + jelszo + ", egyenleg=" + egyenleg
				+ ", kiadasraSzantPenz=" + kiadasraSzantPenz + ", kezdoIdopont=" + kezdoIdopont + ", vegIdopont="
				+ vegIdopont + ", tranzakciok=" + tranzakciok + ", kategoriak=" + kategoriak + "]";
	}
	
}
