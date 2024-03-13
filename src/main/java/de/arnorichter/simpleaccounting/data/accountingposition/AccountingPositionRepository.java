package de.arnorichter.simpleaccounting.data.accountingposition;


import de.arnorichter.simpleaccounting.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountingPositionRepository extends JpaRepository<AccountingPosition, Long>,
		JpaSpecificationExecutor<User> {

	@Query(value = "Select * From accounting_position where Month(date) = ?1", nativeQuery = true)
	public List<AccountingPosition> findByMonth(int month);
}
