package de.arnorichter.simpleaccounting.tasks;

import com.vaadin.flow.component.UI;
import de.arnorichter.simpleaccounting.services.ItemService;
import de.arnorichter.simpleaccounting.views.accounting.AccountingView;

import java.util.TimerTask;

/**
 * GridRefreshTask um die Daten im Grid zu aktualisieren
 */
public class GridRefreshTask extends TimerTask {
    private final UI ui;
    private final ItemService service;

    /**
     * Konstruktur
     *
     * @param ui aktuelle UI View
     */
    public GridRefreshTask(UI ui, ItemService service) {
        this.ui = ui;
        this.service = service;
    }

    /**
     * Grid-Daten aktualisieren
     */
    @Override
    public void run() {
        ui.access(() -> AccountingView.itemGrid.setItems(service.findAll()));
    }
}