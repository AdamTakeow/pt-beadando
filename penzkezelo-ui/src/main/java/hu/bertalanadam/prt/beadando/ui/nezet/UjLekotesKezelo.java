package hu.bertalanadam.prt.beadando.ui.nezet;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class UjLekotesKezelo {
	
	@FXML
	private TextField osszeg_bevitel;
	
	@FXML
	private Button vissza_gomb;
	
	@FXML
	private TextField futamido_bevitel;
	
	@FXML
	private ComboBox<String> nap_honap_ev_valaszto;
	
	@FXML
	private TextField kamat_bevitel;
	
	@FXML
	protected void visszaGombKezeles(ActionEvent event) {
		((Stage)vissza_gomb.getScene().getWindow()).close();
	}
	
	@FXML
	protected void mentesGombKezeles(ActionEvent event) {
		// validáció a mezőkre
		
	}

}
