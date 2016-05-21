// CHECKSTYLE:OFF
package hu.bertalanadam.prt.beadando.ui.model;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TranzakcioData {
	
	private long id;
	private StringProperty osszeg;
	private StringProperty datum;
	private StringProperty leiras;
	private StringProperty kategoria;
	
	public TranzakcioData(){
	}
	
	public TranzakcioData(long id, Long osszeg, LocalDate datum, String leiras, String kategoria) {
		this.setId(id);
		this.osszeg = new SimpleStringProperty( osszeg.toString() );
		this.datum = new SimpleStringProperty( datum.toString() );
		this.leiras = new SimpleStringProperty( leiras );
		this.kategoria = new SimpleStringProperty( kategoria );
	}

	public StringProperty getOsszeg() {
		return osszeg;
	}

	public void setOsszeg(String osszeg) {
		this.osszeg.set(osszeg);
	}
	
	public StringProperty getOsszegProperty(){
		return osszeg;
	}

	public String getDatum() {
		return datum.get();
	}

	public void setDatum(LocalDate datum) {
		this.datum.set(datum.toString());
	}
	
	public StringProperty getDatumProperty(){
		return datum;
	}

	public String getLeiras() {
		return leiras.get();
	}

	public void setLeiras(String leiras) {
		this.leiras.set(leiras);
	}
	
	public StringProperty getLeirasProperty(){
		return leiras;
	}

	public String getKategoria() {
		return kategoria.get();
	}

	public void setKategoria(String kategoria) {
		this.kategoria.set(kategoria);
	}
	
	public StringProperty getKategoriaProperty(){
		return kategoria;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "TranzakcioData [id=" + id + ", osszeg=" + osszeg + ", datum=" + datum + ", leiras=" + leiras
				+ ", kategoria=" + kategoria + "]";
	}

}
