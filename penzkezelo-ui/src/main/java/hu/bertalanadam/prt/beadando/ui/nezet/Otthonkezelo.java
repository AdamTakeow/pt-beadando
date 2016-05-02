package hu.bertalanadam.prt.beadando.ui.nezet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.TranzakcioSzolgaltatas;
import hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader;
import hu.bertalanadam.prt.beadando.ui.model.TranzakcioData;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	
	@FXML
	private TableView<TranzakcioData> tranzakcioTable;
	
	@FXML
	private TableColumn<TranzakcioData, String> osszegOszlop;
	
	@FXML
	private TableColumn<TranzakcioData, String> datumOszlop;
	
	@FXML
	private TableColumn<TranzakcioData, String> kategoriaOszlop;
	
	private ObservableList<TranzakcioData> tranzakcioTablazatAdatok = FXCollections.observableArrayList();
	
	private TranzakcioVo kivalasztott_trz;
	
	public void adatFrissites(){
		bejelentkezett_fh = felhasznaloSzolgaltatas.findByFelhasznalonev(bejelentkezett_fh.getFelhasznalonev());
		egyenleg_text.setText(bejelentkezett_fh.getEgyenleg().toString());
		
		long osszes_bev = felhasznaloSzolgaltatas.osszesBevetelAFelhasznalohoz(bejelentkezett_fh);
		sum_bevetel.setText("" + osszes_bev);
		
		long osszes_kiad = felhasznaloSzolgaltatas.osszesKiadasAFelhasznalohoz(bejelentkezett_fh);
		sum_kiadas.setText("" + osszes_kiad);
		
		// tablazat
		List<TranzakcioVo> felh_tranzakcioi = 
				tranzakcioSzolgaltatas.osszesTranzakcioAFelhasznalohoz(bejelentkezett_fh);
		
		if( tranzakcioTablazatAdatok.isEmpty() ){
			
			for (TranzakcioVo tranzakcioVo : felh_tranzakcioi) {
				
				String kategorianev = "nincs";
				if( tranzakcioVo.getKategoria() != null ){
					kategorianev = tranzakcioVo.getKategoria().getNev();
				}
				
				tranzakcioTablazatAdatok.add(
						new TranzakcioData(
							tranzakcioVo.getId(),
							tranzakcioVo.getOsszeg(),
							tranzakcioVo.getDatum(),
							tranzakcioVo.getLeiras(),
							kategorianev )
				);	
			}
			
		} else {
			tranzakcioTablazatAdatok.remove(0, tranzakcioTablazatAdatok.size());
			
			for (TranzakcioVo tranzakcioVo : felh_tranzakcioi) {
				
				String kategorianev = "nincs";
				if( tranzakcioVo.getKategoria() != null ){
					kategorianev = tranzakcioVo.getKategoria().getNev();
				}
				
				tranzakcioTablazatAdatok.add(
						new TranzakcioData(
								tranzakcioVo.getId(),
								tranzakcioVo.getOsszeg(),
								tranzakcioVo.getDatum(),
								tranzakcioVo.getLeiras(),
								kategorianev)
						);

			}
			
		}
	}
	
	@FXML
	private void initialize(){
		// beállítjuk az aktuális felhasználót
		bejelentkezett_fh = bejelentkezesKezelo.getBejelentkezett_fh();
		
		// beállítjuk az adatokat
		welcome_user.setText("Üdvözlöm, " + bejelentkezett_fh.getFelhasznalonev());
		
		// beállítjuk az aktuális egyenleget
		egyenleg_text.setText(bejelentkezett_fh.getEgyenleg().toString());
		
		// beállítjuk az összes bevételt
		long osszes_bev = felhasznaloSzolgaltatas.osszesBevetelAFelhasznalohoz(bejelentkezett_fh);
		sum_bevetel.setText("" + osszes_bev);

		// beállítjuk az összes kiadást
		long osszes_kiad = felhasznaloSzolgaltatas.osszesKiadasAFelhasznalohoz(bejelentkezett_fh);
		sum_kiadas.setText("" + osszes_kiad);
		
		// feltöltjük a táblázatot tranzakcióval
		// elkérjük a felhasználó összes tranzakcióját
		List<TranzakcioVo> felh_tranzakcioi = 
				tranzakcioSzolgaltatas.osszesTranzakcioAFelhasznalohoz(bejelentkezett_fh);
		
		if( tranzakcioTablazatAdatok.isEmpty() ){
		
			for (TranzakcioVo tranzakcioVo : felh_tranzakcioi) {
				
				String kategorianev = "nincs";
				if( tranzakcioVo.getKategoria() != null ){
					kategorianev = tranzakcioVo.getKategoria().getNev();
				}
				
				tranzakcioTablazatAdatok.add(
						new TranzakcioData(
								tranzakcioVo.getId(),
								tranzakcioVo.getOsszeg(),
								tranzakcioVo.getDatum(),
								tranzakcioVo.getLeiras(),
								kategorianev)
						);
			}
		} else {
			tranzakcioTablazatAdatok.remove(0, tranzakcioTablazatAdatok.size());
			
			for (TranzakcioVo tranzakcioVo : felh_tranzakcioi) {
				
				String kategorianev = "nincs";
				if( tranzakcioVo.getKategoria() != null ){
					kategorianev = tranzakcioVo.getKategoria().getNev();
				}
				
				tranzakcioTablazatAdatok.add(
						new TranzakcioData(
								tranzakcioVo.getId(),
								tranzakcioVo.getOsszeg(),
								tranzakcioVo.getDatum(),
								tranzakcioVo.getLeiras(),
								kategorianev)
						);

			}
		}
		
		
		osszegOszlop.setCellValueFactory( celldata -> celldata.getValue().getOsszegProperty() );
		datumOszlop.setCellValueFactory( celldata -> celldata.getValue().getDatumProperty() );
		kategoriaOszlop.setCellValueFactory( celldata -> celldata.getValue().getKategoriaProperty() );
		
		tranzakcioTable.setItems(tranzakcioTablazatAdatok);
		
		tranzakcioTable.getSelectionModel()
					   .selectedItemProperty()
		               .addListener( (observable, oldValue, newValue) -> showTranzakcioData(newValue) );
		
	}
	
	private void showTranzakcioData(TranzakcioData tData){
		if( tData != null ){
			
			kivalasztott_trz = tranzakcioSzolgaltatas.findById(tData.getId());
			
			BorderPane pane = (BorderPane)loader.load("/TranzakcioReszletezo.fxml");
			Scene scene = new Scene(pane);

			Stage stage = new Stage();
			stage.setTitle("Tranzakció részletei");
			stage.initModality(Modality.WINDOW_MODAL);
//			stage.initOwner((Stage)((Node) event.getSource()).getScene().getWindow());
			stage.setScene(scene);
			stage.show();
			
			Platform.runLater(new Runnable() {
			    @Override public void run() {
			    	tranzakcioTable.getSelectionModel().clearSelection();
			}});
		}
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

	public TranzakcioVo getKivalasztott_trz() {
		return kivalasztott_trz;
	}

	public void setKivalasztott_trz(TranzakcioVo kivalasztott_trz) {
		this.kivalasztott_trz = kivalasztott_trz;
	}

	public TableView<TranzakcioData> getTranzakcioTable() {
		return tranzakcioTable;
	}

	public void setTranzakcioTable(TableView<TranzakcioData> tranzakcioTable) {
		this.tranzakcioTable = tranzakcioTable;
	}
}
