package de.arnorichter.simpleaccounting.views.stats;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.arnorichter.simpleaccounting.views.MainLayout;

@PageTitle("Stats")
@Route(value = "stats", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class StatsView extends Composite<VerticalLayout> {

    public StatsView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
    }
}
