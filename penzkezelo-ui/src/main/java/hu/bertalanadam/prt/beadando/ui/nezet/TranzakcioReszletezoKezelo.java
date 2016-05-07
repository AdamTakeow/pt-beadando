package hu.bertalanadam.prt.beadando.ui.nezet;

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
	
	@Autowired
	private Otthonkezelo otthonkezelo;
	
	@Autowired
	private TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	@FXML
	private Text azonosito_reszletezo;
	
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
	private Button closeButton;
	
	TranzakcioVo kival_trz;
	
	@FXML
	private void initialize(){
		kival_trz = otthonkezelo.getKivalasztott_trz();
		FelhasznaloVo bej_felh = otthonkezelo.getBejelentkezett_fh();
		
		azonosito_reszletezo.setText("" + kival_trz.getId() );
		osszeg_reszletezo.setText(""+ kival_trz.getOsszeg() );
		datum_reszletezo.setText(kival_trz.getDatum().toString() );
		leiras_reszletezo.setText(kival_trz.getLeiras());
		felhasznalo_reszletezo.setText( bej_felh.getFelhasznalonev() );
		
		if( kival_trz.getKategoria() == null ){
			kategoria_reszletezo.setText("nincs");
		} else {
			kategoria_reszletezo.setText(kival_trz.getKategoria().getNev());			
		}
		
		if( kival_trz.getLekotes() != null ){
			closeButton.setDisable(true);
		}
		
	}
	
	@FXML
	public void tranzakcioTorlesKezelo( ActionEvent event ){
		tranzakcioSzolgaltatas.tranzakcioTorles(kival_trz);
		
		otthonkezelo.adatFrissites();
		
		((Stage)closeButton.getScene().getWindow()).close();
	}
	
	@FXML
	public void stop(){
	}

}
