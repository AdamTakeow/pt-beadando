package hu.bertalanadam.prt.beadando.ui.nezet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class Otthonkezelo {

	private static final SpringFxmlLoader loader = new SpringFxmlLoader();
	
	@Autowired
	private BejelentkezesKezelo bejelentkezesKezelo;
	
	private FelhasznaloVo bejelentkezett_fh;
	
	@FXML
	private Text welcome_user;
	
	@FXML
	private Text egyenleg_text;
	
	@FXML
	private Text tranzakciok_text;
	
	@FXML
	private void initialize(){
		this.bejelentkezett_fh = bejelentkezesKezelo.getBejelentkezett_fh();
		
		welcome_user.setText("Üdvözlöm, " + bejelentkezett_fh.getFelhasznalonev());
		egyenleg_text.setText(bejelentkezett_fh.getEgyenleg().toString());
		
		
	}
	
	@FXML
	protected void kijelentkezesGombKezelo(ActionEvent event) {
		Parent parent = (Parent)loader.load("/BejelentkezoFelulet.fxml");
		
		Scene scene = new Scene(parent);
	    
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
	}
	
	@FXML
	protected void ujTranzakcioKezelo(ActionEvent event) {
		BorderPane pane = (BorderPane)loader.load("/UjTranzakcioFelulet.fxml");
		
		Stage stage = new Stage();
		stage.setTitle("Új Tranzakció létrehozása");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner((Stage)((Node) event.getSource()).getScene().getWindow());
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		stage.showAndWait();
	}
}
