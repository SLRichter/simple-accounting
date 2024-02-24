package de.arnorichter.simpleaccounting.views.accounting;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import de.arnorichter.simpleaccounting.security.AuthenticatedUser;
import de.arnorichter.simpleaccounting.security.SecurityConfiguration;
import de.arnorichter.simpleaccounting.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.io.File;

@PageTitle("Accounting")
@Route(value = "accounting", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class AccountingView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;
    AuthenticatedUser user;

    public AccountingView(AuthenticatedUser user) {
        this.user = user;
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        var pw = user.get().get().getHashedPassword();
        SecurityConfiguration conf = new SecurityConfiguration();
        var encPw = conf.passwordEncoder().matches("user", pw);
        String userDirectory = new File("").getAbsolutePath();
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
            System.out.println(userDirectory);
            System.out.println(encPw);
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
    }

}
