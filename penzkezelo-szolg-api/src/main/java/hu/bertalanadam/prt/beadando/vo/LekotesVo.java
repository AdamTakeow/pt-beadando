package hu.bertalanadam.prt.beadando.vo;

import java.io.Serializable;

/**
 * Egyszerű osztály a lekötéshez tartozó adatok szállítására az adatbázis réteg
 * és a szolgáltatás réteg között (POJO). 
 */
public class LekotesVo implements Serializable {
	
	/**
	 * Alapértelmezett szerializációs azonosító.
	 * */
	private static final long serialVersionUID = 1L;

	/**
	 * Üres konstruktor a szerializálhatóság miatt.
	 * */
	public LekotesVo() {
	}
	
	/**
	 * A lekötés azonosítója.
	 */
	private Long id;

	/**
	 * Az a pénzösszeg amelyről a lekötés szól.
	 * */
	private Long osszeg;
	
	/**
	 * Az a mennyiség (nap) amely megahározza hogy mennyi időre szeretnénk lekötni a pénzünket.
	 * */
	private Long futamido;
	
	/**
	 * Az a mennyiség (százalék) ami meghatározza hogy mekkora kamattal kötjük le a pénzünket.
	 * */
	private double kamat;
	
	/**
	 * Az a pénzösszeg ami a lekötés lejárta után várható.
	 * */
	private Long varhato;
	
	/**
	 * Jelzi hogy az adott lekötés lejárt-e már vagy sem.
	 * */
	private boolean teljesitett;

	/**
	 * Visszaadja hogy mekkora összegről szól a lekötés.
	 * @return A lekötés összege.
	 */
	public Long getOsszeg() {
		return osszeg;
	}

	/**
	 * Beállítja hogy mekkora összegről szóljon a lekötés.
	 * @param osszeg A beállítandó összeg.
	 */
	public void setOsszeg(Long osszeg) {
		this.osszeg = osszeg;
	}

	/**
	 * Visszaadja hogy hány napról szól a lekötés.
	 * @return Az a mennyiség (napban) amennyi ideig tart a lekötés.
	 */
	public Long getFutamido() {
		return futamido;
	}

	/**
	 * Beállítja hogy hány napról szóljon a lekötés.
	 * @param futamido A beállítandó mennyiség (napban).
	 */
	public void setFutamido(Long futamido) {
		this.futamido = futamido;
	}

	/**
	 * Visszaadja hogy milyen kamatra kötöttük le a pénzünket (százalékban).
	 * @return A lekötés kamata (százalékban).
	 */
	public double getKamat() {
		return kamat;
	}

	/**
	 * Beállítja hogy milyen kamata legyen a lekötésnek.
	 * @param kamat A beállítandó kamat (százalékban).
	 */
	public void setKamat(double kamat) {
		this.kamat = kamat;
	}

	/**
	 * Visszaadja azt a pénzösszeget amelyet a lekötés lejártával kapunk.
	 * @return A várhaó pénzösszeg.
	 */
	public Long getVarhato() {
		return varhato;
	}

	/**
	 * Beállítja a várható összegét a lekötésnek.
	 * @param varhato A beállítandó összeg.
	 */
	public void setVarhato(Long varhato) {
		this.varhato = varhato;
	}

	/**
	 * Visszaadja hogy ez a lekötés teljesített-e.
	 * @return true ha teljesített, false ha nem teljesített.
	 */
	public boolean isTeljesitett() {
		return teljesitett;
	}

	/**
	 * Beállítja hogy ez a lekötés teljesített-e.
	 * @param teljesitett A beállítandó true vagy false érték.
	 */
	public void setTeljesitett(boolean teljesitett) {
		this.teljesitett = teljesitett;
	}

	/**
	 * Visszaadja a lekötés azonosítóját.
	 * @return A lekötés azonosítója.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Beállítja a lekötés azonosítóját.
	 * @param id A beállítandó azonosító.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LekotesVo [id=" + id + ", osszeg=" + osszeg + ", futamido=" + futamido + ", kamat=" + kamat
				+ ", varhato=" + varhato + ", teljesitett=" + teljesitett + "]";
	}
}
