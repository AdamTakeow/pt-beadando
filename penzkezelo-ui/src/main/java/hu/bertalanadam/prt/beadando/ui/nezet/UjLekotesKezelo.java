package hu.bertalanadam.prt.beadando.ui.nezet;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.LekotesSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.LekotesVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class UjLekotesKezelo {
	
	@Autowired
	private TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	@Autowired
	private KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	@Autowired
	private FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;
	
	@Autowired
	private LekotesSzolgaltatas lekotesSzolgaltatas;
	
	@Autowired
	private Otthonkezelo otthonkezelo;
	
	@FXML
	private TextField osszeg_bevitel;
	
	@FXML
	private TextField futamido_bevitel;

	@FXML
	private TextField kamat_bevitel;
	
	@FXML
	private DatePicker datum_kivalasztas;

	@FXML
	private Button vissza_gomb;
	
	@FXML
	private Label celszoveg;
	
	private FelhasznaloVo bejelentkezett_fh;
	
	@FXML
	protected void visszaGombKezeles(ActionEvent event) {
		((Stage)vissza_gomb.getScene().getWindow()).close();
	}
	
	@FXML
	private void initialize(){
		bejelentkezett_fh = otthonkezelo.getBejelentkezett_fh();
	}
	
	@FXML
	protected void mentesGombKezeles(ActionEvent event) {
		
		celszoveg.setText("");
		boolean mehet = true;
		
		// ha a felhasználó nem adott meg összeget
		long osszeg = 0;
		try {
			if( osszeg_bevitel.getText() == null ){
				celszoveg.setText(celszoveg.getText() + "Írja be az összeget!\n");
				mehet = false;
			} else {
				osszeg = Long.parseLong( osszeg_bevitel.getText() );
				if( osszeg == 0 ){
					throw new NumberFormatException();
				}
			}
		} catch (NumberFormatException nfe) {
			celszoveg.setText(celszoveg.getText() + "Az összeg mezőbe számot (nullától különbözőt) kell írni!\n");
			mehet = false;
		}
		
		long futamido = 0;
		try {
			if( futamido_bevitel.getText() == null ){
				celszoveg.setText(celszoveg.getText() + "Írja be az kamatot!\n");
				mehet = false;
			} else {
				futamido = Long.parseLong( futamido_bevitel.getText() );
				if( futamido == 0 ){
					throw new NumberFormatException();
				}
			}
		} catch (NumberFormatException nfe) {
			celszoveg.setText(celszoveg.getText() + "Az futamidő mezőbe számot (nullától különbözőt) kell írni!\n");
			mehet = false;
		}
		
		
		double kamat = 0;
		try {
			if( kamat_bevitel.getText() == null ){
				celszoveg.setText(celszoveg.getText() + "Írja be az kamatot!\n");
				mehet = false;
			} else {
				kamat = Double.parseDouble( kamat_bevitel.getText() );
				if( kamat == 0.0 ){
					throw new NumberFormatException();
				}
			}
		} catch (NumberFormatException nfe) {
			celszoveg.setText(celszoveg.getText() + "Az kamat mezőbe tört számot (nullától különbözőt) kell írni!\n");
			mehet = false;
		}
		
		if( mehet ){
			
			// új lekötés létrehozása
			LekotesVo ujLekotes = new LekotesVo();
			ujLekotes.setFutamido(futamido);
			ujLekotes.setKamat(kamat);
			ujLekotes.setOsszeg(osszeg);
			ujLekotes.setTeljesitett(false);
			
			// várható összeg kiszámolása
			// Kamatos kamat számítás
			double ertek = osszeg * ( Math.pow(( 1 + ((double)kamat/(double)100) ), futamido));
				
			ujLekotes.setVarhato( Math.round(ertek) );
			
			LekotesVo letezo_lek = lekotesSzolgaltatas.ujLekotesLetrehozas(ujLekotes);
			
			// levonjuk a felhasználó egyenlegéből a lekötött pénzt egy tranzakció formájában
			TranzakcioVo ujTranzakcio = new TranzakcioVo();
			ujTranzakcio.setOsszeg(-osszeg);
			
			if( datum_kivalasztas.getValue() == null ){
				ujTranzakcio.setDatum(LocalDate.now());
			} else {
				ujTranzakcio.setDatum(datum_kivalasztas.getValue());
			}
			ujTranzakcio.setLeiras("Lekötés");
			// lekötés kategóriája?

			// van már lekötés kategória?
			KategoriaVo letezo = kategoriaSzolgaltatas.getKategoriaByNev("Lekötés");
			if( letezo == null ){
			
				KategoriaVo kategoria = new KategoriaVo();
				kategoria.setNev("Lekötés");
//				kategoria.setTranzakciok(new ArrayList<TranzakcioVo>());
				kategoria.setFelhasznalok(new ArrayList<FelhasznaloVo>());
				kategoria.getFelhasznalok().add(bejelentkezett_fh);
				
				letezo = kategoriaSzolgaltatas.ujKategoriaLetrehozas(kategoria);
			}
						
			TranzakcioVo letezo_trz = tranzakcioSzolgaltatas.ujTranzakcioLetrehozas(ujTranzakcio);
			
			letezo_trz.setLekotes(letezo_lek);
			
			bejelentkezett_fh.getTranzakciok().add(letezo_trz);
			bejelentkezett_fh.getKategoriak().add(letezo);
			felhasznaloSzolgaltatas.frissitFelhasznalot(bejelentkezett_fh);
			
			letezo_trz.setKategoria(letezo);
			
			letezo_trz.setFelhasznalo(bejelentkezett_fh);
			
			tranzakcioSzolgaltatas.frissitTranzakciot(letezo_trz);
			
			otthonkezelo.adatFrissites();
			
			// bezárjuk ezt a dialogot
			((Stage)vissza_gomb.getScene().getWindow()).close();
		}
	}

}
