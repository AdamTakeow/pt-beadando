package hu.bertalanadam.prt.beadando.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javafx.beans.property.StringProperty;

/**
 * Egyszerű osztály a tranzakcióhoz tartozó adatok szállítására az adatbázis réteg
 * és a szolgáltatás réteg között (POJO). 
 */
public class TranzakcioVo implements Serializable{
	
	/**
	 * Alapértelmezett szerializációs azonosító.
	 * */
	private static final long serialVersionUID = 1L;

	/**
	 * Üres konstruktor a szerializálhatóság miatt.
	 * */
	public TranzakcioVo() {
	}
	
//	private StringProperty osszeg_prop;
//	private StringProperty kategoria_prop;
//	private StringProperty datum_prop;
	
	/**
	 * A tranzakció azonosítója.
	 */
	private Long id;

	/**
	 * Az a pénzösszeg amiről a tranzakció szól.
	 * */
	private Long osszeg;
	
	/**
	 * A tranzakció végrehajtásának dátuma.
	 * */
	private Date datum;
	
	/**
	 * A tranzakció részleteinek leírása.
	 * */
	private String leiras;
	
	/**
	 * Az a felhasználó akihez ez a tranzació tartozik.
	 * Több tranzakció is tartozhat egy felhasználóhoz.
	 * */
	private FelhasznaloVo felhasznalo;
	
	/**
	 * Az a kategória amihez ez a tranzakció tartozik.
	 * Több tranzakció sorolható egy kategóriába.
	 * */
	private KategoriaVo kategoria;
	
	/**
	 * Ha ez a tranzakció nem egy kiadás és nem is egy bevétel, hanem egy
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes}, akkor az ehhez
	 * a tranzakcióhoz tartozó lekötés.
	 * */
	private LekotesVo lekotes;
	
	/**
	 * Ha ez a tranzakció egy olyan trazakció ami bizonyos időközönként ismétlődik automatikusan,
	 * akkor az ehhez a tranzakcióhoz tartozó ismétlődési információk.
	 * */
	private IsmetlodoVo ismetlodo;
	
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

	/**
	 * Visszaadja a felhasználót akihez a tranzakció tartozik.
	 * @return A felhasználó akihez ez a tranzakció tartozik.
	 */
	public FelhasznaloVo getFelhasznalo() {
		return felhasznalo;
	}

	/**
	 * Beállítja hogy melyik felhasználóhoz tartozzon ez a tranzakció.
	 * @param felhasznalo A tranzakció új tulajdonosa.
	 */
	public void setFelhasznalo(FelhasznaloVo felhasznalo) {
		this.felhasznalo = felhasznalo;
	}

	/**
	 * Visszaadja hogy a tranzakció melyik kategóriába tartozik.
	 * @return A tranzakció kategóriája.
	 */
	public KategoriaVo getKategoria() {
		return kategoria;
	}

	/**
	 * Beállítja hogy a tranzakció melyik kategóriába tartozzon.
	 * @param kategoria A beállítandó kategória.
	 */
	public void setKategoria(KategoriaVo kategoria) {
		this.kategoria = kategoria;
	}

	/**
	 * Ha ez a tranzakció egy lekötés, akkor visszaadja a lekötésről szóló információkat tarzalmazó
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes} objektumot.
	 * @return A tranzakció lekötési információit tartalmazó objektum.
	 */
	public LekotesVo getLekotes() {
		return lekotes;
	}

	/**
	 * Beállítja a tranzakció lekötéséhez tartozó objektumot.
	 * @param lekotes A beállítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes} objektum.
	 */
	public void setLekotes(LekotesVo lekotes) {
		this.lekotes = lekotes;
	}

	/**
	 * Ha ez a tranzakció automatikusan ismétlődik, akkor az ismétlődést reprezentáló {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo} objektumot
	 * adja vissza.
	 * @return A tranzakcióhoz tartozó {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo} objektum.
	 */
	public IsmetlodoVo getIsmetlodo() {
		return ismetlodo;
	}

	/**
	 * Beállítja a tranzakcióhoz tartozó {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo} objektumot.
	 * @param ismetlodo A beállítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo} objektum.
	 */
	public void setIsmetlodo(IsmetlodoVo ismetlodo) {
		this.ismetlodo = ismetlodo;
	}

	/**
	 * Visszaadja a tranzakció azonosítóját.
	 * @return A tranzakció azonosítója.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Beállítja a tranzakció azonosítóját.
	 * @param id A beállítandó azonosító.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TranzakcioVo [id=" + id + ", osszeg=" + osszeg + ", datum=" + datum + ", leiras=" + leiras
				+ ", felhasznalo=" + felhasznalo + ", kategoria=" + kategoria + ", lekotes=" + lekotes + ", ismetlodo="
				+ ismetlodo + "]";
	}
}