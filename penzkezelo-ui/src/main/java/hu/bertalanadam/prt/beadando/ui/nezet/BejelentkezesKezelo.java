package hu.bertalanadam.prt.beadando.ui.nezet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

@Component
public class BejelentkezesKezelo {
	
//	private static Logger logger = Logger.
	
	@Autowired
	FelhasznaloSzolgaltatas fhsz;

	@FXML
	private Text celszoveg;
	
	@FXML
	private TextField felhnev_bevitel;
	
	@FXML
	private TextField jelszo_bevitel;

	@FXML
	protected void bejelentkezesGombKezelo(ActionEvent event) {
//		celszoveg.setText("Bejelentkezve! Felhasználónév: " + felhnev_bevitel.getText() +
//				" Jelszó: " + jelszo_bevitel.getText());
		
		
		FelhasznaloVo felh = null;
		try {
			felh = fhsz.findByFelhasznalonev(felhnev_bevitel.getText());
		} catch (Exception e) {
//			System.out.println("ERROR:: A UI-nal");
			e.printStackTrace();
		}
		if( fhsz == null ){
			celszoveg.setText("Nem sikerult az Autowire");
//			System.out.println();
		} else {
			celszoveg.setText("AUTOWIRE!!");
		}
		if( felh == null ){
			System.out.println("ERROR: felh is NULL");
		} else {
			// adatok ellenőrzése
			System.out.println("SIKERES");
		}
	}
	
	@FXML
	protected void regisztracioGombKezelo(ActionEvent event) {
		celszoveg.setText("Regisztráció!");
	}

}
