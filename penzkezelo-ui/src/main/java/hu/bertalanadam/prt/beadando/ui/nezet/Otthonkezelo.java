package hu.bertalanadam.prt.beadando.ui.nezet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
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
	
	private static Logger logolo = LoggerFactory.getLogger(Otthonkezelo.class);
	
	@Autowired
	TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	@Autowired
	FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;
	
	@Autowired
	private BejelentkezesKezelo bejelentkezesKezelo;
	
	private FelhasznaloVo bejelentkezett_fh;
	
	@FXML
	private Text welcome_user;
	
	@FXML
	private Text egyenleg_text;
	
	@FXML
	private Text sum_bevetel;
	
	@FXML
	private Text sum_kiadas;
	
	@FXML
	private Text tranzakciok_text;
	
	public void adatFrissites(){
		bejelentkezett_fh = felhasznaloSzolgaltatas.findByFelhasznalonev(bejelentkezett_fh.getFelhasznalonev());
		egyenleg_text.setText(bejelentkezett_fh.getEgyenleg().toString());
		
		long osszes_bev = felhasznaloSzolgaltatas.osszesBevetelAFelhasznalohoz(bejelentkezett_fh);
		sum_bevetel.setText("" + osszes_bev);
		
		long osszes_kiad = felhasznaloSzolgaltatas.osszesKiadasAFelhasznalohoz(bejelentkezett_fh);
		sum_kiadas.setText("" + osszes_kiad);
	}
	
	@FXML
	private void initialize(){
		// beállítjuk az aktuális felhasználót
		bejelentkezett_fh = bejelentkezesKezelo.getBejelentkezett_fh();
//		logolo.info("Otthonkezelo initialize: bejelentkezett felhasznalo: " + bejelentkezett_fh );
		
		// beállítjuk az adatokat
		welcome_user.setText("Üdvözlöm, " + bejelentkezett_fh.getFelhasznalonev());
		
		egyenleg_text.setText(bejelentkezett_fh.getEgyenleg().toString());
		
		long osszes_bev = felhasznaloSzolgaltatas.osszesBevetelAFelhasznalohoz(bejelentkezett_fh);
		sum_bevetel.setText("" + osszes_bev);
		
		long osszes_kiad = felhasznaloSzolgaltatas.osszesKiadasAFelhasznalohoz(bejelentkezett_fh);
		sum_kiadas.setText("" + osszes_kiad);
	}
	
	@FXML
	protected void kijelentkezesGombKezelo(ActionEvent event) {
		
		logolo.info("Kijelentkezés gomb megnyomva");
		
		Parent parent = (Parent)loader.load("/BejelentkezoFelulet.fxml");
		Scene scene = new Scene(parent);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	protected void ujTranzakcioKezelo(ActionEvent event) {
		logolo.info("Új tranzakció gomb megnyomva");
		
		BorderPane pane = (BorderPane)loader.load("/UjTranzakcioFelulet.fxml");
		Scene scene = new Scene(pane);

		Stage stage = new Stage();
		stage.setTitle("Új Tranzakció létrehozása");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner((Stage)((Node) event.getSource()).getScene().getWindow());
		stage.setScene(scene);
		stage.show();
	}

	public FelhasznaloVo getBejelentkezett_fh() {
		return bejelentkezett_fh;
	}

	public void setBejelentkezett_fh(FelhasznaloVo bejelentkezett_fh) {
		this.bejelentkezett_fh = bejelentkezett_fh;
	}
}
