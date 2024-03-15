package de.arnorichter.simpleaccounting.view.accounting;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import de.arnorichter.simpleaccounting.data.accountingposition.AccountingPosition;
import de.arnorichter.simpleaccounting.data.accountingposition.AccountingPositionType;
import de.arnorichter.simpleaccounting.service.AccountingPostionService;
import de.arnorichter.simpleaccounting.task.GridRefreshTask;
import de.arnorichter.simpleaccounting.view.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

@PageTitle("Accounting")
@Route(value = "accounting", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class AccountingView extends HorizontalLayout {

	private TextField itemDescriptionTextField;
	private NumberField itemAmountNumberField;
	private Button addItemBtn;
	private static AccountingPostionService accountingPostionService;
	private VerticalLayout leftLayout;
	private VerticalLayout rightLayout;
	private Timer timer;
	private TimerTask timerTask;
	public static DatePicker datePicker;
	public static Grid<AccountingPosition> itemGrid;

	/**
	 * AccountingView
	 *
	 * @param service ItemService
	 */
	public AccountingView(AccountingPostionService service) {
		accountingPostionService = service;
		itemGrid = new Grid<>(AccountingPosition.class, false);
		itemGrid.addColumn(AccountingPosition::getDescription).setHeader("Description").setSortable(true);
		itemGrid.addColumn(new NumberRenderer<>(AccountingPosition::getAmount, NumberFormat.getCurrencyInstance()))
				.setHeader("Amount").setSortable(true).setComparator(AccountingPosition::getAmount);
		itemGrid.addColumn(createStatusComponentRenderer()).setHeader("Type").setSortable(true);
		itemGrid.addColumn(new LocalDateRenderer<>(AccountingPosition::getDate, "dd.MM.yyyy"))
				.setHeader("Date").setSortable(true).setComparator(AccountingPosition::getDate);
		itemGrid.addColumn(deleteItemComponentRenderer()).setHeader("Delete");

		itemDescriptionTextField = new TextField("Description");
		itemDescriptionTextField.setWidth("400px");

		itemAmountNumberField = new NumberField("Amount");
		itemAmountNumberField.setSuffixComponent(new Div("€"));

		datePicker = new DatePicker("Date");
		datePicker.setValue(LocalDate.now());
		datePicker.setInitialPosition(LocalDate.now());

		addItemBtn = new Button("Add Item", event -> saveItem(service, itemDescriptionTextField.getValue(),
				datePicker.getValue(), itemAmountNumberField.getValue()));
		addItemBtn.setWidth("192px");
		addItemBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


		leftLayout = new VerticalLayout(itemDescriptionTextField, new HorizontalLayout(itemAmountNumberField,
				datePicker), addItemBtn);
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
		timerTask = new GridRefreshTask(UI.getCurrent(), accountingPostionService);
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
	 * @param date        Datum
	 * @param amount      Wert
	 */
	private void saveItem(AccountingPostionService service, String description, LocalDate date, Double amount) {
		if (description == null || amount == null) {
			var notification = new Notification("Please fill in all Fields!");
			notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
			notification.setDuration(4000);
			notification.open();
			return;
		}
		AccountingPositionType type = amount < 0 ? AccountingPositionType.EXPENSES : AccountingPositionType.INCOME;
		service.add(new AccountingPosition(description, date, type, amount));
	}

	/**
	 * Styling fuer ItemType Component
	 */
	private static final SerializableBiConsumer<Span, AccountingPosition> statusComponentUpdater =
			(span, accountingPosition) -> {
				boolean isAvailable = AccountingPositionType.INCOME.equals(accountingPosition.getType());
				String theme = String.format("badge %s", isAvailable ? "success" : "error");
				span.getElement().setAttribute("theme", theme);
				span.setText(accountingPosition.getType().name());
			};

	/**
	 * Renderer für ItemType Component in Grid
	 *
	 * @return ComponentRenderer
	 */
	private static ComponentRenderer<Span, AccountingPosition> createStatusComponentRenderer() {
		return new ComponentRenderer<>(Span::new, statusComponentUpdater);
	}

	/**
	 * Renderer für delete Component in Grid
	 *
	 * @return ComponentRenderer
	 */
	private static ComponentRenderer<Button, AccountingPosition> deleteItemComponentRenderer() {
		return new ComponentRenderer<>(Button::new, (button, accountingPosition) -> {
			button.addThemeVariants(ButtonVariant.LUMO_ICON,
					ButtonVariant.LUMO_ERROR,
					ButtonVariant.LUMO_TERTIARY);
			button.addClickListener(e -> AccountingView.accountingPostionService.delete(accountingPosition.getId()));
			button.setIcon(new Icon(VaadinIcon.TRASH));
		});
	}
}
