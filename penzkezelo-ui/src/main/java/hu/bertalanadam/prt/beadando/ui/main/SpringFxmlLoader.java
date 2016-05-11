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

public class SpringFxmlLoader {
	
	private static Logger logolo = LoggerFactory.getLogger(SpringFxmlLoader.class);

	private static final ApplicationContext applicationContext =
			new ClassPathXmlApplicationContext("/spring-ui.xml");

	public Object load(String url) {
		try (InputStream fxmlStream = SpringFxmlLoader.class.getResourceAsStream(url)) {
			
			logolo.debug("Betoltes: " + url);
			
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
	
	public static void close(){
		logolo.debug("Spring ApplicationContext leallitasa");
		((ConfigurableApplicationContext)applicationContext).close();
	}
}