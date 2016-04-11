package hu.bertalanadam.prt.beadando.db.entitas;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Felhasznalo")
public class Felhasznalo extends FoEntitas {

	private static final long serialVersionUID = 1L;

	public Felhasznalo() {
	}

	private String felasznalonev;
	private String jelszo;
	private Long egyenleg;
	private int lebontas;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="tranzakcio_id")
	private List<Tranzakcio> tranzakciok;
	

	public String getFelasznalonev() {
		return felasznalonev;
	}

	public void setFelasznalonev(String felasznalonev) {
		this.felasznalonev = felasznalonev;
	}

	public String getJelszo() {
		return jelszo;
	}

	public void setJelszo(String jelszo) {
		this.jelszo = jelszo;
	}

	public Long getEgyenleg() {
		return egyenleg;
	}

	public void setEgyenleg(Long egyenleg) {
		this.egyenleg = egyenleg;
	}

	public int getLebontas() {
		return lebontas;
	}

	public void setLebontas(int lebontas) {
		this.lebontas = lebontas;
	}

	@Override
	public String toString() {
		return "Felhasznalo [felasznalonev=" + felasznalonev + ", jelszo=" + jelszo + ", egyenleg=" + egyenleg
				+ ", lebontas=" + lebontas + "]";
	}

	public List<Tranzakcio> getTranzakciok() {
		return tranzakciok;
	}

	public void setTranzakciok(List<Tranzakcio> tranzakciok) {
		this.tranzakciok = tranzakciok;
	}

}
