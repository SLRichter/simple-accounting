package de.arnorichter.simpleaccounting.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounting_item")
public class Item extends AbstractEntity {

    private String description;
    private LocalDateTime dateTime;
    private ItemType type;
    private double amount;

    public Item(String description, LocalDateTime dateTime, ItemType type, double amount) {
        this.description = description;
        this.dateTime = dateTime;
        this.type = type;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
