package de.arnorichter.simpleaccounting.data.accountingposition;

import de.arnorichter.simpleaccounting.data.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * Die Klasse AccountingPosition repräsentiert eine Buchungsposition.
 */
@Entity
@Table(name = "accounting_position")
public class AccountingPosition extends AbstractEntity {

	private String description;
	private LocalDate date;
	private AccountingPositionType type;
	private Double amount;

	/**
	 * Standardkonstruktor für AccountingPosition.
	 */
	public AccountingPosition() {
	}

	/**
	 * Konstruktor für AccountingPosition mit Parametern.
	 *
	 * @param description Die Beschreibung der Buchungsposition.
	 * @param date        Das Datum der Buchungsposition.
	 * @param type        Der Typ der Buchungsposition.
	 * @param amount      Der Betrag der Buchungsposition.
	 */
	public AccountingPosition(String description, LocalDate date, AccountingPositionType type, Double amount) {
		this.description = description;
		this.date = date;
		this.type = type;
		this.amount = amount;
	}

	/**
	 * Ruft die Beschreibung der Buchungsposition ab.
	 *
	 * @return Die Beschreibung der Buchungsposition.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Legt die Beschreibung der Buchungsposition fest.
	 *
	 * @param description Die Beschreibung der Buchungsposition.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Ruft das Datum der Buchungsposition ab.
	 *
	 * @return Das Datum der Buchungsposition.
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Legt das Datum der Buchungsposition fest.
	 *
	 * @param date Das Datum der Buchungsposition.
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Ruft den Typ der Buchungsposition ab.
	 *
	 * @return Der Typ der Buchungsposition.
	 */
	public AccountingPositionType getType() {
		return type;
	}

	/**
	 * Legt den Typ der Buchungsposition fest.
	 *
	 * @param type Der Typ der Buchungsposition.
	 */
	public void setType(AccountingPositionType type) {
		this.type = type;
	}

	/**
	 * Ruft den Betrag der Buchungsposition ab.
	 *
	 * @return Der Betrag der Buchungsposition.
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Legt den Betrag der Buchungsposition fest.
	 *
	 * @param amount Der Betrag der Buchungsposition.
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
