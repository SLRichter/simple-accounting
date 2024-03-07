package de.arnorichter.simpleaccounting.data.item;

import de.arnorichter.simpleaccounting.data.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounting_item")
public class AccountingPosition extends AbstractEntity {

	private String description;
	private LocalDateTime dateTime;
	private AccountingPositionType type;
	private BigDecimal amount;

	public AccountingPosition() {
	}

	public AccountingPosition(String description, LocalDateTime dateTime, AccountingPositionType type,
							  BigDecimal amount) {
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

	public AccountingPositionType getType() {
		return type;
	}

	public void setType(AccountingPositionType type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
