package de.lukegoll.application.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LogEvent extends ComponentEvent<VerticalLayout> {
    public LogEvent(VerticalLayout verticalLayout, boolean client) {
        super(verticalLayout, client);
    }
}
