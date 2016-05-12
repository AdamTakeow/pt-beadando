package hu.bertalanadam.prt.beadando.ui.main;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static final SpringFxmlLoader loader = new SpringFxmlLoader();
	
	private static Logger logolo = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		logolo.debug("Javafx UI indul!");
		launch(args); 
	}

	@Override public void start(Stage primaryStage) throws IOException {
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
