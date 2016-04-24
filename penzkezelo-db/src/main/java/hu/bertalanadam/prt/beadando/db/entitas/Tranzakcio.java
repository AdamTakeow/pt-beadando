package hu.bertalanadam.prt.beadando.db.entitas;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Az adatbázisban egy tranzakciót reprezentáló osztály.
 * Ez egy entitás amit az adatbázisba leképezve egy tranzakcio nevű táblát kapunk a megfelelő oszlopokkal.
 * */
@Entity
@Table(name = "Tranzakcio")
public class Tranzakcio extends FoEntitas {

	/**
	 * Alapértelmezett szerializációs azonosító.
	 * */
	private static final long serialVersionUID = 1L;

	/**
	 * Üres konstruktor a szerializálhatóság miatt.
	 * */
	public Tranzakcio() {
	}

	/**
	 * Az a pénzösszeg amiről a tranzakció szól.
	 * */
	private Long osszeg;
	
	/**
	 * A tranzakció végrehajtásának dátuma.
	 * */
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datum;
	
	/**
	 * A tranzakció részleteinek leírása.
	 * */
	@Lob
	private String leiras;
	
	/**
	 * Az a felhasználó akihez ez a tranzació tartozik.
	 * Több tranzakció is tartozhat egy felhasználóhoz.
	 * */
	@ManyToOne
	private Felhasznalo felhasznalo;
	
	/**
	 * Az a kategória amihez ez a tranzakció tartozik.
	 * Több tranzakció sorolható egy kategóriába.
	 * */
	@ManyToOne
	private Kategoria kategoria;
	
	/**
	 * Ha ez a tranzakció nem egy kiadás és nem is egy bevétel, hanem egy
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes}, akkor az ehhez
	 * a tranzakcióhoz tartozó lekötés.
	 * */
	@OneToOne
	private Lekotes lekotes;
	
	/**
	 * Ha ez a tranzakció egy olyan trazakció ami bizonyos időközönként ismétlődik automatikusan,
	 * akkor az ehhez a tranzakcióhoz tartozó ismétlődési információk.
	 * */
	@OneToOne
	private Ismetlodo ismetlodo;

	/**
	 * Visszaadja azt a pénzösszeget amelyről a tranzakció szól.
	 * @return A tranzakció pénzösszege.
	 */
	public Long getOsszeg() {
		return osszeg;
	}

	/**
	 * Beállítja azt a pénzösszeget amelyről a tranzakció szól.
	 * @param osszeg A beállítandó pénzösszeg.
	 */
	public void setOsszeg(Long osszeg) {
		this.osszeg = osszeg;
	}

	/**
	 * Visszaadja a tranzakció teljesítésének dátumát.
	 * @return A tranzakció dátuma.
	 */
	public Date getDatum() {
		return datum;
	}

	/**
	 * Beállítja a tranzakció teljesítésének dátumát.
	 * @param datum A beállítandó dátum.
	 */
	public void setDatum(Date datum) {
		this.datum = datum;
	}

	/**
	 * Visszaadja a tranzakció részletes leírását.
	 * @return A tranzakció leírása.
	 */
	public String getLeiras() {
		return leiras;
	}

	/**
	 * Beállítja a tranzakció részletes leírását.
	 * @param leiras A beállítandó leírás szövege.
	 */
	public void setLeiras(String leiras) {
		this.leiras = leiras;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tranzakcio [osszeg=" + osszeg + ", datum=" + datum + ", leiras=" + leiras + "]";
	}

	/**
	 * Visszaadja a felhasználót akihez a tranzakció tartozik.
	 * @return A felhasználó akihez ez a tranzakció tartozik.
	 */
	public Felhasznalo getFelhasznalo() {
		return felhasznalo;
	}

	/**
	 * Beállítja hogy melyik felhasználóhoz tartozzon ez a tranzakció.
	 * @param felhasznalo A tranzakció új tulajdonosa.
	 */
	public void setFelhasznalo(Felhasznalo felhasznalo) {
		this.felhasznalo = felhasznalo;
	}

	/**
	 * Visszaadja hogy a tranzakció melyik kategóriába tartozik.
	 * @return A tranzakció kategóriája.
	 */
	public Kategoria getKategoria() {
		return kategoria;
	}

	/**
	 * Beállítja hogy a tranzakció melyik kategóriába tartozzon.
	 * @param kategoria A beállítandó kategória.
	 */
	public void setKategoria(Kategoria kategoria) {
		this.kategoria = kategoria;
	}

	/**
	 * Ha ez a tranzakció egy lekötés, akkor visszaadja a lekötésről szóló információkat tarzalmazó
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes} objektumot.
	 * @return A tranzakció lekötési információit tartalmazó objektum.
	 */
	public Lekotes getLekotes() {
		return lekotes;
	}

	/**
	 * Beállítja a tranzakció lekötéséhez tartozó objektumot.
	 * @param lekotes A beállítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes} objektum.
	 */
	public void setLekotes(Lekotes lekotes) {
		this.lekotes = lekotes;
	}

	/**
	 * Ha ez a tranzakció automatikusan ismétlődik, akkor az ismétlődést reprezentáló {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo} objektumot
	 * adja vissza.
	 * @return A tranzakcióhoz tartozó {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo} objektum.
	 */
	public Ismetlodo getIsmetlodo() {
		return ismetlodo;
	}

	/**
	 * Beállítja a tranzakcióhoz tartozó {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo} objektumot.
	 * @param ismetlodo A beállítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo} objektum.
	 */
	public void setIsmetlodo(Ismetlodo ismetlodo) {
		this.ismetlodo = ismetlodo;
	}
}
