package hu.bertalanadam.prt.beadando.ui.nezet;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class UjTranzakcioKezelo {
	
	private static final SpringFxmlLoader loader = new SpringFxmlLoader();
	
	@Autowired
	private TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	@Autowired
	private KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	@Autowired
	private BejelentkezesKezelo bejelentkezesKezelo;
	
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
	
	@FXML
	private void initialize(){
		bejelentkezett_fh = bejelentkezesKezelo.getBejelentkezett_fh();
		
		List<KategoriaVo> kategoriak = kategoriaSzolgaltatas.getAllKategoria();
		
		
		ObservableList<String> list = FXCollections.observableArrayList();
	
		for (KategoriaVo kategoriaVo : kategoriak) {
			list.add(kategoriaVo.getNev());
		}
		
		kategoria_bevitel.setItems(list);
	}

	@FXML
	protected void bezarasKezelo(ActionEvent event) {
		((Stage)closeButton.getScene().getWindow()).close();
	}
	
	@FXML
	protected void ujKategoriaLetrehozasKezelo(ActionEvent event) {
		GridPane pane = (GridPane)loader.load("/UjKategoriaFelulet.fxml");
		
		Scene scene = new Scene(pane);
	    
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
//		stage.setTitle("Új kategória létrehozása");
		
		stage.setScene(scene);
	}
	
	@FXML
	protected void mentesKezelo(ActionEvent event) {
		// megnézzük hogy ki van-e minden töltve rendesen!
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
		
		// a kategória listában a default uncategorised lesz ezért elég csak elkérni.
		String kategoria = kategoria_bevitel.getValue();
		
		if( mehet ){
			TranzakcioVo ujTranzakcio = new TranzakcioVo();
			if( kiadas ){
				ujTranzakcio.setOsszeg(osszeg);				
			} else {
				ujTranzakcio.setOsszeg(-osszeg);
			}
			ujTranzakcio.setLeiras(leiras);
			ujTranzakcio.setDatum(datum);
			
			// és ha van már?
			ujTranzakcio.setKategoria(kategoriaSzolgaltatas.getKategoriaByNev(kategoria));
			
			ujTranzakcio.setFelhasznalo(bejelentkezett_fh);
//			ujTranzakcio.setIsmetlodo(null);
			
			tranzakcioSzolgaltatas.ujTranzakcioLetrehozas(ujTranzakcio);
			
			((Stage)closeButton.getScene().getWindow()).close();
		}
	}
}
