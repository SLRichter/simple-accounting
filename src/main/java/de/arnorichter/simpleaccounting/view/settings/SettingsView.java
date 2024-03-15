package de.arnorichter.simpleaccounting.view.settings;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import de.arnorichter.simpleaccounting.view.MainLayout;
import jakarta.annotation.security.RolesAllowed;

/**
 * Die Klasse SettingsView repräsentiert die Ansicht für Einstellungen.
 */
@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class SettingsView extends VerticalLayout {

	private Button lightButton;

	/**
	 * Konstruktor für die SettingsView.
	 */
	public SettingsView() {
		lightButton = new Button("Switch Theme", this::setThemeLight);
		add(lightButton);
	}

	/**
	 * Methode zum Umschalten des Themes.
	 *
	 * @param event Das ClickEvent für den Button.
	 */
	private void setThemeLight(ClickEvent<Button> event) {
		ThemeList test = UI.getCurrent().getElement().getThemeList();
		if (test.isEmpty()) {
			test.add(Lumo.DARK);
		} else {
			test.remove(Lumo.DARK);
		}
	}
}
