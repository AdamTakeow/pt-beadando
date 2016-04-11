package hu.bertalanadam.prt.beadando.db.entitas;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Kategoria")
public class Kategoria extends FoEntitas {

	private static final long serialVersionUID = 1L;

	public Kategoria() {
	}

	private String nev;
	
	@OneToOne
	private Tranzakcio tranzakcio;

	public String getNev() {
		return nev;
	}

	public void setNev(String nev) {
		this.nev = nev;
	}

	@Override
	public String toString() {
		return "Kategoria [nev=" + nev + "]";
	}

	public Tranzakcio getTranzakcio() {
		return tranzakcio;
	}

	public void setTranzakcio(Tranzakcio tranzakcio) {
		this.tranzakcio = tranzakcio;
	}

}
