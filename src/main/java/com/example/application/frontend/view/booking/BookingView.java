package com.example.application.frontend.view.booking;

import com.example.application.backend.entity.Specialist;
import com.example.application.backend.entity.Visit;
import com.example.application.backend.service.SpecialistService;
import com.example.application.backend.service.VisitService;
import com.example.application.frontend.MainLayout;
import com.example.application.frontend.view.reservation.VisitDetailsView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;
import java.util.List;

@Route(value="", layout = MainLayout.class)
@PageTitle("Book Time | SDVisits")
public class BookingView extends VerticalLayout {

    private final VisitService visitService;
    private final SpecialistService specialistService;
    private final ComboBox<Specialist> specialistComboBox = new ComboBox<>();

    public BookingView(VisitService visitService, SpecialistService specialistService) {
        this.visitService = visitService;
        this.specialistService = specialistService;
            configureSpecialistComboBox();
            add(specialistComboBox);
            add(getButton());

    }

    private void configureSpecialistComboBox() {

        specialistComboBox.setLabel("Choose a specialist");
        List<Specialist> specialists = specialistService.findAll();

        specialistComboBox.setItemLabelGenerator(Specialist::toString);
        specialistComboBox.setItems(specialists);
    }

    private Component getButton() {
        Button button = new Button("Book Visit", e -> {
            if (specialistComboBox.isEmpty()) {
                Notification.show("No specialist selected!");
            } else {
                Visit visit = new Visit();
                visit.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
                visit.setSpecialist(specialistComboBox.getValue());

                visit.setCode(RandomStringUtils.randomAlphanumeric(6).toUpperCase());
                visitService.save(visit);
                this.getUI().ifPresent(ui ->
                        ui.navigate(VisitDetailsView.class, visit.getCode()));
            }
        });
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addClassName("centered-content");
        return button;
    }

}