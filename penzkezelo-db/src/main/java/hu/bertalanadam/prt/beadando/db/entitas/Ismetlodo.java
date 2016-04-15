package hu.bertalanadam.prt.beadando.db.entitas;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Az adatbázisban egy ismétlődő tranzakcióhoz tartozó ismétlődési információkat reprezentáló osztály.
 * Ez egy entitás amit az adatbázisba leképezve egy ismetlodo nevű táblát kapunk a megfelelő oszlopokkal.
 * */
@Entity
@Table(name = "Ismetlodo")
public class Ismetlodo extends FoEntitas {

	/**
	 * Alapértelmezett szerializációs azonosító.
	 * */
	private static final long serialVersionUID = 1L;

	/**
	 * Üres konstruktor a szerializálhatóság miatt.
	 * */
	public Ismetlodo() {
	}

	/**
	 * Az a mennyiség (napban) ami megmondja hogy milyen időközönként ismétlődik egy tranzakció.
	 * */
	private Long ido;
	
	/**
	 * Megadja hogy egy ismétlődő tranzakció teljesült-e már.
	 * */
	private boolean teljesitett;
	
	/**
	 * Az a tranzakció aminek ismétlődnie kell.
	 * Egy tranzakcióhoz egy ismétlődés tartozik csak.
	 * */
	@OneToOne
	private Tranzakcio tranzakcio;

	/**
	 * Visszaadja hogy hány naponta ismétlődik egy tranzakció.
	 * @return Az ismétlődés gyakorisága napban.
	 */
	public Long getIdo() {
		return ido;
	}

	/**
	 * Beállítja hogy hány naponta ismétlődjön egy tranzakció.
	 * @param ido Az ismétlődés gyakorisága napban.
	 */
	public void setIdo(Long ido) {
		this.ido = ido;
	}

	/**
	 * Visszaadja hogy teljesítve van-e egy ismétlődő tranzakció.
	 * @return true ha teljesítve van, false ha nincs.
	 */
	public boolean isTeljesitett() {
		return teljesitett;
	}

	/**
	 * Beállítja hogy teljesítve legyen-e egy ismétlődő tranzakció.
	 * @param teljesitett true ha legyen az ismétlődő tranzakció teljesítve, false ha ne.
	 */
	public void setTeljesitett(boolean teljesitett) {
		this.teljesitett = teljesitett;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ismetlodo [ido=" + ido + ", teljesitett=" + teljesitett + "]";
	}

	/**
	 * Visszaadja azt a tranzakciót amihez ez az ismétlődő tartozik.
	 * @return Az ismétlődőhöz tartozó tranzakció
	 */
	public Tranzakcio getTranzakcio() {
		return tranzakcio;
	}

	/**
	 * Beállítja hogy melyik tranzakcióhoz tartozzon ez az ismétlődő.
	 * @param tranzakcio A beállítandó tranzakció.
	 */
	public void setTranzakcio(Tranzakcio tranzakcio) {
		this.tranzakcio = tranzakcio;
	}
}
