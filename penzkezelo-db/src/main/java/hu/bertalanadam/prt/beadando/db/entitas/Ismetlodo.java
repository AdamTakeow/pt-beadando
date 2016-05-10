package hu.bertalanadam.prt.beadando.db.entitas;

import java.time.LocalDate;

import javax.persistence.Entity;
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
	 * Az a dátum amikor az ismétlődő tranzakciót utóljára beszúrtuk, és ezzel arra a napra
	 * teljesítettük.
	 */
	private LocalDate utolsoBeszuras;

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
	 * Visszaadja azt a dátumot amikor az utolsó ismétlődő tranzakciót beszúrtuk.
	 * @return Az utolsó ismétlődő tranzakció beszúrásának dátuma.
	 */
	public LocalDate getUtolsoBeszuras() {
		return utolsoBeszuras;
	}

	/**
	 * Beállítja az utolsó ismétlődő tranzakció beszúrásának dátumát.
	 * @param utolsoBeszuras Az a dátum amikor az utolsó ismétlődő tranzakció be lett szúrva.
	 */
	public void setUtolsoBeszuras(LocalDate utolsoBeszuras) {
		this.utolsoBeszuras = utolsoBeszuras;
	}

	@Override
	public String toString() {
		return "Ismetlodo [ido=" + ido + ", utolsoBeszuras=" + utolsoBeszuras + "]";
	}
}
