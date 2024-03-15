package de.arnorichter.simpleaccounting.view.settings;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import de.arnorichter.simpleaccounting.view.MainLayout;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
@RolesAllowed("ADMIN")
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
