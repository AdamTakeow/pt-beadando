package hu.bertalanadam.prt.beadando.ui.main;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

/**
 * Ez az osztály segít az fxml állományok betöltésében, valamint hozzáadja
 * a betöltött fxml-ek kontroller osztályait az 
 * {@link hu.bertalanadam.prt.beadando.ui.main.SpringFxmlLoader#applicationContext applicationContext}-hez
 * bean-ként hogy injektálható legyen.
 */
public class SpringFxmlLoader {
	
	/**
	 * A logoláshoz használt {@link org.slf4j.Logger Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(SpringFxmlLoader.class);

	/**
	 * Az az {@link org.springframework.context.ApplicationContext ApplicationContext}, amely
	 * a {@code spring-ui.xml}-t betöltve elvégzi a spring konfigurációját.
	 */
	private static final ApplicationContext applicationContext =
			new ClassPathXmlApplicationContext("/spring-ui.xml");

	/**
	 * A metódus amely az fxml-ek betöltését végzi.
	 * Ezt a következő képpen teszi: nyit egy streamet amin keresztül be tudja olvasni
	 * az fxml-t.
	 * A metódusban létrejön egy FXMLLoader példány, amelynek a ControllerFactory-ját.
	 * A beállított Callback névtelen osztály létrehoz a context-be egy bean-t a betöltött 
	 * fxml kontrollerének majd visszaad belőle egy példányt.
	 * Végül a metódus visszaadja az FXMLLoader load metódusa által betöltött fxml-t amelyet a streamen talál meg.
	 * @param url A betölteni kívánt fxml fájl elérési útja.
	 * @return A betöltött fxml Object-ként.
	 */
	public Object load(String url) {
		try (InputStream fxmlStream = SpringFxmlLoader.class.getResourceAsStream(url)) {
			
			logolo.debug("Felulet betoltese: " + url);
			
			FXMLLoader loader = new FXMLLoader();
			
			loader.setControllerFactory(
					new Callback<Class<?>, Object>() {
						@Override
						public Object call(Class<?> clazz) {
							return applicationContext.getBean(clazz);
						}
					}
			);
			
			return loader.load(fxmlStream);
			
		} catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}
	
	/**
	 * A metódus meghívására bezárjuk az ApplicationContext-et.
	 */
	public static void close(){
		logolo.debug("Spring ApplicationContext leallitasa");
		((ConfigurableApplicationContext)applicationContext).close();
	}
}