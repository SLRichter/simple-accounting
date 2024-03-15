package de.arnorichter.simpleaccounting.repository;

import de.arnorichter.simpleaccounting.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Das UserRepository wird verwendet, um auf die Datenbanktabelle für Benutzer zuzugreifen.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	/**
	 * Sucht nach einem Benutzer anhand seines Benutzernamens.
	 *
	 * @param username Der Benutzername des Benutzers.
	 * @return Das gefundene Benutzerobjekt oder null, wenn kein Benutzer mit dem angegebenen Benutzernamen gefunden
	 * wurde.
	 */
	User findByUsername(String username);
}
