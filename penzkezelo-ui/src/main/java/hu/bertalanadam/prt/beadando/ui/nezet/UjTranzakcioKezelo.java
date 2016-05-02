package hu.bertalanadam.prt.beadando.ui.nezet;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Component
public class UjTranzakcioKezelo {
	
	private static final SpringFxmlLoader loader = new SpringFxmlLoader();
	
	private static Logger logolo = LoggerFactory.getLogger(UjTranzakcioKezelo.class);
	
	@Autowired
	private KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	@Autowired
	private TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	@Autowired
	private Otthonkezelo otthonkezelo;
	
	@Autowired
	private FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;
	
	private FelhasznaloVo bejelentkezett_fh;
	
	@FXML
	private Text celszoveg;
	
	@FXML 
	private Button closeButton;
	
	@FXML
	private RadioButton bevetel_radiogomb;
	
	@FXML
	private RadioButton kiadas_radiogomb;
	
	@FXML
	private TextField osszeg_bevitel;
	
	@FXML
	private TextArea leiras_bevitel;
	
	@FXML
	private DatePicker datum_bevitel;
	
	@FXML
	private ComboBox<String> kategoria_bevitel;
	
	// hogy új kategória létrehozásánál ne vesszenek el a bevitt adatok.	

	private boolean kiadas_radiogomb_mentes;
	
	private boolean bevetel_radiogomb_mentes;
	
	private String osszeg_bevitel_mentes;
	
	private String leiras_bevitel_mentes;
	
	private Date datum_bevitel_mentes;
	

