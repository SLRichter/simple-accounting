package de.arnorichter.simpleaccounting.service;

import de.arnorichter.simpleaccounting.data.user.User;
import de.arnorichter.simpleaccounting.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Der UserService stellt Methoden zum Verwalten von Benutzern bereit.
 */
@Service
public class UserService {

	private final UserRepository repository;

	/**
	 * Konstruktor für den UserService.
	 *
	 * @param repository Das Repository für Benutzer.
	 */
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	/**
	 * Ruft einen Benutzer anhand seiner ID ab.
	 *
	 * @param id Die ID des Benutzers.
	 * @return Ein Optional, das den Benutzer enthält, falls vorhanden.
	 */
	public Optional<User> get(Long id) {
		return repository.findById(id);
	}

	/**
	 * Aktualisiert einen vorhandenen Benutzer.
	 *
	 * @param entity Der Benutzer, der aktualisiert werden soll.
	 * @return Der aktualisierte Benutzer.
	 */
	public User update(User entity) {
		return repository.save(entity);
	}

	/**
	 * Löscht einen Benutzer anhand seiner ID.
	 *
	 * @param id Die ID des zu löschenden Benutzers.
	 */
	public void delete(Long id) {
		repository.deleteById(id);
	}

	/**
	 * Gibt eine Seite von Benutzern zurück.
	 *
	 * @param pageable Die Pagination-Informationen.
	 * @return Die Seite von Benutzern.
	 */
	public Page<User> list(Pageable pageable) {
		return repository.findAll(pageable);
	}

	/**
	 * Gibt eine Seite von Benutzern basierend auf einem bestimmten Filter zurück.
	 *
	 * @param pageable Die Pagination-Informationen.
	 * @param filter   Der Filter für die Benutzer.
	 * @return Die Seite von Benutzern, die dem Filter entsprechen.
	 */
	public Page<User> list(Pageable pageable, Specification<User> filter) {
		return repository.findAll(filter, pageable);
	}

	/**
	 * Zählt die Anzahl der Benutzer.
	 *
	 * @return Die Anzahl der Benutzer.
	 */
	public int count() {
		return (int) repository.count();
	}

}
