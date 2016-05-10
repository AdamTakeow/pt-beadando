package hu.bertalanadam.prt.beadando.ui.nezet;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.LekotesSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader;
import hu.bertalanadam.prt.beadando.ui.model.TranzakcioData;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class Otthonkezelo {

	private static final SpringFxmlLoader loader = new SpringFxmlLoader();
	
	private static Logger logolo = LoggerFactory.getLogger(Otthonkezelo.class);
	
	// szolgáltatások
	
	@Autowired
	TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	@Autowired
	FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;
	
	@Autowired
	LekotesSzolgaltatas lekotesSzolgaltatas;
	
	@Autowired
	private BejelentkezesKezelo bejelentkezesKezelo;
	
	// az aktuálisan bejelentkezett felhasználó
	private FelhasznaloVo bejelentkezett_fh;
	
	// üdvözlet a felhasználónak
	@FXML
	private Text welcome_user;
	
	// a felhasználó egyenlege
	@FXML
	private Text egyenleg_text;
	
	// a felhasználó összes bevétele
	@FXML
	private Text sum_bevetel;
	
	// a felhasználó összes kiadása
	@FXML
	private Text sum_kiadas;
	
	// a tranzakciókat tartalmazó táblázat
	@FXML
	private TableView<TranzakcioData> tranzakcioTable;
	
	// a tranzakciókat tartalmazó táblázat összeg oszlopa
	@FXML
	private TableColumn<TranzakcioData, String> osszegOszlop;
	
	// a tranzakciókat tartalmazó táblázat dátum oszlopa
	@FXML
	private TableColumn<TranzakcioData, String> datumOszlop;
	
	// a tranzakciókat tartalmazó táblázat kategória oszlopa
	@FXML
	private TableColumn<TranzakcioData, String> kategoriaOszlop;
	
	// a bevételeket szemléltető diagram
	@FXML
	private PieChart bev_diagram;
	
	// a kiadásokat szemléltető diagram
	@FXML
	private PieChart kiad_diagram;
	
	@FXML
	private DatePicker lebontas_innentol;
	
	@FXML
	private DatePicker lebontas_idaig;
	
	@FXML
	private Button lekotesGomb;
	
	@FXML
	private Button beallitasok_gomb;
	
	@FXML
	private Text ennyitKolthetekMeg;
	
	// a tranzakciókat tartalmazó táblázat adatait tartalmazó lista
	private ObservableList<TranzakcioData> tranzakcioTablazatAdatok = FXCollections.observableArrayList();
	
	// a bevételeket szemléltető diagram adatait tartalmazó lista
	private ObservableList<PieChart.Data> bev_diagramAdatok = FXCollections.observableArrayList();
	
	// a kiadásokat szemléltető diagram adatait tartalmazó lista
	private ObservableList<PieChart.Data> kiad_diagramAdatok = FXCollections.observableArrayList();
	
	// a tranzakciólistából éppen kiválasztott tranzakció
	private TranzakcioVo kivalasztott_trz;
	
	private String[] pieColors = new String[]{"Blue","Black","Yellow","Red","Green","Orange",
			"Pink","Purple","White","Brown","Cyan"};
	
	// az kezdőképernyő adatait frissíti
	public void adatFrissites(){
		
		// újból felhozza az adatbázisból a felhasználót hogy a módosított adatai frissek legyenek a felületen.
		bejelentkezett_fh = felhasznaloSzolgaltatas.findByFelhasznalonev(bejelentkezesKezelo.getBejelentkezett_fh().getFelhasznalonev());

		// beállítjuk a dátum választókat a felhasználónak megfelelőre
		lebontas_innentol.setValue(bejelentkezett_fh.getKezdoIdopont());
		lebontas_idaig.setValue(bejelentkezett_fh.getVegIdopont());
		
		bejelentkezett_fh = felhasznaloSzolgaltatas.frissitFelhasznalot(bejelentkezett_fh);
		
		// lefrissítjük a táblázatot
		List<TranzakcioVo> felh_tranzakcioi = 
				tranzakcioSzolgaltatas.osszesTranzakcioAFelhasznalohoz(bejelentkezett_fh);

		// újra lefrissítem a fazba mostmár
		bejelentkezett_fh = felhasznaloSzolgaltatas.findByFelhasznalonev(bejelentkezesKezelo.getBejelentkezett_fh().getFelhasznalonev());
		
		// kiírjuk az aktuális egyenlegét
		long egyenleg = bejelentkezett_fh.getEgyenleg();
		if( egyenleg < 0 ){
			egyenleg_text.setFill(Color.web("#ff0303"));
		} else {
			egyenleg_text.setFill(Color.web("#15bcb1"));
		}
		egyenleg_text.setText("" + egyenleg + " Ft");
		
		// kiszámoljuk hogy mennyit költhet még a hónapban
		ennyitKolthetekMeg.setText( "" + felhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(bejelentkezett_fh) + " Ft");
		
		// kiszámoljuk az összes bevételét és kiírjuk
		long osszes_bev = felhasznaloSzolgaltatas.osszesBevetelAFelhasznalohoz(bejelentkezett_fh);
		sum_bevetel.setText("" + osszes_bev + " Ft");
		
		// kiszámoljuk az összes kiadását és kiírjuk
		long osszes_kiad = felhasznaloSzolgaltatas.osszesKiadasAFelhasznalohoz(bejelentkezett_fh);
		sum_kiadas.setText("" + osszes_kiad + " Ft");
		
		
		// ha nem üres a lista akkor kitöröljük a tartalmát
		if( !tranzakcioTablazatAdatok.isEmpty() ){
			tranzakcioTablazatAdatok.clear();
		}
		// végigmegyünk a felhasználó tranzakcióin
		for (TranzakcioVo tranzakcioVo : felh_tranzakcioi) {

			// hozzáadjuk a táblázat adatlistájához a tranzakciókat modellként
			tranzakcioTablazatAdatok.add(
					new TranzakcioData(
						tranzakcioVo.getId(),
						tranzakcioVo.getOsszeg(),
						tranzakcioVo.getDatum(),
						tranzakcioVo.getLeiras(),
						tranzakcioVo.getKategoria().getNev() )
			);	
		}
		
		
		// frissítjük a diagramok adatait is.
		// újraszámoljuk a bevételeket szemléltető diagram adatait 
		Map<String, Long> bev_adatok = felhasznaloSzolgaltatas.bevDiagramAdatokSzamitasaFelhasznalohoz(bejelentkezett_fh);
		
		// ha nem üres a diagram adatait tartalmazó lista
		if( !bev_adatok.isEmpty() ){
			// kitöröljük a tartalmát 
			bev_diagramAdatok.clear();
			// hozzáadjuk újból a frissen számolt adatokat
			for (Map.Entry<String, Long> elem : bev_adatok.entrySet() ) {
				bev_diagramAdatok.add( new PieChart.Data(elem.getKey(), elem.getValue()) );			
			}			
		}
		
//		int i = 0;
//		for (PieChart.Data data : bev_diagramAdatok) {
//		  data.getNode().setStyle(
//		    "-fx-pie-color: " + pieColors[i % pieColors.length] + ";"
//		  );
//		  i++;
//		}
		
		// újraszámoljuk a kiadásokat szemléltető diagram adatait
		Map<String, Long> kiad_adatok = felhasznaloSzolgaltatas.kiadDiagramAdatokSzamitasaFelhasznalohoz(bejelentkezett_fh);
		
		// ha nem üres a diagram adatait tartalmazó lista
		if( !kiad_adatok.isEmpty() ){
			// kitöröljük a tartalmát
			kiad_diagramAdatok.clear();
			// hozzáadjuk újból a frissen számolt adatokat
			for (Map.Entry<String, Long> elem : kiad_adatok.entrySet() ) {
				kiad_diagramAdatok.add( new PieChart.Data(elem.getKey(), elem.getValue()) );			
			}			
		}
		
//		i = 0;
//		for (PieChart.Data data : kiad_diagramAdatok) {
//		  data.getNode().setStyle(
//		    "-fx-pie-color: " + pieColors[i % pieColors.length] + ";"
//		  );
//		 
//		  i++;
//		}

		if (lekotesSzolgaltatas.vanLekotesAFelhasznalohoz(bejelentkezett_fh, bejelentkezett_fh.getTranzakciok()) ){
			lekotesGomb.setText("Lekötés megtekintése");
		} else {
			lekotesGomb.setText("Új lekötés");
		}
	}
	
	@FXML
	protected void beallitasokGombKezelo( ActionEvent event ){
		
		BorderPane pane = (BorderPane)loader.load("/BeallitasokFelulet.fxml");
		Scene scene = new Scene(pane);

		Stage stage = new Stage();
		stage.setTitle("Beállítások");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner((Stage)((Node) event.getSource()).getScene().getWindow());
		stage.setScene(scene);
		stage.show();
	}
	
	// a képernyő betöltődése előtt lefutó metódus
	@FXML
	private void initialize(){
		
		adatFrissites();
		
		// üdvözöljük a felhasználót
		welcome_user.setText("Üdvözlöm, " + bejelentkezett_fh.getFelhasznalonev());
		
		// beállítjuk hogy az összeg oszlopba honnan származzanak az értékek
		osszegOszlop.setCellValueFactory( celldata -> celldata.getValue().getOsszegProperty() );
		// beállítjuk hogy a dátum oszlopba honnan származzanak az értékek
		datumOszlop.setCellValueFactory( celldata -> celldata.getValue().getDatumProperty() );
		// beállítjuk hogy a kategória oszlopba honnan származzanak az értékek
		kategoriaOszlop.setCellValueFactory( celldata -> celldata.getValue().getKategoriaProperty() );
		
		// beállítjuk a táblázatnak hogy melyik adatlistát használja
		tranzakcioTable.setItems(tranzakcioTablazatAdatok);
		
		// beállítjuk hogy mi történjen a táblázat egy sorára kattintva
		tranzakcioTable.getSelectionModel()
					   .selectedItemProperty()
		               .addListener( (observable, oldValue, newValue) -> showTranzakcioData(newValue) );
		
		osszegOszlop.setComparator( (c1, c2) -> { Long c_1 = Long.parseLong(c1);
												  Long c_2 = Long.parseLong(c2);
												  return c_1.compareTo(c_2);} );
		
		
//		Color.
		bev_diagram.setTitle("Bevételek kategóriánként");
		bev_diagram.setData(bev_diagramAdatok);
		
//		int i = 0;
//		for (PieChart.Data data : bev_diagramAdatok) {
//		  data.getNode().setStyle(
//		    "-fx-pie-color: " + pieColors[i % pieColors.length] + ";"
//		  );
//		  i++;
//		}
		
		kiad_diagram.setTitle("Kiadások kategóriánként");
		kiad_diagram.setData(kiad_diagramAdatok);
		
//		i = 0;
//		for (PieChart.Data data : kiad_diagramAdatok) {
//		  data.getNode().setStyle(
//		    "-fx-pie-color: " + pieColors[i % pieColors.length] + ";"
//		  );
//		  i++;
//		}
		
	}
	
	// ez történik a táblázat egy sorára kattintva
	private void showTranzakcioData(TranzakcioData tData){
		if( tData != null ){
			// beállíjuk hogy melyik volt a kiválasztott tranzakció
			logolo.info("tData id FONTOS: " + tData.getId());
			kivalasztott_trz = tranzakcioSzolgaltatas.findById(tData.getId());
			logolo.info("Kiválasztott tranzakció azonosítója: " + kivalasztott_trz.getId());
			
			// betöltjük a dialog-ot
			BorderPane pane = (BorderPane)loader.load("/TranzakcioReszletezo.fxml");
			Scene scene = new Scene(pane);

			Stage stage = new Stage();
			stage.setTitle("Tranzakció részletei");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setScene(scene);
			stage.show();
			
			// majd kivesszük a kijelölést a táblázatból
			Platform.runLater(new Runnable() {
			    @Override public void run() {
			    	tranzakcioTable.getSelectionModel().clearSelection();
			}});
		}
	}
	
	// ez a metódus fut le a kijelentkezés gombra kattintva
	@FXML
	protected void kijelentkezesGombKezelo(ActionEvent event) {
		
		logolo.info("Kijelentkezés gomb megnyomva");
		
		Parent parent = (Parent)loader.load("/BejelentkezoFelulet.fxml");
		Scene scene = new Scene(parent);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Bejelentkezés");
		stage.setScene(scene);
	}
	
	// ez a metódus fut le az új lekötés gombra kattintva
	@FXML
	protected void ujLekotesKezelo(ActionEvent event) {
		logolo.info("Új lekötés gomb megnyomva");
		
		if (lekotesSzolgaltatas.vanLekotesAFelhasznalohoz(bejelentkezett_fh, bejelentkezett_fh.getTranzakciok()) ){
			// ha már van lekötésünk
			
			BorderPane pane = (BorderPane)loader.load("/MeglevoLekotesFelulet.fxml");
			Scene scene = new Scene(pane);
			
			Stage stage = new Stage();
			stage.setTitle("Lekötés Részletei");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner((Stage)((Node) event.getSource()).getScene().getWindow());
			stage.setScene(scene);
			stage.show();
			
		} else {
			// ha még nincs lekötésünk
			BorderPane pane = (BorderPane)loader.load("/UjLekotesFelulet.fxml");
			Scene scene = new Scene(pane);
			
			Stage stage = new Stage();
			stage.setTitle("Új Lekötés létrehozása");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner((Stage)((Node) event.getSource()).getScene().getWindow());
			stage.setScene(scene);
			stage.show();
		}
	}
	
	// ez a metódus fut le az új tranzakció gombra kattintva
	@FXML
	protected void ujTranzakcioKezelo(ActionEvent event) {
		logolo.info("Új tranzakció gomb megnyomva");
		
		BorderPane pane = (BorderPane)loader.load("/UjTranzakcioFelulet.fxml");
		Scene scene = new Scene(pane);

		Stage stage = new Stage();
		stage.setTitle("Új Tranzakció létrehozása");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner((Stage)((Node) event.getSource()).getScene().getWindow());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void valtozottALebontas(){
		// lefut, ha a lebontásos dátumokat piszkálják
		bejelentkezett_fh.setKezdoIdopont( lebontas_innentol.getValue() );
		
		bejelentkezett_fh.setVegIdopont( lebontas_idaig.getValue() );
		
		felhasznaloSzolgaltatas.frissitFelhasznalot(bejelentkezett_fh);
		
		adatFrissites();
	}

	public FelhasznaloVo getBejelentkezett_fh() {
		return bejelentkezett_fh;
	}
	
	public void setBejelentkezett_fh( FelhasznaloVo felhasznalo ){
		bejelentkezett_fh = felhasznalo;
	}

	public TranzakcioVo getKivalasztott_trz() {
		return kivalasztott_trz;
	}

}
