package hu.bertalanadam.prt.beadando.vo;

import java.io.Serializable;
import java.util.List;

public class FelhasznaloVo implements Serializable {

	private static final long serialVersionUID = 1L;

	public FelhasznaloVo() {
	}

	private Long id;
	private String felasznalonev;
	private String jelszo;
	private Long egyenleg;
	private int lebontas;
	private List<TranzakcioVo> tranzakciok;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public List<TranzakcioVo> getTranzakciok() {
		return tranzakciok;
	}

	public void setTranzakciok(List<TranzakcioVo> tranzakciok) {
		this.tranzakciok = tranzakciok;
	}

	@Override
	public String toString() {
		return "FelhasznaloVo [id=" + id + ", felasznalonev=" + felasznalonev + ", jelszo=" + jelszo + ", egyenleg="
				+ egyenleg + ", lebontas=" + lebontas + ", tranzakciok=" + tranzakciok + "]";
	}

}
