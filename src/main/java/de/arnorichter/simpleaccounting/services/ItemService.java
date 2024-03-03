package de.arnorichter.simpleaccounting.services;

import de.arnorichter.simpleaccounting.data.item.Item;
import de.arnorichter.simpleaccounting.data.item.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public Optional<Item> get(Long id) {
        return repository.findById(id);
    }

    public Item add(Item entity) {
        return repository.save(entity);
    }

    public Item update(Item entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Item> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Item> findAll() {
        return repository.findAll();
    }

//    public Page<Item> list(Pageable pageable, Specification<Item> filter) {
//        return repository.findAll(filter, pageable);
//    }

    public int count() {
        return (int) repository.count();
    }

}
