package hu.bertalanadam.prt.beadando.ui.main;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Az osztály ahonnan indul az alkalmazás.
 * Az osztály a {@link javafx.application.Application Application} osztályból
 * származik ezáltal a {@link hu.bertalanadam.prt.beadando.ui.main.Main#main(String[])}
 * metódusban a {@link javafx.application.Application#launch(String...)} metódust meghívva
 * elindul az alkalmazás
 */
public class Main extends Application {
	
	/**
	 * Az fxml-ek betöltését elvégző {@link hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader SpringFxmlLoader} osztály.
	 */
	private static final SpringFxmlLoader loader = new SpringFxmlLoader();
	
	/**
	 * A logoláshoz használt {@link org.slf4j.Logger Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(Main.class);

	/**
	 * Az alkalmazást indító main metódus.
	 * @param args A program futásához hozzáadható parancssori argumentumok.
	 */
	public static void main(String[] args) {
		logolo.debug("Javafx UI indul!");
		launch(args); 
	}

	@Override 
	public void start(Stage primaryStage) throws IOException {
		Parent root = (Parent) loader.load("/BejelentkezoFelulet.fxml");
	    
        Scene scene = new Scene(root);
    
        primaryStage.setTitle("Bejelentkezés");
        primaryStage.setScene(scene);
        primaryStage.show(); 
	}
	
	@Override
	public void stop() throws Exception {
		logolo.info("Javafx UI leall.");
		SpringFxmlLoader.close();
	}

}
