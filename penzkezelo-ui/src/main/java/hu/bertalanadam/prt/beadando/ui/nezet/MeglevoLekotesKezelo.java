package hu.bertalanadam.prt.beadando.ui.nezet;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

@Component
public class MeglevoLekotesKezelo {

	@FXML
	private Label lekotes_azonosito;
	
	@FXML
	private Label lekotes_osszeg;
	
	@FXML
	private Label lekotes_futamido;
	
	@FXML
	private Label lekotes_kamat;
	
	@FXML
	private Label lekotes_hatralevo_ido;
	
	@FXML
	private Button visszaGomb;
	
	@FXML
	private Button feltoresGomb;
}
