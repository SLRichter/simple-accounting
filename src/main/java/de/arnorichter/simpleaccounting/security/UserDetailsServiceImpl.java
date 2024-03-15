package de.arnorichter.simpleaccounting.security;

import de.arnorichter.simpleaccounting.data.user.User;
import de.arnorichter.simpleaccounting.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Die Klasse UserDetailsServiceImpl implementiert den UserDetailsService für die Benutzerauthentifizierung.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	/**
	 * Konstruktor für UserDetailsServiceImpl.
	 *
	 * @param userRepository Das UserRepository-Objekt.
	 */
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Lädt einen Benutzer anhand seines Benutzernamens.
	 *
	 * @param username Der Benutzername des Benutzers.
	 * @return Ein UserDetails-Objekt für den gefundenen Benutzer.
	 * @throws UsernameNotFoundException Wenn kein Benutzer mit dem angegebenen Benutzernamen gefunden wurde.
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		} else {
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getHashedPassword(),
					getAuthorities(user));
		}
	}

	/**
	 * Erstellt die Autoritäten für einen Benutzer anhand seiner Rollen.
	 *
	 * @param user Der Benutzer.
	 * @return Eine Liste von GrantedAuthority-Objekten für den Benutzer.
	 */
	private static List<GrantedAuthority> getAuthorities(User user) {
		return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.collect(Collectors.toList());
	}
}
