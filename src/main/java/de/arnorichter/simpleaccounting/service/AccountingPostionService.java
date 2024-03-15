package de.arnorichter.simpleaccounting.service;

import de.arnorichter.simpleaccounting.data.accountingposition.AccountingPosition;
import de.arnorichter.simpleaccounting.repository.AccountingPositionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Der AccountingPositionService stellt Methoden zum Verwalten von Buchhaltungspositionen bereit.
 */
@Service
public class AccountingPostionService {

	private final AccountingPositionRepository repository;

	/**
	 * Konstruktor für den AccountingPositionService.
	 *
	 * @param repository Das Repository für AccountingPositionen.
	 */
	public AccountingPostionService(AccountingPositionRepository repository) {
		this.repository = repository;
	}

	/**
	 * Ruft eine Buchhaltungsposition anhand ihrer ID ab.
	 *
	 * @param id Die ID der Buchhaltungsposition.
	 * @return Ein Optional, das die Buchhaltungsposition enthält, falls vorhanden.
	 */
	public Optional<AccountingPosition> get(Long id) {
		return repository.findById(id);
	}

	/**
	 * Fügt eine neue Buchhaltungsposition hinzu.
	 *
	 * @param entity Die Buchhaltungsposition, die hinzugefügt werden soll.
	 * @return Die hinzugefügte Buchhaltungsposition.
	 */
	public AccountingPosition add(AccountingPosition entity) {
		return repository.save(entity);
	}

	/**
	 * Aktualisiert eine vorhandene Buchhaltungsposition.
	 *
	 * @param entity Die Buchhaltungsposition, die aktualisiert werden soll.
	 * @return Die aktualisierte Buchhaltungsposition.
	 */
	public AccountingPosition update(AccountingPosition entity) {
		return repository.save(entity);
	}

	/**
	 * Löscht eine Buchhaltungsposition anhand ihrer ID.
	 *
	 * @param id Die ID der zu löschenden Buchhaltungsposition.
	 */
	public void delete(Long id) {
		repository.deleteById(id);
	}

	/**
	 * Gibt eine Seite von Buchhaltungspositionen zurück.
	 *
	 * @param pageable Die Pagination-Informationen.
	 * @return Die Seite von Buchhaltungspositionen.
	 */
	public Page<AccountingPosition> list(Pageable pageable) {
		return repository.findAll(pageable);
	}

	/**
	 * Gibt eine Liste aller Buchhaltungspositionen zurück.
	 *
	 * @return Die Liste aller Buchhaltungspositionen.
	 */
	public List<AccountingPosition> findAll() {
		return repository.findAll();
	}

	/**
	 * Gibt eine Liste von Buchhaltungspositionen für einen bestimmten Monat und ein bestimmtes Jahr zurück.
	 *
	 * @param month Der Monat.
	 * @param year  Das Jahr.
	 * @return Die Liste der Buchhaltungspositionen für den angegebenen Monat und das Jahr.
	 */
	public List<AccountingPosition> findByMonthYear(int month, int year) {
		return repository.findByMonth(month, year);
	}

	/**
	 * Zählt die Anzahl der Buchhaltungspositionen.
	 *
	 * @return Die Anzahl der Buchhaltungspositionen.
	 */
	public int count() {
		return (int) repository.count();
	}
}
