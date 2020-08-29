package com.example.application.frontend;

import com.example.application.backend.service.SpecialistService;
import com.example.application.frontend.view.booking.BookingView;
import com.example.application.frontend.view.dashboard.DashboardView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    private final SpecialistService specialistService;


    public MainLayout(SpecialistService specialistService) {
        this.specialistService = specialistService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("SDVisits");
        logo.addClassName("logo");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);

        if (isLoggedIn()) {
            Anchor logout = new Anchor("logout", "Log out");
            header.add(new Icon(VaadinIcon.USER));
            header.add(new Label(specialistService.getCurrentUserFullName()));
            header.add(logout);
        }
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(
             FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");


        addToNavbar(header); 

    }

    private void createDrawer() {
        RouterLink bookTimeLink = new RouterLink("Book Time", BookingView.class);
        RouterLink dashboardLink = new RouterLink("Service Department", DashboardView.class);
        bookTimeLink.setHighlightCondition(HighlightConditions.sameLocation());
        dashboardLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(bookTimeLink));

        if (isLoggedIn()) {
            addToDrawer(new VerticalLayout(dashboardLink));
        }

    }

    private boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }
}