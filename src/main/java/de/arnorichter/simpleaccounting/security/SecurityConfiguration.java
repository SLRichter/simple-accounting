package de.arnorichter.simpleaccounting.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import de.arnorichter.simpleaccounting.view.login.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Die Klasse SecurityConfiguration konfiguriert die Sicherheitseinstellungen für die Anwendung.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

	/**
	 * Erstellt einen Passwortencoder.
	 *
	 * @return Der Passwortencoder.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Konfiguriert die HTTP-Sicherheit für die Anwendung.
	 *
	 * @param http Der HttpSecurity-Builder.
	 * @throws Exception Wenn ein Fehler bei der Konfiguration auftritt.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Erlaubt den Zugriff auf bestimmte Ressourcen ohne Authentifizierung
		http.authorizeHttpRequests(
				authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll());

		// Erlaubt den Zugriff auf Icons aus dem line-awesome-Addon ohne Authentifizierung
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll());

		// Verwendet die Standardkonfiguration von VaadinWebSecurity und setzt die LoginView auf LoginView.class
		super.configure(http);
		setLoginView(http, LoginView.class);
	}
}
