package hu.bertalanadam.prt.beadando.ui.nezet;

import java.time.LocalDate;
import java.time.Period;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.LekotesSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.LekotesVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

@Component
public class MeglevoLekotesKezelo {
	
	private static Logger logolo = LoggerFactory.getLogger(MeglevoLekotesKezelo.class);

	@Autowired
	private Otthonkezelo otthonkezelo;
	
	@Autowired
	private LekotesSzolgaltatas lekotesSzolgaltatas;
	
	@Autowired
	private TranzakcioSzolgaltatas tranzakcioSzolgaltatas;
	
	@Autowired
	private KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	@Autowired
	private FelhasznaloSzolgaltatas felhasznaloSzolgaltatas;
	
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
	
//	private TranzakcioVo lekoteses_tranzakcio;
	
	private LekotesVo lekotes;
	
	private FelhasznaloVo bejelentkezett_fh;
	
	@FXML
	private void initialize(){
		bejelentkezett_fh = otthonkezelo.getBejelentkezett_fh();
		logolo.debug("Bejelentkezett felhasznalo: " + otthonkezelo.getBejelentkezett_fh().getFelhasznalonev());

		lekotes = lekotesSzolgaltatas.felhasznaloLekotese(bejelentkezett_fh);
		
		lekotes_azonosito.setText(lekotes.getId().toString());
		logolo.debug("Lekotes azonositoja: " + lekotes.getId() );
		
		lekotes_osszeg.setText(lekotes.getOsszeg().toString() + " Ft");
		logolo.debug("Lekotes osszege: " + lekotes.getOsszeg() ); 
		
		lekotes_futamido.setText(lekotes.getFutamido().toString() + " év");
		logolo.debug("Lekotes futamideje: " + lekotes.getFutamido() + " ev" ); 
		
		lekotes_kamat.setText("" + lekotes.getKamat() + "%");
		logolo.debug("Lekotes kamata: " + lekotes.getKamat() + " %");
		
		Period hatralevo = lekotesSzolgaltatas.mennyiIdoVanHatra(bejelentkezett_fh, lekotes);
		
		lekotes_hatralevo_ido.setText("" + hatralevo.getYears() + " év, " + hatralevo.getMonths() + " hónap és " + hatralevo.getDays() + " nap");
		
	}
	
	@FXML
	protected void visszaGombKezelo( ActionEvent event ){
		logolo.debug("Vissza gomb megnyomva!");
		((Stage)visszaGomb.getScene().getWindow()).close();
	}
	
	@FXML
	protected void feltoresGombKezelo( ActionEvent event ){
		logolo.debug("Feltores gomb megnyomva!");
		
		// ha feltöri idő előtt, akkor csak a lekötött pénzt kapja vissza
		
		// TODO szolgaltatasba talán?
		lekotes.setTeljesitett(true);
			
		// lefrissítjük a lekötést mivel módosítottuk
		lekotesSzolgaltatas.frissitLekotest(lekotes);
				
		// és be kell szúrni egy új tranzakciót mert lejárt a lekötés
		TranzakcioVo ujTr = new TranzakcioVo();
		ujTr.setOsszeg(lekotes.getOsszeg());
		ujTr.setLeiras("Lekötés feltörve");
		ujTr.setDatum(LocalDate.now());
				
		KategoriaVo trz_kategoriaja = kategoriaSzolgaltatas.keresKategoriat("Lekötés");
				
		TranzakcioVo letezo_tr = tranzakcioSzolgaltatas.letrehozTranzakciot(ujTr);
				
		bejelentkezett_fh.getTranzakciok().add(letezo_tr);
				
		felhasznaloSzolgaltatas.frissitFelhasznalot(bejelentkezett_fh);
				
		// beállítom a tranzakciónak a frissített kategóriát
		letezo_tr.setKategoria(trz_kategoriaja);
		// beállítom a tranzakciónak a felhasználót
		letezo_tr.setFelhasznalo(bejelentkezett_fh);
				
		tranzakcioSzolgaltatas.frissitTranzakciot(letezo_tr);
		
		otthonkezelo.adatFrissites();
		
		((Stage)visszaGomb.getScene().getWindow()).close();
	}
}
