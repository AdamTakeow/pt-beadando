package hu.bertalanadam.prt.beadando.ui.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static final SpringFxmlLoader loader = new SpringFxmlLoader();

	public static void main(String[] args) {
		  launch(args); 
	}

	@Override public void start(Stage stage) throws IOException {
		Parent root = (Parent) loader.load("/BejelentkezoFelulet.fxml");
	    
        Scene scene = new Scene(root, 300, 275);
    
        stage.setTitle("Bejelentkezés");
        stage.setScene(scene);
        stage.show(); 
	}

}
