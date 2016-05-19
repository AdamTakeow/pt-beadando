package hu.bertalanadam.prt.beadando.ui.nezet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.IsmetlodoSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.IsmetlodoVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Component
public class UjTranzakcioKezelo {
	
	private static final SpringFxmlLoader loader = new SpringFxmlLoader();
	
	private static Logger logolo = LoggerFactory.getLogger(UjTranzakcioKezelo.class);
	
	// szolgáltatások
	@Autowired
	private KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	@Autowired
	private TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	@Autowired
	private FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;
	
	@Autowired
	private IsmetlodoSzolgaltatas ismetlodoSzolgaltatas;

	@Autowired
	private Otthonkezelo otthonkezelo;
	
	// kellékek
	
	// az aktuálisan bejelentkezett felhasználó
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
	private CheckBox ismetlodo_checkbox;
	@FXML
	private Spinner<Integer> ismetlodo_napvalaszto; 
	
	// hogy új kategória létrehozásánál ne vesszenek el a bevitt adatok, eltároljuk őket	

	private boolean kiadas_radiogomb_mentes;

	private boolean bevetel_radiogomb_mentes;
	
	private String osszeg_bevitel_mentes;
	
	private String leiras_bevitel_mentes;
	
	private LocalDate datum_bevitel_mentes;
	
	private boolean ismetlodik_mentes;
	
	private long spinner_ertek_mentes;
	
	@FXML
	public void ismetlodik(){
		if( ismetlodo_checkbox.isSelected() ){
			ismetlodo_napvalaszto.setDisable(false);
		} else {
			ismetlodo_napvalaszto.setDisable(true);
		}
	}
	
	// a dialog betöltődése előtt lefutó metódus
	@FXML
	private void initialize(){
		
		// spinner érték
		logolo.debug("Spinner ertektartomanyanak beallitasa: [1, 365]");
		SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365);
		ismetlodo_napvalaszto.setValueFactory(svf);
		
		// beállítjuk a bejelentkezett felhasználót
		logolo.debug("Bejelelentkezett felhasznalo beallitasa: " + otthonkezelo.getBejelentkezett_fh().getFelhasznalonev());
		bejelentkezett_fh = otthonkezelo.getBejelentkezett_fh();
		
		// felhozom azokat a kategóriákat az adatbázisból amelyek a felhasználóhoz tartoznak
		List<KategoriaVo> kategoriak = 
				kategoriaSzolgaltatas.felhasznaloOsszesKategoriaja(bejelentkezett_fh);
		
		ObservableList<String> list = FXCollections.observableArrayList();

		// belerakom a legördülőbe, hogy ki tudja onnan választani a megfelelőt
		logolo.debug("Legordulo feltoltese adatokkal:");
		for (KategoriaVo kategoriaVo : kategoriak) {
			list.add(kategoriaVo.getNev());
			logolo.debug("Adat: " + kategoriaVo.getNev());
		}
		
		// Beállítom a legördülő tartalmát
		kategoria_bevitel.setItems(list);
		
		// beállítjuk az elmetett állapotot
		logolo.debug("Elmentett adatok betoltese");
		kiadas_radiogomb.setSelected(kiadas_radiogomb_mentes);
		bevetel_radiogomb.setSelected(bevetel_radiogomb_mentes);
		osszeg_bevitel.setText(osszeg_bevitel_mentes);
		leiras_bevitel.setText(leiras_bevitel_mentes);
		ismetlodo_checkbox.setSelected(ismetlodik_mentes);
		
		if( ismetlodo_checkbox.isSelected() ){
			ismetlodo_napvalaszto.setDisable(false);
			ismetlodo_napvalaszto.increment((int)spinner_ertek_mentes-1);
		}
		
