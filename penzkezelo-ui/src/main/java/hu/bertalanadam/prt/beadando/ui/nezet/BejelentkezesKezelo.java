package hu.bertalanadam.prt.beadando.ui.nezet;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Component
public class BejelentkezesKezelo {
	
	private static Logger logolo = LoggerFactory.getLogger(BejelentkezesKezelo.class);
	
	private static final SpringFxmlLoader loader = new SpringFxmlLoader();
	
	@Autowired
	FelhasznaloSzolgaltatas fhsz;
	
	// Az aktuálisan bejelentkezett felhasználó
	private FelhasznaloVo bejelentkezett_fh;

	@FXML
	private Text celszoveg;
	
	@FXML
	private TextField felhnev_bevitel;
	
	@FXML
	private TextField jelszo_bevitel;

	@FXML
	protected void bejelentkezesGombKezelo(ActionEvent event) {
		logolo.info("Bejelentkezes gomb megnyomva");
		
		FelhasznaloVo felh = fhsz.findByFelhasznalonev(felhnev_bevitel.getText());

		if( felh == null ){
			logolo.info("Nem található ilyen nevű felhasználó még regisztrálva");
			celszoveg.setText("Nincs ilyen nevű felhasználó regisztrálva!");
		} else {
			logolo.info("Van ilyen nevű felhasználó regisztrálva!");
			
			// ha megegyezik a beírt jelszó az adatbázisban lévővel
			if( felh.getJelszo().equals(jelszo_bevitel.getText()) ){
				
				// beállítjuk a bejelentkezett felhasználót az adatbázisból felhozottra
				bejelentkezett_fh = felh;
				logolo.info("A beállított felhasználó: " + felh);
				
				// átváltunk a kezdőlapra
				Parent szulo = (Parent)loader.load("/OtthonFelulet.fxml");
				Scene scene = new Scene(szulo);
				Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
				
			} else {
				// ha nem egyezik meg a jelszó
				celszoveg.setText("Helytelen jelszó!");
			}
		}
	}
	
	@FXML
	protected void regisztracioGombKezelo(ActionEvent event) {
		
		logolo.info("Regisztráció gomb megnyomva");
		
		boolean ok = true;
		// kinullázzuk az üzeneteket
		celszoveg.setText("");
		
		if( felhnev_bevitel.getText().length() == 0 ){
			celszoveg.setText(celszoveg.getText() + "Felhasználónév megadása kötelező!\n");
			ok = false;
		}
		if( jelszo_bevitel.getText().length() == 0 ){
			celszoveg.setText(celszoveg.getText() + "Jelszó megadása kötelező!\n");
			ok = false;
		}

		if( felhnev_bevitel.getText().length() > 0 && felhnev_bevitel.getText().length() < 4 ){
			celszoveg.setText(celszoveg.getText() + "A felhasználónév túl rövid!\n");
			ok = false;
		}
		
		if( jelszo_bevitel.getText().length() > 0 && jelszo_bevitel.getText().length() < 4 ){
			celszoveg.setText(celszoveg.getText() + "A felhasználónév túl rövid!\n");
			ok = false;
		}
		
		// ha minden rendben
		if( ok ){
			// megnézzük hogy van-e már ilyen felhasználónév az adatbázisban
			if( fhsz.findByFelhasznalonev(felhnev_bevitel.getText()) != null ){
				// ha van már ilyen felhasználó
				celszoveg.setText(celszoveg.getText() + "Ilyen felhasználónév már létezik!\n");
			} else {
				// ha nincs még ilyen felhasználó
				logolo.info("Új felhasználó létrehozása");
				
				FelhasznaloVo ujfelhasznalo = new FelhasznaloVo();
				ujfelhasznalo.setFelhasznalonev(felhnev_bevitel.getText());
				ujfelhasznalo.setEgyenleg(0L);
				ujfelhasznalo.setJelszo(jelszo_bevitel.getText());
				ujfelhasznalo.setLebontas(0);
				ujfelhasznalo.setTranzakciok(new ArrayList<TranzakcioVo>());
				
				// elmentjük az új felhasználót
				fhsz.ujFelhasznaloLetrehozas(ujfelhasznalo);	
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
