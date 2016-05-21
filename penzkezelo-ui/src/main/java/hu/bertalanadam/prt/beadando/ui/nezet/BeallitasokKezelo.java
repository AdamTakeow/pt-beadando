// CHECKSTYLE:OFF
package hu.bertalanadam.prt.beadando.ui.nezet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Component
public class BeallitasokKezelo {
	
	private static Logger logolo = LoggerFactory.getLogger(BeallitasokKezelo.class);
	
	@Autowired
	Otthonkezelo otthonkezelo;
	
	@Autowired
	FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;

	@FXML
	private Button koltekezes_mentes;
	@FXML
	private TextField koltekezes_bevitel;
	@FXML
	private PasswordField ujJelszo_bevitel;
	@FXML
	private Button jelszoCsereGomb;
	@FXML
	private Label celszoveg;
	@FXML
	private Button closeButton;
	@FXML
	private Text jelenlegi_kiadasraszant;
	
	@FXML
	public void initialize(){
		logolo.debug("Kiadasra szant osszeg megjelenitese");
		jelenlegi_kiadasraszant.setText(otthonkezelo.getBejelentkezett_fh().getKiadasraSzantPenz().toString() + " Ft");
	}
	
	@FXML
	protected void jelszoValtas(ActionEvent event){
		
		logolo.debug("Jelszo valtas gomb megnyomva!");
		celszoveg.setText("");
		
		boolean ok = true;
		
		if( ujJelszo_bevitel.getText().length() == 0 ){
			celszoveg.setText(celszoveg.getText() + "Új jelszó megadása kötelező!\n");
			ok = false;
		}
		
		if( ujJelszo_bevitel.getText().length() > 0 && ujJelszo_bevitel.getText().length() < 4 ){
			celszoveg.setText(celszoveg.getText() + "Az új jelszó túl rövid!\n");
			ok = false;
		}
		
		if( ok ){
			otthonkezelo.getBejelentkezett_fh().setJelszo(ujJelszo_bevitel.getText());
			
			felhasznaloSzolgaltatas.frissitFelhasznalot(otthonkezelo.getBejelentkezett_fh());
			
			celszoveg.setText("Jelszó sikeresen megváltoztatva!");
			logolo.debug("Jelszo megvaltoztatva: " + ujJelszo_bevitel.getText());
		}
	}
	
	@FXML
	protected void koltekezesMentesKezeles( ActionEvent event ){
		
		logolo.debug("Felhasznalo kiadasra szant penzenek beallitasa gomb megnyomva!");
		
		celszoveg.setText("");
		
		boolean ok = true;
		
		long osszeg = 0;
		try {
			if( koltekezes_bevitel.getText() == null ){
				celszoveg.setText(celszoveg.getText() + "Írja be az kiadásra szánt összeget!\n");
				ok = false;
			} else {
				osszeg = Long.parseLong(koltekezes_bevitel.getText());
				if( osszeg == 0 ){
					throw new NumberFormatException();
				}
			}
		} catch (NumberFormatException nfe) {
			celszoveg.setText(celszoveg.getText() + "Az összeg mezőbe számot (nullától különbözőt) kell írni!\n");
			ok = false;
		}
		
		if( ok ){
			otthonkezelo.getBejelentkezett_fh().setKiadasraSzantPenz(osszeg);
			
			otthonkezelo.setBejelentkezett_fh( felhasznaloSzolgaltatas.frissitFelhasznalot(otthonkezelo.getBejelentkezett_fh()) );
			
			jelenlegi_kiadasraszant.setText( otthonkezelo.getBejelentkezett_fh().getKiadasraSzantPenz().toString() + " Ft");
			
			celszoveg.setText("Beállítás sikeres!");
			logolo.debug("Uj kiadasra szant osszeg beallitva: " + osszeg);
		}
	}
	
	@FXML
	protected void bezarasKezelo( ActionEvent event ){
		
		logolo.debug("Bezaras gomb megnyomva!");
		
		otthonkezelo.adatFrissites();
		((Stage)closeButton.getScene().getWindow()).close();
	}
}
