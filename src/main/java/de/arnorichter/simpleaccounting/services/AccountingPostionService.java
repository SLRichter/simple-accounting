package de.arnorichter.simpleaccounting.services;

import de.arnorichter.simpleaccounting.data.item.AccountingPosition;
import de.arnorichter.simpleaccounting.data.item.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountingPostionService {

	private final ItemRepository repository;

	public AccountingPostionService(ItemRepository repository) {
		this.repository = repository;
	}

	public Optional<AccountingPosition> get(Long id) {
		return repository.findById(id);
	}

	public AccountingPosition add(AccountingPosition entity) {
		return repository.save(entity);
	}

	public AccountingPosition update(AccountingPosition entity) {
		return repository.save(entity);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Page<AccountingPosition> list(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public List<AccountingPosition> findAll() {
		return repository.findAll();
	}

//    public Page<Item> list(Pageable pageable, Specification<Item> filter) {
//        return repository.findAll(filter, pageable);
//    }

	public int count() {
		return (int) repository.count();
	}

}
