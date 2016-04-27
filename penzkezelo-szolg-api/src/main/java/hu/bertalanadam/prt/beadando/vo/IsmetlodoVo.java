package hu.bertalanadam.prt.beadando.vo;

import java.io.Serializable;

/**
 * Egyszerű osztály az ismétlődőhöz tartozó adatok szállítására az adatbázis réteg
 * és a szolgáltatás réteg között (POJO). 
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
	 * Az a mennyiség (napban) ami megmondja hogy milyen időközönként ismétlődik
	 * egy tranzakció.
	 */
	private Long ido;

	/**
	 * Megadja hogy egy ismétlődő tranzakció teljesült-e már.
	 */
	private boolean teljesitett;

	/**
	 * Az a tranzakció aminek ismétlődnie kell. Egy tranzakcióhoz egy ismétlődés
	 * tartozik csak.
	 */
	private TranzakcioVo tranzakcio;

	/**
	 * Visszaadja hogy hány naponta ismétlődik egy tranzakció.
	 * 
	 * @return Az ismétlődés gyakorisága napban.
	 */
	public Long getIdo() {
		return ido;
	}

	/**
	 * Beállítja hogy hány naponta ismétlődjön egy tranzakció.
	 * 
	 * @param ido
	 *            Az ismétlődés gyakorisága napban.
	 */
	public void setIdo(Long ido) {
		this.ido = ido;
	}

	/**
	 * Visszaadja hogy teljesítve van-e egy ismétlődő tranzakció.
	 * 
	 * @return true ha teljesítve van, false ha nincs.
	 */
	public boolean isTeljesitett() {
		return teljesitett;
	}

	/**
	 * Beállítja hogy teljesítve legyen-e egy ismétlődő tranzakció.
	 * 
	 * @param teljesitett
	 *            true ha legyen az ismétlődő tranzakció teljesítve, false ha
	 *            ne.
	 */
	public void setTeljesitett(boolean teljesitett) {
		this.teljesitett = teljesitett;
	}

	/**
	 * Visszaadja azt a tranzakciót amihez ez az ismétlődő tartozik.
	 * 
	 * @return Az ismétlődőhöz tartozó tranzakció
	 */
	public TranzakcioVo getTranzakcio() {
		return tranzakcio;
	}

	/**
	 * Beállítja hogy melyik tranzakcióhoz tartozzon ez az ismétlődő.
	 * 
	 * @param tranzakcio
	 *            A beállítandó tranzakció.
	 */
	public void setTranzakcio(TranzakcioVo tranzakcio) {
		this.tranzakcio = tranzakcio;
	}

	@Override
	public String toString() {
		return "IsmetlodoVo [ido=" + ido + ", teljesitett=" + teljesitett + ", tranzakcio=" + tranzakcio + "]";
	}

}
