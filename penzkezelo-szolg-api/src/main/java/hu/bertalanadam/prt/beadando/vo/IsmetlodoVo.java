package hu.bertalanadam.prt.beadando.vo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Egyszerű osztály az ismétlődőhöz tartozó adatok szállítására az egyes rétegek között (POJO). 
 */
public class IsmetlodoVo implements Serializable {

	/**
	 * Alapértelmezett szerializációs azonosító.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Üres konstruktor a szerializálhatóság miatt.
	 */
	public IsmetlodoVo() {
	}
	
	/**
	 * Az ismétlődő azonosítója.
	 */
	private Long id;

	/**
	 * Az a mennyiség (napban) ami megmondja hogy milyen időközönként ismétlődik
	 * egy tranzakció.
	 */
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
	 * Visszaadja az ismétlődő azonosítóját.
	 * @return Az ismétlődő azonosítója.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Beállítja az ismétlődő azonosítóját.
	 * @param id A beállítandó azonosító.
	 */
	public void setId(Long id) {
		this.id = id;
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
		return "IsmetlodoVo [id=" + id + ", ido=" + ido + ", utolsoBeszuras=" + utolsoBeszuras + "]";
	}
	
}
