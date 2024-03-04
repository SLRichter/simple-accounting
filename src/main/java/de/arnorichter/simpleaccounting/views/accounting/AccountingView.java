package de.arnorichter.simpleaccounting.views.accounting;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import de.arnorichter.simpleaccounting.data.item.Item;
import de.arnorichter.simpleaccounting.data.item.ItemType;
import de.arnorichter.simpleaccounting.services.ItemService;
import de.arnorichter.simpleaccounting.tasks.GridRefreshTask;
import de.arnorichter.simpleaccounting.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

@PageTitle("Accounting")
@Route(value = "accounting", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class AccountingView extends HorizontalLayout {

    private TextField itemDescriptionTextField;
    private NumberField itemAmountNumberField;
    private ComboBox<ItemType> itemTypeComboBox;
    private Button addItem;
    private static ItemService itemService;
    private VerticalLayout leftLayout;
    private VerticalLayout rightLayout;
    private Timer timer;
    private TimerTask timerTask;
    public static Grid<Item> itemGrid;

    /**
     * AccountingView
     *
     * @param service ItemService
     */
    public AccountingView(ItemService service) {
        itemService = service;
        itemGrid = new Grid<>(Item.class, false);
        itemGrid.addColumn(Item::getDescription).setHeader("Description");
        itemGrid.addColumn(Item::getAmount).setHeader("Amount");
        itemGrid.addColumn(createStatusComponentRenderer()).setHeader("Type");
        itemGrid.addColumn(new LocalDateTimeRenderer<>(Item::getDateTime, "MM/dd/yyyy - HH:mm")).setHeader("DateTime");
        itemGrid.addColumn(deleteItemComponentRenderer()).setHeader("Delete");

        itemDescriptionTextField = new TextField("Description");
        itemAmountNumberField = new NumberField("Amount");
        itemAmountNumberField.setSuffixComponent(new Div("€"));
        itemTypeComboBox = new ComboBox<>("Type");
        itemTypeComboBox.setItems(ItemType.values());
        itemTypeComboBox.setLabel("Type");
        addItem = new Button("Add Item", event ->
                saveItem(service, itemDescriptionTextField.getValue(), itemTypeComboBox.getValue(), BigDecimal.valueOf(itemAmountNumberField.getValue())));

        leftLayout = new VerticalLayout(itemDescriptionTextField, itemAmountNumberField, itemTypeComboBox, addItem);
        leftLayout.setWidth("40%");
        rightLayout = new VerticalLayout(itemGrid);

        setMargin(true);
        add(leftLayout, rightLayout);
    }

    /**
     * Beim aufrufen der View Timer mit Timertask starten
     *
     * @param attachEvent AufrufenEvent
     */
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        timer = new Timer();
        timerTask = new GridRefreshTask(UI.getCurrent(), itemService);
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    /**
     * Beim verlassen der View Timertask und Timer stoppen
     *
     * @param detachEvent VerlassenEvent
     */
    @Override
    protected void onDetach(DetachEvent detachEvent) {
        timerTask.cancel();
        timer.cancel();
    }

    /**
     * Item in Datenbank speichern
     *
     * @param service     ItemsService
     * @param description Beschreibung
     * @param type        Itemtype
     * @param amount      Wert
     */
    private void saveItem(ItemService service, String description, ItemType type, BigDecimal amount) {
        service.add(new Item(description, LocalDateTime.now(), type, amount.setScale(2, RoundingMode.HALF_UP)));
    }

    /**
     * Styling fuer ItemType Component
     */
    private static final SerializableBiConsumer<Span, Item> statusComponentUpdater = (span, item) -> {
        boolean isAvailable = ItemType.PLUS.equals(item.getType());
        String theme = String.format("badge %s", isAvailable ? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        span.setText(item.getType().name());
    };

    /**
     * Renderer für ItemType Component in Grid
     *
     * @return ComponentRenderer
     */
    private static ComponentRenderer<Span, Item> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }

    /**
     * Renderer für delete Component in Grid
     *
     * @return ComponentRenderer
     */
    private static ComponentRenderer<Button, Item> deleteItemComponentRenderer() {
        return new ComponentRenderer<>(Button::new, (button, item) -> {
            button.addThemeVariants(ButtonVariant.LUMO_ICON,
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_TERTIARY);
            button.addClickListener(e -> AccountingView.itemService.delete(item.getId()));
            button.setIcon(new Icon(VaadinIcon.TRASH));
        });
    }
}
