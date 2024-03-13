package de.arnorichter.simpleaccounting.tasks;

import com.vaadin.flow.component.UI;
import de.arnorichter.simpleaccounting.services.AccountingPostionService;
import de.arnorichter.simpleaccounting.views.accounting.AccountingView;

import java.util.TimerTask;

/**
 * GridRefreshTask um die Daten im Grid zu aktualisieren
 */
public class GridRefreshTask extends TimerTask {
	private final UI ui;
	private final AccountingPostionService service;

	/**
	 * Konstruktur
	 *
	 * @param ui aktuelle UI View
	 */
	public GridRefreshTask(UI ui, AccountingPostionService service) {
		this.ui = ui;
		this.service = service;
	}

	/**
	 * Grid-Daten aktualisieren
	 */
	@Override
	public void run() {
		ui.access(() -> {
//			var sumMonth = service.findByMonth(AccountingView.datePicker.getValue().getMonth().getValue());
			AccountingView.itemGrid.setItems(service.findAll());
//			DecimalFormat dec = new DecimalFormat("#0.00");
//			AccountingView.month.setText("Current Month Sum: " +
//					dec.format(sumMonth.stream().mapToDouble(AccountingPosition::getAmount).sum()));
		});
	}
}