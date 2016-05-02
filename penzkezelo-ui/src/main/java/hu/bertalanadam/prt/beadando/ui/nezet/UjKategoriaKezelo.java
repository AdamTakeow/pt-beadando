package hu.bertalanadam.prt.beadando.ui.nezet;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas;
import hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;
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
	
	private static Logger logolo = LoggerFactory.getLogger(UjKategoriaKezelo.class);
	
	@Autowired
	KategoriaSzolgaltatas kategoriaSzolgaltatas;
	
	@Autowired
	UjTranzakcioKezelo ujtranzakciokezelo;
	
	@FXML
	private TextField ujKatNeve;
	
	@FXML 
	private Button closeButton;
	
	@FXML
	private Text celszoveg;
	
	@FXML
	protected void bezarasKezelo(ActionEvent event) {
		
		logolo.info("Ujkategoriakezelo: bezaras gomb megnyomva");
		
		BorderPane pane = (BorderPane)loader.load("/UjTranzakcioFelulet.fxml");
		Scene scene = new Scene(pane);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	protected void letrehozUjKategoriatGomb(ActionEvent event){
		
		logolo.info("Ujkategoriakezelo: új kategória létrhozása gomb megnyomva");
		
		celszoveg.setText("");
		
		if( ujKatNeve.getText().length() == 0 ){
			celszoveg.setText("A kategória neve nem lehet üres!");
		} else {
			
			// ellenőrzés kell hogy van-e már ilyen nevű kategória az adatbázisban
			if( kategoriaSzolgaltatas.getKategoriaByNev(ujKatNeve.getText()) != null ){
				// van már ilyen kategórianév
				logolo.info("Már létezik az adatbázisban ilyen kategória: " + ujKatNeve.getText());
//				
				KategoriaVo letezo_kat = kategoriaSzolgaltatas.getKategoriaByNev(ujKatNeve.getText());
				
				List<FelhasznaloVo> fhk = letezo_kat.getFelhasznalok();
				boolean isEmpty = fhk.stream()
							    .filter( f -> f.getFelhasznalonev().equals(ujtranzakciokezelo.getBejelentkezett_fh().getFelhasznalonev()) )
								.collect(Collectors.toList()).isEmpty();
				
				if( isEmpty ){
					fhk.add(ujtranzakciokezelo.getBejelentkezett_fh());					
					letezo_kat.setFelhasznalok(fhk);
					kategoriaSzolgaltatas.frissitKategoriat(letezo_kat);
					
					BorderPane pane = (BorderPane)loader.load("/UjTranzakcioFelulet.fxml");
					Scene scene = new Scene(pane);
					Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
					stage.setScene(scene);
					
				} else {
					celszoveg.setText("Ilyen kategórianév már létezik!");
				}
				
				
			} else {
				logolo.info("Még nincs ilyen kategória az adatbázisban");
				// nincs még ilyen nevű kategória az adatbázisban
				// létrehozzuk az új kategóriát
				KategoriaVo ujkat = new KategoriaVo();
				ujkat.setNev(ujKatNeve.getText());
				
				List<FelhasznaloVo> felhasznalok = new ArrayList<>();
				felhasznalok.add(ujtranzakciokezelo.getBejelentkezett_fh());
				ujkat.setFelhasznalok(felhasznalok);
				
				ujkat.setTranzakciok(new ArrayList<TranzakcioVo>() );
				
				// beállítjuk a tranzakciós képernyőn a kategóriát
				// ez biztosan egy új kategória itt.
//				ujtranzakciokezelo.setValasztott_kategoria(ujkat);
				
				// elmentjük az adatbázisba az új kategóriát
				kategoriaSzolgaltatas.ujKategoriaLetrehozas(ujkat);
				
				BorderPane pane = (BorderPane)loader.load("/UjTranzakcioFelulet.fxml");
				Scene scene = new Scene(pane);
				Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);				
			}
			
		}
	}
	
}
