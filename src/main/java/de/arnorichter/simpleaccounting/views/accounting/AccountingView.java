package de.arnorichter.simpleaccounting.views.accounting;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import de.arnorichter.simpleaccounting.data.item.Item;
import de.arnorichter.simpleaccounting.data.item.ItemType;
import de.arnorichter.simpleaccounting.services.ItemService;
import de.arnorichter.simpleaccounting.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDateTime;

@PageTitle("Accounting")
@Route(value = "accounting", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class AccountingView extends HorizontalLayout {

    private TextField itemDescription;
    private TextField amount;
    private ComboBox<ItemType> itemType;
    private Button addItem;

    public AccountingView(ItemService service) {
        itemDescription = new TextField("Your name");
        addItem = new Button("Add Item", event -> saveItem(service));

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, itemDescription, addItem);
        add(itemDescription, addItem);
    }

    private void saveItem(ItemService service) {
        service.add(new Item("Test", LocalDateTime.now(), ItemType.PLUS, 10.50));
    }
}
