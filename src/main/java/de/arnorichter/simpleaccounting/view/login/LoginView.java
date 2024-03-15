package de.arnorichter.simpleaccounting.view.login;

import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.arnorichter.simpleaccounting.security.AuthenticatedUser;

/**
 * Die Klasse LoginView stellt die Benutzeroberfläche für die Anmeldung dar.
 */
@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

	private final AuthenticatedUser authenticatedUser;

	/**
	 * Konstruktor für die LoginView.
	 *
	 * @param authenticatedUser Das AuthenticatedUser-Objekt.
	 */
	public LoginView(AuthenticatedUser authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
		setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

		LoginI18n i18n = LoginI18n.createDefault();
		i18n.setHeader(new LoginI18n.Header());
		i18n.getHeader().setTitle("Accounting");
		i18n.getHeader().setDescription("Login using user/user or admin/admin");
		i18n.setAdditionalInformation(null);
		setI18n(i18n);

		setForgotPasswordButtonVisible(false);
		setOpened(true);
	}

	/**
	 * Diese Methode wird aufgerufen, bevor die Ansicht betreten wird.
	 *
	 * @param event Das BeforeEnterEvent.
	 */
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (authenticatedUser.get().isPresent()) {
			// Bereits angemeldet
			setOpened(false);
			event.forwardTo("");
		}

		setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
	}
}
