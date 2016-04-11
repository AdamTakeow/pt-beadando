package hu.bertalanadam.prt.beadando.db.entitas;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Ismetlodo")
public class Ismetlodo extends FoEntitas {

	private static final long serialVersionUID = 1L;

	public Ismetlodo() {
	}

	private Long ido;
	private boolean teljesitett;
	
	@OneToOne
	private Tranzakcio tranzakcio;

	public Long getIdo() {
		return ido;
	}

	public void setIdo(Long ido) {
		this.ido = ido;
	}

	public boolean isTeljesitett() {
		return teljesitett;
	}

	public void setTeljesitett(boolean teljesitett) {
		this.teljesitett = teljesitett;
	}

	@Override
	public String toString() {
		return "Ismetlodo [ido=" + ido + ", teljesitett=" + teljesitett + "]";
	}

	public Tranzakcio getTranzakcio() {
		return tranzakcio;
	}

	public void setTranzakcio(Tranzakcio tranzakcio) {
		this.tranzakcio = tranzakcio;
	}

}
