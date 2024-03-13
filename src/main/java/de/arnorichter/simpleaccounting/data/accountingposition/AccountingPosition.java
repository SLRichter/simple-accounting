package de.arnorichter.simpleaccounting.data.accountingposition;

import de.arnorichter.simpleaccounting.data.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "accounting_position")
public class AccountingPosition extends AbstractEntity {

	private String description;
	private LocalDate date;
	private AccountingPositionType type;
	private Double amount;

	public AccountingPosition() {
	}

	public AccountingPosition(String description, LocalDate date, AccountingPositionType type, Double amount) {
		this.description = description;
		this.date = date;
		this.type = type;
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public AccountingPositionType getType() {
		return type;
	}

	public void setType(AccountingPositionType type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
