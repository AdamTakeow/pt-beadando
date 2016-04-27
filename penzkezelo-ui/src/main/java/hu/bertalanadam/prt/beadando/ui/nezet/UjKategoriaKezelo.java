package hu.bertalanadam.prt.beadando.ui.nezet;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas;
import hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader;
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
	
	@Autowired
	KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	@FXML
	private TextField ujKatNeve;
	
	@FXML 
	private Button closeButton;
	
	@FXML
	private Text celszoveg;
	
	@FXML
	protected void bezarasKezelo(ActionEvent event) {
		BorderPane pane = (BorderPane)loader.load("/UjTranzakcioFelulet.fxml");
		
		Scene scene = new Scene(pane);
	    
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
	}
	
	@FXML
	protected void letrehozUjKategoriatGomb(ActionEvent event){
		
		if( ujKatNeve.getText().length() == 0 ){
			celszoveg.setText("A kategória neve nem lehet üres!");
		} else {
			KategoriaVo ujkat = new KategoriaVo();
			ujkat.setNev(ujKatNeve.getText());
			
			kategoriaSzolgaltatas.ujKategoriaLetrehozas(ujkat);
			
			BorderPane pane = (BorderPane)loader.load("/UjTranzakcioFelulet.fxml");
			
			Scene scene = new Scene(pane);
		    
			Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			
			stage.setScene(scene);
		}
	}
	
}
