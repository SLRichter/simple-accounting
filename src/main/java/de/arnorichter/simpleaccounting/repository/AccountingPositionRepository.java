package de.arnorichter.simpleaccounting.repository;

import de.arnorichter.simpleaccounting.data.accountingposition.AccountingPosition;
import de.arnorichter.simpleaccounting.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Das AccountingPositionRepository wird verwendet, um auf die Datenbanktabelle für Buchungsposten zuzugreifen.
 */
public interface AccountingPositionRepository extends JpaRepository<AccountingPosition, Long>,
		JpaSpecificationExecutor<User> {

	/**
	 * Sucht nach Buchungsposten anhand des Monats und Jahres.
	 *
	 * @param month Der Monat.
	 * @param year  Das Jahr.
	 * @return Eine Liste von Buchungsposten, die im angegebenen Monat und Jahr gefunden wurden.
	 */
	@Query(value = "Select * From accounting_position where Month(date) = ?1 and Year(date) = ?2", nativeQuery = true)
	public List<AccountingPosition> findByMonth(int month, int year);
}
