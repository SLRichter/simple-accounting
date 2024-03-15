package de.arnorichter.simpleaccounting;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import de.arnorichter.simpleaccounting.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * Der Einstiegspunkt der Spring Boot-Anwendung.
 * <p>
 * Verwenden Sie die @PWA Annotation, um die Anwendung auf Telefonen, Tablets
 * und einigen Desktop-Browsern installierbar zu machen.
 */
@SpringBootApplication
@Theme(value = "simple-accounting")
@Push
public class Application implements AppShellConfigurator {

	/**
	 * Die main-Methode startet die Spring Boot-Anwendung.
	 *
	 * @param args Die Befehlszeilenargumente.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Erstellt den Bean für die Initialisierung der Datenquelle.
	 *
	 * @param dataSource Die Datenquelle.
	 * @param properties Die Initialisierungseigenschaften für SQL.
	 * @param repository Das Benutzer-Repository.
	 * @return Der Datenbankinitialisierer.
	 */
	@Bean
	SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(DataSource dataSource,
																			   SqlInitializationProperties properties,
																			   UserRepository repository) {
		// Dieser Bean stellt sicher, dass die Datenbank nur initialisiert wird, wenn sie leer ist
		return new SqlDataSourceScriptDatabaseInitializer(dataSource, properties) {
			@Override
			public boolean initializeDatabase() {
				if (repository.count() == 0L) {
					return super.initializeDatabase();
				}
				return false;
			}
		};
	}
}
