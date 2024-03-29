package de.arnorichter.simpleaccounting.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.arnorichter.simpleaccounting.data.user.User;
import de.arnorichter.simpleaccounting.security.AuthenticatedUser;
import de.arnorichter.simpleaccounting.view.accounting.AccountingView;
import de.arnorichter.simpleaccounting.view.settings.SettingsView;
import de.arnorichter.simpleaccounting.view.stats.StatsView;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.io.ByteArrayInputStream;
import java.util.Optional;

/**
 * Die Hauptansicht ist ein Top-Level-Platzhalter für andere Ansichten.
 */
public class MainLayout extends AppLayout {

	private H2 viewTitle;

	private AuthenticatedUser authenticatedUser;
	private AccessAnnotationChecker accessChecker;

	/**
	 * Konstruktor für das MainLayout.
	 *
	 * @param authenticatedUser Das AuthenticatedUser-Objekt.
	 * @param accessChecker     Der AccessAnnotationChecker.
	 */
	public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
		this.authenticatedUser = authenticatedUser;
		this.accessChecker = accessChecker;

		setPrimarySection(Section.DRAWER);
		addDrawerContent();
		addHeaderContent();
	}

	/**
	 * Fügt Inhalte zum Header hinzu, einschließlich des Titels und des Menü-Toggles.
	 */
	private void addHeaderContent() {
		DrawerToggle toggle = new DrawerToggle();
		toggle.setAriaLabel("Menu toggle");

		viewTitle = new H2();
		viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

		addToNavbar(true, toggle, viewTitle);
	}

	/**
	 * Fügt Inhalte zum Drawer hinzu, einschließlich der Navigationsleiste und des Footers.
	 */
	private void addDrawerContent() {
		H1 appName = new H1("Simple Accounting");
		appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
		Header header = new Header(appName);

		Scroller scroller = new Scroller(createNavigation());

		addToDrawer(header, scroller, createFooter());
	}

	/**
	 * Erstellt die Navigationsleiste mit den verfügbaren Ansichten.
	 *
	 * @return Die SideNav mit den Navigationspunkten.
	 */
	private SideNav createNavigation() {
		SideNav nav = new SideNav();

		if (accessChecker.hasAccess(AccountingView.class)) {
			nav.addItem(new SideNavItem("Accounting", AccountingView.class, LineAwesomeIcon.GLOBE_SOLID.create()));
		}
		if (accessChecker.hasAccess(StatsView.class)) {
			nav.addItem(new SideNavItem("Month Stats", StatsView.class, LineAwesomeIcon.CHART_BAR_SOLID.create()));
		}
		if (accessChecker.hasAccess(SettingsView.class)) {
			nav.addItem(new SideNavItem("Settings", SettingsView.class, LineAwesomeIcon.COG_SOLID.create()));
		}

		return nav;
	}

	/**
	 * Erstellt den Footer für den Drawer, der Informationen zum angemeldeten Benutzer enthält.
	 *
	 * @return Der Footer mit Benutzerinformationen oder einem Anmelde-Link.
	 */
	private Footer createFooter() {
		Footer layout = new Footer();

		Optional<User> maybeUser = authenticatedUser.get();
		if (maybeUser.isPresent()) {
			User user = maybeUser.get();

			Avatar avatar = new Avatar(user.getName());
			StreamResource resource = new StreamResource("profile-pic",
					() -> new ByteArrayInputStream(user.getProfilePicture()));
			avatar.setImageResource(resource);
			avatar.setThemeName("xsmall");
			avatar.getElement().setAttribute("tabindex", "-1");

			MenuBar userMenu = new MenuBar();
			userMenu.setThemeName("tertiary-inline contrast");

			MenuItem userName = userMenu.addItem("");
			Div div = new Div();
			div.add(avatar);
			div.add(user.getName());
			div.add(new Icon("lumo", "dropdown"));
			div.getElement().getStyle().set("display", "flex");
			div.getElement().getStyle().set("align-items", "center");
			div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
			userName.add(div);
			userName.getSubMenu().addItem("Sign out", e -> {
				authenticatedUser.logout();
			});

			layout.add(userMenu);
		} else {
			Anchor loginLink = new Anchor("login", "Sign in");
			layout.add(loginLink);
		}

		return layout;
	}

	/**
	 * Wird nach der Navigation aufgerufen, um den Titel der aktuellen Seite zu aktualisieren.
	 */
	@Override
	protected void afterNavigation() {
		super.afterNavigation();
		viewTitle.setText(getCurrentPageTitle());
	}

	/**
	 * Ruft den Titel der aktuellen Seite ab.
	 *
	 * @return Der Titel der aktuellen Seite.
	 */
	private String getCurrentPageTitle() {
		PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
		return title == null ? "" : title.value();
	}
}
