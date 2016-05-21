// CHECKSTYLE:OFF
package hu.bertalanadam.prt.beadando.ui.nezet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Component
public class TranzakcioReszletezoKezelo {
	
	private static Logger logolo = LoggerFactory.getLogger(TranzakcioReszletezoKezelo.class);
	
	@Autowired
	private Otthonkezelo otthonkezelo;
	
	@Autowired
	private TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	@FXML
	private Text osszeg_reszletezo;
	@FXML
	private Text datum_reszletezo;
	@FXML
	private Text leiras_reszletezo;
	@FXML
	private Text felhasznalo_reszletezo;
	@FXML
	private Text kategoria_reszletezo;
	@FXML
	private Text ismetlodik_reszletezo;
	@FXML
	private Text nap_reszletezo;
	@FXML
	private Text lekotes_reszletezo;
	@FXML
	private Button torlesGomb;
	
	TranzakcioVo kival_trz;
	
	@FXML
	private void initialize(){
		kival_trz = otthonkezelo.getKivalasztott_trz();
		logolo.info("Kivalasztott tranzakcio azon: " + kival_trz.getId());
		
		FelhasznaloVo bej_felh = otthonkezelo.getBejelentkezett_fh();
		
		osszeg_reszletezo.setText(""+ kival_trz.getOsszeg() );
		logolo.debug("Kivalasztott tranzakcio osszege: " + kival_trz.getOsszeg());
		
		datum_reszletezo.setText(kival_trz.getDatum().toString() );
		logolo.debug("Kivalasztott tranzakcio datuma: " + kival_trz.getDatum());
		
		leiras_reszletezo.setText(kival_trz.getLeiras());
		logolo.debug("Kivalasztott tranzakcio leirasa: " + kival_trz.getLeiras());
		
		felhasznalo_reszletezo.setText( bej_felh.getFelhasznalonev() );
		logolo.debug("Kivalasztott tranzakcio felhasznaloja: " + bej_felh.getFelhasznalonev());
		
		kategoria_reszletezo.setText(kival_trz.getKategoria().getNev());
		logolo.debug("Kivalasztott tranzakcio kategoriaja: " + kival_trz.getKategoria().getNev());
		
		ismetlodik_reszletezo.setText(kival_trz.getIsmetlodo() == null ? "Nem" : "Igen");
		logolo.debug("Kivalasztott tranzakcio ismetlodik-e: " + ismetlodik_reszletezo.getText());
		
		if( kival_trz.getIsmetlodo() != null ){
			nap_reszletezo.setText(kival_trz.getIsmetlodo().getIdo().toString());
			logolo.debug("Kivalasztott tranzakcio ennyi naponta ismetlodik: " + kival_trz.getIsmetlodo().getIdo());
		} else {
			nap_reszletezo.setText("0");
		}
		
		lekotes_reszletezo.setText(kival_trz.getLekotes() == null ? "Nem" : "Igen");
		logolo.debug("Kivalasztott tranzakcio lekotes-e: " + lekotes_reszletezo.getText());
		
		if( kival_trz.getLekotes() != null ){
			torlesGomb.setDisable(true);
		}
		
	}
	
	@FXML
	public void tranzakcioTorlesKezelo( ActionEvent event ){
		logolo.debug("Tranzakcio torlese gombra kattintva!");
		tranzakcioSzolgaltatas.tranzakcioTorles(kival_trz);
		
		otthonkezelo.adatFrissites();
		
		((Stage)torlesGomb.getScene().getWindow()).close();
	}
}
