package hu.bertalanadam.prt.beadando.ui.nezet;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas;
import hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Component
public class UjKategoriaKezelo {

	private static final SpringFxmlLoader loader = new SpringFxmlLoader();
	
	private static Logger logolo = LoggerFactory.getLogger(UjKategoriaKezelo.class);
	
	// szolgáltatások
	
	@Autowired
	KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	@Autowired
	UjTranzakcioKezelo ujtranzakciokezelo;
	
	// eszközök

	@FXML
	private TextField ujKatNeve;
	
	@FXML 
	private Button closeButton;
	
	@FXML
	private Text celszoveg;
	
	// a bezárás gombra kattintáskor lefutó metódus
	@FXML
	protected void bezarasKezelo(ActionEvent event) {
		
		logolo.info("Bezaras gomb megnyomva");
		
		// visszatöltjük a tranzakciókezelőt
		BorderPane pane = (BorderPane)loader.load("/UjTranzakcioFelulet.fxml");
		Scene scene = new Scene(pane);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Új tranzakció létrehozása");
		stage.setScene(scene);
		stage.centerOnScreen();
	}
	
	// a mentés gombra kattintáskor lefutó metódus
	@FXML
	protected void letrehozUjKategoriatGomb(ActionEvent event){
		
		logolo.info("Uj kategoria letrhozasa gomb megnyomva");
		
		celszoveg.setText("");
		
		// ha nem írt be semmit a felhasználó
		if( ujKatNeve.getText().length() == 0 ){
			celszoveg.setText("A kategória neve nem lehet üres!");
		} else {
			
			// ellenőrzés kell hogy van-e már ilyen nevű kategória az adatbázisban
			if( kategoriaSzolgaltatas.keresKategoriat(ujKatNeve.getText()) != null ){
				// van már ilyen kategórianév
				logolo.info("Mar letezik az adatbazisban ilyen kategoria: " + ujKatNeve.getText());
				
				// felhozom azt a kategóriát
				KategoriaVo letezo_kat = kategoriaSzolgaltatas.keresKategoriat(ujKatNeve.getText());

				boolean isEmpty = kategoriaSzolgaltatas.vanIlyenKategoriajaAFelhasznalonak(
												ujtranzakciokezelo.getBejelentkezett_fh(),
												letezo_kat);
				
				// ha nem birtokolja még 
				if( isEmpty ){
					// akkor hozzáadom a kategória listájához az aktuálisan bejelentkezett felhasználót
					letezo_kat.getFelhasznalok().add(ujtranzakciokezelo.getBejelentkezett_fh());
					
					kategoriaSzolgaltatas.frissitKategoriat(letezo_kat);
					
					// visszatöltjük a tranzakciókezelőt
					BorderPane pane = (BorderPane)loader.load("/UjTranzakcioFelulet.fxml");
					Scene scene = new Scene(pane);
					Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
					stage.setTitle("Új tranzakció létrehozása");
					stage.setScene(scene);
					stage.centerOnScreen();
					
				} else {
					celszoveg.setText("Ilyen kategórianév már létezik!");
					logolo.debug("Ilyen kategorianev mar letezik: " + ujKatNeve.getText());
				}
				
			} else {
				logolo.info("Meg nincs ilyen kategoria az adatbazisban: " + ujKatNeve.getText());
				// nincs még ilyen nevű kategória az adatbázisban
				// létrehozzuk az új kategóriát
				KategoriaVo ujkat = new KategoriaVo();
				ujkat.setNev(ujKatNeve.getText());
				
				// beállítok neki kezdetben egy felhasználólistát egyetlen felhasználóval, az éppen bejelentkezettel
				List<FelhasznaloVo> felhasznalok = new ArrayList<>();
				felhasznalok.add(ujtranzakciokezelo.getBejelentkezett_fh());
				ujkat.setFelhasznalok(felhasznalok);
				
				// elmentjük az adatbázisba az új kategóriát
				kategoriaSzolgaltatas.letrehozKategoriat(ujkat);
				
				BorderPane pane = (BorderPane)loader.load("/UjTranzakcioFelulet.fxml");
				Scene scene = new Scene(pane);
				Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
				stage.setTitle("Új tranzakció létrehozása");
				stage.setScene(scene);
				stage.centerOnScreen();
			}
		}
	}
}
