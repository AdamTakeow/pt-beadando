package hu.bertalanadam.prt.beadando.ui.nezet;

import java.time.LocalDate;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

@Component
public class BejelentkezesKezelo {
	
	private static Logger logolo = LoggerFactory.getLogger(BejelentkezesKezelo.class);
	
	private static final SpringFxmlLoader loader = new SpringFxmlLoader();
	
	@Autowired
	FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;
	
	@Autowired
	TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	// Az aktuálisan bejelentkezett felhasználó
	private FelhasznaloVo bejelentkezett_fh;

	// ahová a bejelentkezéssel kapcsolatos üzeneteket írjuk ki
	@FXML
	private Text celszoveg;
	
	// felhasználónév beviteli mező
	@FXML
	private TextField felhnev_bevitel;
	
	// jelszó beviteli mező
	@FXML
	private TextField jelszo_bevitel;

	// a bejelentkezés gombra lefutó metódus
	@FXML
	protected void bejelentkezesGombKezelo(ActionEvent event) {
		logolo.info("Bejelentkezes gomb megnyomva");
		
		// megkeressük a felhasználónevet amit beírt a felhasználó
		FelhasznaloVo felh = felhasznaloSzolgaltatas.findByFelhasznalonev(felhnev_bevitel.getText());

		// ha nincs ilyen fhnév
		if( felh == null ){
			logolo.info("Nem található ilyen nevű felhasználó még regisztrálva");
			celszoveg.setText("Nincs ilyen nevű felhasználó regisztrálva!");
		} else { // ha van ilyen fhnév
			logolo.info("Van ilyen nevű felhasználó regisztrálva!");
			
			// ha megegyezik a beírt jelszó az adatbázisban lévővel
			if( felh.getJelszo().equals(jelszo_bevitel.getText()) ){
				
				// beállítjuk a bejelentkezett felhasználót az adatbázisból felhozottra
				bejelentkezett_fh = felh;
				
				// átváltunk a kezdőlapra
				Parent szulo = (Parent)loader.load("/OtthonFelulet.fxml");
				Scene scene = new Scene(szulo);
				Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
				
				stage.setTitle("Kezdőlap");
				stage.setScene(scene);
				stage.centerOnScreen(); // TODO átdolgozni
				
			} else {
				// ha nem egyezik meg a jelszó
				celszoveg.setText("Helytelen jelszó!");
			}
		}
	}
	
	// a regisztráció gombra lefutó metódus
	@FXML
	protected void regisztracioGombKezelo(ActionEvent event) {
		
		logolo.info("Regisztráció gomb megnyomva");
		
		boolean ok = true;
		// kinullázzuk az üzeneteket
		celszoveg.setText("");
		
		// nem írt be semmit a felhasználónévhez
		if( felhnev_bevitel.getText().length() == 0 ){
			celszoveg.setText(celszoveg.getText() + "Felhasználónév megadása kötelező!\n");
			ok = false;
		}
		// nem írt be semmit a jelszóhoz
		if( jelszo_bevitel.getText().length() == 0 ){
			celszoveg.setText(celszoveg.getText() + "Jelszó megadása kötelező!\n");
			ok = false;
		}

		// túl rövid felhasználónevet adott meg
		if( felhnev_bevitel.getText().length() > 0 && felhnev_bevitel.getText().length() < 4 ){
			celszoveg.setText(celszoveg.getText() + "A felhasználónév túl rövid!\n");
			ok = false;
		}
		
		// túl rövid jelszót adott meg
		if( jelszo_bevitel.getText().length() > 0 && jelszo_bevitel.getText().length() < 4 ){
			celszoveg.setText(celszoveg.getText() + "A jelszó túl rövid!\n");
			ok = false;
		}
		
		// ha minden rendben
		if( ok ){
			// megnézzük hogy van-e már ilyen felhasználónév az adatbázisban
			if( felhasznaloSzolgaltatas.findByFelhasznalonev(felhnev_bevitel.getText()) != null ){
				// ha van már ilyen felhasználó
				celszoveg.setText(celszoveg.getText() + "Ilyen felhasználónév már létezik!\n");
			} else {
				// ha nincs még ilyen felhasználó
				logolo.info("Új felhasználó létrehozása");
				
				// létrehozzuk az új felhasználót
				FelhasznaloVo ujfelhasznalo = new FelhasznaloVo();
				ujfelhasznalo.setFelhasznalonev(felhnev_bevitel.getText());
				ujfelhasznalo.setEgyenleg(0L);
				ujfelhasznalo.setKiadasraSzantPenz(0L);
				ujfelhasznalo.setJelszo(jelszo_bevitel.getText());
				
				LocalDate innentol = LocalDate.of(LocalDate.now().getYear(), 1, 1);
				LocalDate idaig = LocalDate.now();
				
				ujfelhasznalo.setKezdoIdopont(innentol);
				ujfelhasznalo.setVegIdopont(idaig);
				
				ujfelhasznalo.setTranzakciok(new ArrayList<TranzakcioVo>());
				ujfelhasznalo.setKategoriak(new ArrayList<KategoriaVo>());
				
				// elmentjük az új felhasználót
				felhasznaloSzolgaltatas.ujFelhasznaloLetrehozas(ujfelhasznalo);	
				celszoveg.setText(celszoveg.getText() + "Sikeres regisztráció!");
			}
		}
	}

	public FelhasznaloVo getBejelentkezett_fh() {
		return bejelentkezett_fh;
	}

	public void setBejelentkezett_fh(FelhasznaloVo bejelentkezett_fh) {
		this.bejelentkezett_fh = bejelentkezett_fh;
	}
}
