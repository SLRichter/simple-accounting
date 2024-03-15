package de.arnorichter.simpleaccounting.data;

import jakarta.persistence.*;

/**
 * Die abstrakte Klasse AbstractEntity dient als Basisklasse für alle Entitätsklassen.
 */
@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
	// Der initiale Wert berücksichtigt IDs für Demodaten aus data.sql
	@SequenceGenerator(name = "idgenerator", initialValue = 1000)
	private Long id;

	@Version
	private int version;

	/**
	 * Ruft die ID der Entität ab.
	 *
	 * @return Die ID der Entität.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Legt die ID der Entität fest.
	 *
	 * @param id Die ID der Entität.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Ruft die Versionsnummer der Entität ab.
	 *
	 * @return Die Versionsnummer der Entität.
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Gibt den Hashcode für die Entität zurück.
	 *
	 * @return Der Hashcode für die Entität.
	 */
	@Override
	public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		}
		return super.hashCode();
	}

	/**
	 * Vergleicht diese Entität mit einem anderen Objekt auf Gleichheit.
	 *
	 * @param obj Das Objekt, das mit dieser Entität verglichen werden soll.
	 * @return true, wenn das übergebene Objekt mit dieser Entität gleich ist, andernfalls false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractEntity that)) {
			return false; // null or not an AbstractEntity class
		}
		if (getId() != null) {
			return getId().equals(that.getId());
		}
		return super.equals(that);
	}
}
