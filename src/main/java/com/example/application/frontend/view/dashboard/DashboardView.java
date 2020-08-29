package com.example.application.frontend.view.dashboard;

import com.example.application.backend.entity.Visit;
import com.example.application.backend.service.VisitService;
import com.example.application.frontend.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Scope("prototype")
@Route(value="dashboard", layout = MainLayout.class)
@PageTitle("Service Department")
public class DashboardView extends VerticalLayout {

    final VisitService visitService;

    final Grid<Visit> grid = new Grid<>(Visit.class);
    List<Visit> visitList;


    public DashboardView(VisitService visitService) {
        this.visitService = visitService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        getVisitData();
        add(grid);
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("id", "code", "createdDate");
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(new ComponentRenderer<>(visit -> {
            if (visit.getId().equals(visitList.get(0).getId())) {
                HorizontalLayout buttons = new HorizontalLayout();
                if (visit.getAcceptedDate() == null) {
                    buttons.add(getAcceptButton(visit));
                } else {
                    buttons.add(getCompleteButton(visit));
                }
                buttons.add(getCancelButton(visit));
                return buttons;
            } else {
                return new Label("");
            }
        })).setHeader("Actions");

    }

    private void getVisitData() {
        visitList = visitService.findCurrentUserNext6Visits();
        grid.setItems(visitList);
    }


    private Button getAcceptButton(Visit visit) {
        Button button = new Button("Accept", new Icon(VaadinIcon.CHECK), e -> {
            visit.setAcceptedDate(new Date());
            visitService.save(visit);
            getVisitData();
        });
        button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        addClassName("centered-content");
        return button;
    }

    private Button getCancelButton(Visit visit) {
        Button button = new Button("Cancel", new Icon(VaadinIcon.CLOSE_SMALL), e -> {
            visit.setCanceledDate(new Date());
            visitService.save(visit);
            getVisitData();
        });
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        addClassName("centered-content");
        return button;
    }

    private Button getCompleteButton(Visit visit) {
        Button button = new Button("Complete", new Icon(VaadinIcon.CHECK_CIRCLE), e -> {
            visit.setCompletedDate(new Date());
            visitService.save(visit);
            getVisitData();
        });
        button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        addClassName("centered-content");
        return button;
    }
}

