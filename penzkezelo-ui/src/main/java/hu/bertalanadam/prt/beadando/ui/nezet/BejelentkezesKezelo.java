package hu.bertalanadam.prt.beadando.ui.nezet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Component
public class BejelentkezesKezelo {
	
	private static final SpringFxmlLoader loader = new SpringFxmlLoader();
	
	@Autowired
	FelhasznaloSzolgaltatas fhsz;
	
	private FelhasznaloVo bejelentkezett_fh;

	@FXML
	private Text celszoveg;
	
	@FXML
	private TextField felhnev_bevitel;
	
	@FXML
	private TextField jelszo_bevitel;

	@FXML
	protected void bejelentkezesGombKezelo(ActionEvent event) {
		
		FelhasznaloVo felh = fhsz.findByFelhasznalonev(felhnev_bevitel.getText());
		
		if( felh == null ){
			celszoveg.setText("Nincs ilyen nevű felhasználó regisztrálva!");
		} else {
			
			// validation
			if( felh.getJelszo() == jelszo_bevitel.getText() ){
				
				// login
				
				this.bejelentkezett_fh = felh;
				
				Parent szulo = (Parent)loader.load("/OtthonFelulet.fxml");
				
				Scene scene = new Scene(szulo);
				
				Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
				
				stage.setScene(scene);
			} else {
				celszoveg.setText("Helytelen felhasználónév vagy jelszó!");
			}
		}
		
	}
	
	@FXML
	protected void regisztracioGombKezelo(ActionEvent event) {
		
		boolean ok = true;
		celszoveg.setText("");
		
		if( felhnev_bevitel.getText().length() == 0 ){
			celszoveg.setText(celszoveg.getText() + "Felhasználónév megadása kötelező!\n");
			ok = false;
		}
		if( jelszo_bevitel.getText().length() == 0 ){
			celszoveg.setText(celszoveg.getText() + "Jelszó megadása kötelező!\n");
			ok = false;
		}

		if( felhnev_bevitel.getText().length() > 0 && felhnev_bevitel.getText().length() < 4 ){
			celszoveg.setText(celszoveg.getText() + "A felhasználónév túl rövid!\n");
			ok = false;
		}
		
		if( jelszo_bevitel.getText().length() > 0 && jelszo_bevitel.getText().length() < 4 ){
			celszoveg.setText(celszoveg.getText() + "A felhasználónév túl rövid!\n");
			ok = false;
		}
		
		if( ok ){
			if( fhsz.findByFelhasznalonev(felhnev_bevitel.getText()) != null ){
				celszoveg.setText(celszoveg.getText() + "Ilyen felhasználónév már létezik!\n");
			} else {
				FelhasznaloVo ujfelhasznalo = new FelhasznaloVo();
				ujfelhasznalo.setFelhasznalonev(felhnev_bevitel.getText());
				ujfelhasznalo.setEgyenleg(0L);
				
//				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//				String jelszo_titkositva = encoder.encode(jelszo_bevitel.getText());
				
				ujfelhasznalo.setJelszo(jelszo_bevitel.getText());
				ujfelhasznalo.setLebontas(0);
				ujfelhasznalo.setTranzakciok(null);
				
				fhsz.ujFelhasznaloLetrehozas(ujfelhasznalo);	
				celszoveg.setText(celszoveg.getText() + "Sikeres regisztráció!");
			}
		}
	}

	public FelhasznaloVo getBejelentkezett_fh() {
		return bejelentkezett_fh;
	}

	public void setBejelentkezett_fh(FelhasznaloVo bejelentkezett_fh) {
		this.bejelentkezett_fh = bejelentkezett_fh;
	}
}
