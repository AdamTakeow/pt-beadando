package hu.bertalanadam.prt.beadando.db.entitas;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Lekotes")
public class Lekotes extends FoEntitas {

	private static final long serialVersionUID = 1L;

	public Lekotes() {
	}

	private Long osszeg;
	private Long futamido;
	private int kamat;
	private Long varhato;
	private boolean teljesitett;
	
	@OneToOne
	private Tranzakcio tranzakcio;

	public Long getOsszeg() {
		return osszeg;
	}

	public void setOsszeg(Long osszeg) {
		this.osszeg = osszeg;
	}

	public Long getFutamido() {
		return futamido;
	}

	public void setFutamido(Long futamido) {
		this.futamido = futamido;
	}

	public int getKamat() {
		return kamat;
	}

	public void setKamat(int kamat) {
		this.kamat = kamat;
	}

	public Long getVarhato() {
		return varhato;
	}

	public void setVarhato(Long varhato) {
		this.varhato = varhato;
	}

	public boolean isTeljesitett() {
		return teljesitett;
	}

	public void setTeljesitett(boolean teljesitett) {
		this.teljesitett = teljesitett;
	}

	@Override
	public String toString() {
		return "Lekotes [osszeg=" + osszeg + ", futamido=" + futamido + ", kamat=" + kamat + ", varhato=" + varhato
				+ ", teljesitett=" + teljesitett + "]";
	}

	public Tranzakcio getTranzakcio() {
		return tranzakcio;
	}

	public void setTranzakcio(Tranzakcio tranzakcio) {
		this.tranzakcio = tranzakcio;
	}

}
