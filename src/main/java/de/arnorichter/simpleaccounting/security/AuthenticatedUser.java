package de.arnorichter.simpleaccounting.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import de.arnorichter.simpleaccounting.data.user.User;
import de.arnorichter.simpleaccounting.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Die Klasse AuthenticatedUser verwaltet den authentifizierten Benutzer.
 */
@Component
public class AuthenticatedUser {

	private final UserRepository userRepository;
	private final AuthenticationContext authenticationContext;

	/**
	 * Konstruktor für AuthenticatedUser.
	 *
	 * @param authenticationContext Das AuthenticationContext-Objekt.
	 * @param userRepository        Das UserRepository-Objekt.
	 */
	public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
		this.userRepository = userRepository;
		this.authenticationContext = authenticationContext;
	}

	/**
	 * Ruft den authentifizierten Benutzer ab.
	 *
	 * @return Ein Optional, das den authentifizierten Benutzer enthält, falls vorhanden.
	 */
	@Transactional
	public Optional<User> get() {
		return authenticationContext.getAuthenticatedUser(UserDetails.class)
				.map(userDetails -> userRepository.findByUsername(userDetails.getUsername()));
	}

	/**
	 * Meldet den Benutzer ab.
	 */
	public void logout() {
		authenticationContext.logout();
	}

}
