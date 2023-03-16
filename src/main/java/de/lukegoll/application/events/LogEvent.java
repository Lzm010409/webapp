package de.lukegoll.application.events;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.lukegoll.vaadin.views.auftragsanlage.AuftragsanlageView;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


public class LogEvent {

    private UI ui;
    private AuftragsanlageView auftragsanlageView;

    public LogEvent(UI ui, AuftragsanlageView auftragsanlageView) {
        this.ui = auftragsanlageView.getUI().orElseThrow();
        this.auftragsanlageView = auftragsanlageView;
    }

    @Scheduled(fixedDelay = 5000)
    public void fireEvent() {
        ComponentUtil.fireEvent(ui, new AttachEvent(ui, false));
    }
}
