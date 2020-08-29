package com.example.application.frontend.view.reservation;

import com.example.application.backend.entity.Visit;
import com.example.application.backend.service.VisitService;
import com.example.application.frontend.MainLayout;
import com.example.application.frontend.view.booking.BookingView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Date;

@Route(value = "reservation", layout = MainLayout.class)
@PageTitle("Reservation | SDVisits")
public class VisitDetailsView extends VerticalLayout implements HasUrlParameter<String> {

    private final VisitService visitService;
    private String code;
    private Span details;

    private Visit visit;

    public VisitDetailsView(VisitService visitService) {
        this.visitService = visitService;
        addClassName("visitdetails-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             String parameter) {
        code = parameter;
        visit = visitService.findByCode(code);
        details = new Span();
        if (visit == null) {
            details.setText("Reservation cannot be found with booking code " + code.toUpperCase());
        } else {
            add(getVisitDetails());
            add(getRemainingTime());
            add(getButton());
        }

    }

    private Component getVisitDetails() {
        details.setText("Reservation " + code + ", " + visit.getSpecialist().toString());
        details.addClassName("visit-details");
        return details;
    }

    private Component getRemainingTime() {
        String timeText;
        Date expectedTime = visitService.getExpectedAcceptTime(visit);
        Date now = new Date();
        long diff = expectedTime.getTime() - now.getTime();
        if (diff > 0) {
            timeText = "Approximate waiting time: ";
            long minutes = diff / (60 * 1000) % 60;
            long hours = diff / (60 * 60 * 1000);

            if (hours > 0) {
                timeText += hours + "h ";
            }
            if (minutes > 0) {
                timeText += minutes + "m";
            }
        }
        else {
            timeText = "Please approach your specialist immediately.";
        }
        Span time = new Span();
        time.setText(timeText);
        time.addClassName("visit-details");
        return time;
    }

    private Component getButton() {
        Button button = new Button("Cancel Visit", e -> {
            visit.setCanceledDate(new Date());
            visitService.save(visit);
            this.getUI().ifPresent(ui ->
                    ui.navigate(BookingView.class));
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addClassName("centered-content");
        return button;
    }


}