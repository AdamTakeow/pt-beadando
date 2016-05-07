package hu.bertalanadam.prt.beadando.ui.nezet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class BeallitasokKezelo {
	
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
	protected void jelszoValtas(ActionEvent event){
		
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
		}
	}
	
	@FXML
	protected void koltekezesMentesKezeles( ActionEvent event ){
		
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
			
			felhasznaloSzolgaltatas.frissitFelhasznalot(otthonkezelo.getBejelentkezett_fh());
		}
	}
	
	@FXML
	protected void bezarasKezelo( ActionEvent event ){
		otthonkezelo.adatFrissites();
		((Stage)closeButton.getScene().getWindow()).close();
	}
}
