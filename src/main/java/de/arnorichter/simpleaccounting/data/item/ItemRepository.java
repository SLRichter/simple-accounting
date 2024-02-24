package de.arnorichter.simpleaccounting.data.item;


import de.arnorichter.simpleaccounting.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<User> {
}
