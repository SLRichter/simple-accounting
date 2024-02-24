package de.arnorichter.simpleaccounting.views.settings;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.arnorichter.simpleaccounting.views.MainLayout;

@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class SettingsView extends Composite<VerticalLayout> {

    public SettingsView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
    }
}
