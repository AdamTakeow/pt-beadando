package hu.bertalanadam.prt.beadando.db.entitas;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author takeo
 *
 */
@Entity
@Table(name = "Tranzakcio")
public class Tranzakcio extends FoEntitas {

	private static final long serialVersionUID = 1L;

	public Tranzakcio() {
	}

	private Long osszeg;
	private LocalDate datum;
	private String leiras;
	
	@ManyToOne
	private Felhasznalo felhasznalo;
	
	@OneToOne
	private Kategoria kategoria;
	
	@OneToOne
	private Lekotes lekotes;
	
	@OneToOne
	private Ismetlodo ismetlodo;

	public Long getOsszeg() {
		return osszeg;
	}

	public void setOsszeg(Long osszeg) {
		this.osszeg = osszeg;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public String getLeiras() {
		return leiras;
	}

	public void setLeiras(String leiras) {
		this.leiras = leiras;
	}

	@Override
	public String toString() {
		return "Tranzakcio [osszeg=" + osszeg + ", datum=" + datum + ", leiras=" + leiras + "]";
	}

	public Felhasznalo getFelhasznalo() {
		return felhasznalo;
	}

	public void setFelhasznalo(Felhasznalo felhasznalo) {
		this.felhasznalo = felhasznalo;
	}

	public Kategoria getKategoria() {
		return kategoria;
	}

	public void setKategoria(Kategoria kategoria) {
		this.kategoria = kategoria;
	}

	public Lekotes getLekotes() {
		return lekotes;
	}

	public void setLekotes(Lekotes lekotes) {
		this.lekotes = lekotes;
	}

	public Ismetlodo getIsmetlodo() {
		return ismetlodo;
	}

	public void setIsmetlodo(Ismetlodo ismetlodo) {
		this.ismetlodo = ismetlodo;
	}

}