	@FXML
	private void initialize(){
		
		bejelentkezett_fh = otthonkezelo.getBejelentkezett_fh();		
		
		// felhozom az összes kategóriát az adatbázisból
		List<KategoriaVo> kategoriak = kategoriaSzolgaltatas.osszesKategoriaAFelhasznalohoz(bejelentkezett_fh);	
	
		ObservableList<String> list = FXCollections.observableArrayList();

		// belerakom a legördülőbe
		for (KategoriaVo kategoriaVo : kategoriak) {
			list.add(kategoriaVo.getNev());
		}
		
		kategoria_bevitel.setItems(list);
		
		kiadas_radiogomb.setSelected(kiadas_radiogomb_mentes);
		bevetel_radiogomb.setSelected(bevetel_radiogomb_mentes);
		osszeg_bevitel.setText(osszeg_bevitel_mentes);
		leiras_bevitel.setText(leiras_bevitel_mentes);
		
		if( datum_bevitel_mentes != null )
			datum_bevitel.setValue(datum_bevitel_mentes.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	}

	@FXML
	protected void bezarasKezelo(ActionEvent event) {
		
		logolo.info("UjTranzakcioKezelo: bezaras gomb kattintva!");
		
		kiadas_radiogomb_mentes = false;
		bevetel_radiogomb_mentes = false;
		osszeg_bevitel_mentes = null;
		leiras_bevitel_mentes = null;
		datum_bevitel_mentes = null;
		
		otthonkezelo.adatFrissites();
		((Stage)closeButton.getScene().getWindow()).close();
	}
	
	@FXML
	protected void ujKategoriaLetrehozasKezelo(ActionEvent event) {
		
		logolo.info("UjTranzakcioKezelo: Új kategória létrehozása gomb megnyomva!");
		
		// elmentjük az állapotot
		kiadas_radiogomb_mentes = kiadas_radiogomb.isSelected();
		bevetel_radiogomb_mentes = bevetel_radiogomb.isSelected();
		osszeg_bevitel_mentes = osszeg_bevitel.getText();
		leiras_bevitel_mentes = leiras_bevitel.getText();
		
		if( datum_bevitel.getValue() != null )
			datum_bevitel_mentes = Date.from(datum_bevitel.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

		GridPane pane = (GridPane)loader.load("/UjKategoriaFelulet.fxml");
		Scene scene = new Scene(pane);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	protected void mentesKezelo(ActionEvent event) {
		
		logolo.info("UjTranzakcioKezelo: Mentés gomb megnyomva");
		
		celszoveg.setText("");
		
		boolean mehet = true;
		
		boolean kiadas = false;
		// ha nincs kiválasztva egyetlen rádiógomb se
		if( !bevetel_radiogomb.isSelected() && !kiadas_radiogomb.isSelected() ){
			celszoveg.setText(celszoveg.getText() + "Válassza ki, hogy kiadás vagy bevétel!");
			mehet = false;
		} else {
			if( kiadas_radiogomb.isSelected() ){
				kiadas = true;
			}
		}
		
		// ha nem lett beleírva semmi az összeg mezőbe.
		long osszeg = 0;
		try {
			if( osszeg_bevitel.getText().length() == 0 ){
				celszoveg.setText(celszoveg.getText() + "Írja be az összeget!");
				mehet = false;
			} else {
				osszeg = Long.parseLong(osszeg_bevitel.getText());
			}
		} catch (NumberFormatException nfe) {
			celszoveg.setText(celszoveg.getText() + "Az összeg mezőbe számot kell írni!");
		}
		
		// ha nincs leírás írva, akkor üresen hagyjuk.
		String leiras = leiras_bevitel.getText();
		
		// ha nincs dátum kiválasztva akkor üres lesz
		Date datum = Date.from(datum_bevitel.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		if( datum == null ){
			datum = new Date();
		}
		
		// megnézzük hogy a felhasználó melyik kategóriát választotta
		String kategoria = kategoria_bevitel.getValue();
		
		if( mehet ){
			TranzakcioVo ujTranzakcio = new TranzakcioVo();
			if( kiadas ){
				ujTranzakcio.setOsszeg(-osszeg);				
			} else {
				ujTranzakcio.setOsszeg(osszeg);
			}
			ujTranzakcio.setLeiras(leiras);
			ujTranzakcio.setDatum(datum);
			
			// felhozom a létező kategóriát
			KategoriaVo trz_kategoriaja = kategoriaSzolgaltatas.getKategoriaByNev(kategoria);
						
			// létrehozom a tranzakciót az adatbázisban
			TranzakcioVo letezo_trz = tranzakcioSzolgaltatas.ujTranzakcioLetrehozas(ujTranzakcio);
			
			// elkérem a kategória tranzakcióit
			List<TranzakcioVo> trz_kategoriajanak_trzi = trz_kategoriaja.getTranzakciok();
			// hozzáadom a lementett tranzakciót a kategóriák meglévő tranzakcióihoz
			trz_kategoriajanak_trzi.add(letezo_trz);
			// beállítom a bővített listát a kategória tranzakcióinak
			trz_kategoriaja.setTranzakciok(trz_kategoriajanak_trzi);
			kategoriaSzolgaltatas.frissitKategoriat(trz_kategoriaja);
			
			// elkérem a felhasználótól a tranzakcióit
			List<TranzakcioVo> felh_trzi = bejelentkezett_fh.getTranzakciok();
			// hozzáadom a felhasználó tranzakciókhoz a frissített tranzakciót
			felh_trzi.add(letezo_trz);
			// beállítom a felhasználó tranzakcióit
			bejelentkezett_fh.setTranzakciok(felh_trzi);
			
			// elkérem a felhasználótól a kategóriáit
			List<KategoriaVo> felh_ktgi = bejelentkezett_fh.getKategoriak();
			// hozzáadom a felhasználó kategóriáihoz a kategóriát
			felh_ktgi.add(trz_kategoriaja);
			// beállítom a felhasználó kategóriáit.
			bejelentkezett_fh.setKategoriak(felh_ktgi);
			
			felhasznaloSzolgaltatas.frissitFelhasznalot(bejelentkezett_fh);
			
			
			// beállítom a tranzakciónak a frissített kategóriát
			letezo_trz.setKategoria(trz_kategoriaja);
			// beállítom a tranzakciónak a felhasználót
			letezo_trz.setFelhasznalo(bejelentkezett_fh);
			tranzakcioSzolgaltatas.frissitTranzakciot(letezo_trz);
			
//			kategoriaSzolgaltatas.frissitKategoriat(trz_kategoriaja);
//			tranzakcioSzolgaltatas.frissitTranzakciot(letezo_trz);
//			felhasznaloSzolgaltatas.frissitFelhasznalot(bejelentkezett_fh);
//			ujTranzakcio.setIsmetlodo(null);
			
			
			kiadas_radiogomb_mentes = false;
			bevetel_radiogomb_mentes = false;
			osszeg_bevitel_mentes = null;
			leiras_bevitel_mentes = null;
			datum_bevitel_mentes = null;	
			
			trz_kategoriaja = null;
			
			otthonkezelo.adatFrissites();
			
			((Stage)closeButton.getScene().getWindow()).close();
		}
	}
	
	public FelhasznaloVo getBejelentkezett_fh(){
		return bejelentkezett_fh;
	}
}