		if( datum_bevitel_mentes != null )
			datum_bevitel.setValue(datum_bevitel_mentes);
	}

	// a bezárás gombra kattintáskor lefutó metódus
	@FXML
	protected void bezarasKezelo(ActionEvent event) {
		
		logolo.info("Bezaras gomb kattintva!");
		
		// kitöröljük a mentést
		logolo.debug("Mentes torlese");
		kiadas_radiogomb_mentes = false;
		bevetel_radiogomb_mentes = false;
		osszeg_bevitel_mentes = null;
		leiras_bevitel_mentes = null;
		datum_bevitel_mentes = null;
		ismetlodik_mentes = false;
		spinner_ertek_mentes = 1;
		
		// bezárjk ezt a dialogot
		((Stage)closeButton.getScene().getWindow()).close();
	}
	
	// új kategória létrehozása gombra kattintva lefutó metódus
	@FXML
	protected void ujKategoriaLetrehozasKezelo(ActionEvent event) {
		
		logolo.info("Uj kategoria letrehozasa gomb megnyomva!");
		
		// elmentjük az állapotot
		logolo.debug("Adatok elmentese");
		kiadas_radiogomb_mentes = kiadas_radiogomb.isSelected();
		bevetel_radiogomb_mentes = bevetel_radiogomb.isSelected();
		osszeg_bevitel_mentes = osszeg_bevitel.getText();
		leiras_bevitel_mentes = leiras_bevitel.getText();
		ismetlodik_mentes = ismetlodo_checkbox.isSelected();
		if( ismetlodo_checkbox.isSelected() ){
			spinner_ertek_mentes = ismetlodo_napvalaszto.getValue();			
		}
		if( datum_bevitel.getValue() != null ){
			datum_bevitel_mentes = datum_bevitel.getValue();			
		}

		// betöltjük a kategória létrehozót
		GridPane pane = (GridPane)loader.load("/UjKategoriaFelulet.fxml");
		Scene scene = new Scene(pane);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Új kategória létrehozása");
		stage.setScene(scene);
		stage.centerOnScreen();
	}
	
	// a mentés gombra kattintáskor lefutó metódus
	@FXML
	protected void mentesKezelo(ActionEvent event) {
		
		logolo.info("Mentes gomb megnyomva");
		
		celszoveg.setText("");
		
		boolean mehet = true;
		
		boolean kiadas = false;
		// ha nincs kiválasztva egyetlen rádiógomb se
		if( !bevetel_radiogomb.isSelected() && !kiadas_radiogomb.isSelected() ){
			celszoveg.setText(celszoveg.getText() + "Válassza ki, hogy kiadás vagy bevétel!\n");
			mehet = false;
		} else {
			if( kiadas_radiogomb.isSelected() ){
				kiadas = true;
			}
		}
		
		// ha nem lett beleírva semmi az összeg mezőbe.
		long osszeg = 0;
		try {
			if( osszeg_bevitel.getText() == null ){
				celszoveg.setText(celszoveg.getText() + "Írja be az összeget!\n");
				mehet = false;
			} else {
				logolo.info("Parsolom a következő értéket: " + osszeg_bevitel.getText());
				osszeg = Long.parseLong(osszeg_bevitel.getText());
				if( osszeg == 0 ){
					throw new NumberFormatException();
				}
			}
		} catch (NumberFormatException nfe) {
			celszoveg.setText(celszoveg.getText() + "Az összeg mezőbe számot (nullától különbözőt) kell írni!\n");
			mehet = false;
		}
		
		// ha nincs leírás írva, akkor üresen hagyjuk.
		String leiras = leiras_bevitel.getText();
		
		// ha nincs dátum kiválasztva akkor az aktuális dátum lesz
		java.time.LocalDate datum = null;
		if( datum_bevitel.getValue() != null ){
			datum = datum_bevitel.getValue();
		}
		
		// megnézzük hogy a felhasználó melyik kategóriát választotta
		String kategoria = kategoria_bevitel.getValue();
		
		// ha nem lett megadva kategória, létrehozunk egy "nincs" nevűt
		if( kategoria == null ){
			// ha még nincs Nincs nevű kategória
			logolo.debug("Nincs meg ilyen kategoria: " + kategoria);
			if( kategoriaSzolgaltatas.keresKategoriat("Nincs") == null ){
				// létrehozzuk
				logolo.debug("Nincs meg Nincs nevu kategoria!");
				KategoriaVo ujkat = new KategoriaVo();
				ujkat.setNev("Nincs");
				
				// beállítom a felhasználóit
				List<FelhasznaloVo> felhasznalok = new ArrayList<>();
				felhasznalok.add(bejelentkezett_fh);
				ujkat.setFelhasznalok(felhasznalok);
	
				kategoriaSzolgaltatas.letrehozKategoriat(ujkat);
				
				kategoria = "Nincs";
				
			} else { // ha már van ilyen kategória 
				// felhozom ezt a létező kategóriát
				logolo.debug("Van mar Nincs nevu kategoria!");
				KategoriaVo letezo_kat = kategoriaSzolgaltatas.keresKategoriat("Nincs");

				boolean isEmpty =
						kategoriaSzolgaltatas.vanIlyenKategoriajaAFelhasznalonak(bejelentkezett_fh, letezo_kat);

				// ha nem szerepel, hozzáadom
				if( isEmpty ){
					letezo_kat.getFelhasznalok().add(bejelentkezett_fh);
					
					kategoriaSzolgaltatas.frissitKategoriat(letezo_kat);
				}
				kategoriaSzolgaltatas.frissitKategoriat(letezo_kat);
				
				kategoria = "Nincs";
			}
		}
		
		// bepipálta hogy ismétlődő, de nem adott meg intervallumot
		if( ismetlodo_checkbox.isSelected() && ismetlodo_napvalaszto.getValue() == null  ){
			celszoveg.setText(celszoveg.getText() + "Adja meg az ismétlődés gyakoriságát!\n");
			mehet = false;
		}
		
		// ha rendben lettek megadva az adato
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
			KategoriaVo trz_kategoriaja = kategoriaSzolgaltatas.keresKategoriat(kategoria);
						
			// létrehozom a tranzakciót az adatbázisban
			TranzakcioVo letezo_trz = tranzakcioSzolgaltatas.letrehozTranzakciot(ujTranzakcio);

			// létrehozzuk az új ismétlődőt, ha ismétlődik a tranzakció
			IsmetlodoVo ismetlodo = new IsmetlodoVo();
			
			if( ismetlodo_checkbox.isSelected() ){
				ismetlodo.setIdo( new Long( ismetlodo_napvalaszto.getValue() ) );
				ismetlodo.setUtolsoBeszuras(datum);
				ismetlodo = ismetlodoSzolgaltatas.letrehozIsmetlodot(ismetlodo);
			}
	
			bejelentkezett_fh.getTranzakciok().add(letezo_trz);
			
			bejelentkezett_fh.getKategoriak().add(trz_kategoriaja);
			
			felhasznaloSzolgaltatas.frissitFelhasznalot(bejelentkezett_fh);
			
			
			// beállítom a tranzakciónak a frissített kategóriát
			letezo_trz.setKategoria(trz_kategoriaja);
			// beállítom a tranzakciónak a felhasználót
			letezo_trz.setFelhasznalo(bejelentkezett_fh);
			
			if( ismetlodo_checkbox.isSelected() ){
				letezo_trz.setIsmetlodo(ismetlodo);				
			}
			
			tranzakcioSzolgaltatas.frissitTranzakciot(letezo_trz);	
			
			// kitöröljük a mentést
			logolo.debug("Mentett adatok torlese");
			kiadas_radiogomb_mentes = false;
			bevetel_radiogomb_mentes = false;
			osszeg_bevitel_mentes = null;
			leiras_bevitel_mentes = null;
			datum_bevitel_mentes = null;	
			ismetlodik_mentes = false;
			spinner_ertek_mentes = 1;
			
			trz_kategoriaja = null;

			// lefrissítjük a kezdőképernyőn az adatokat
			otthonkezelo.adatFrissites();
			
			// bezárjuk ezt a dialogot
			((Stage)closeButton.getScene().getWindow()).close();
		}
	}
	
	public FelhasznaloVo getBejelentkezett_fh(){
		return bejelentkezett_fh;
	}
}
