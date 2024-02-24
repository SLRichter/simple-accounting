package de.arnorichter.simpleaccounting.views.settings;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.Lumo;
import de.arnorichter.simpleaccounting.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
@AnonymousAllowed
@PermitAll
@Uses(Icon.class)
public class SettingsView extends VerticalLayout {

    public SettingsView() {

        var themeToggle = new Checkbox("Dark theme");
        themeToggle.addValueChangeListener(e -> {
            setTheme(e.getValue());
        });
        add(themeToggle);
    }

    private void setTheme(boolean dark) {
        var js = "document.documentElement.setAttribute('theme', $0)";

        getElement().executeJs(js, dark ? Lumo.DARK : Lumo.LIGHT);
    }
}
