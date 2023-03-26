package de.lukegoll.vaadin.views.home;

import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import de.lukegoll.vaadin.views.MainLayout;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    public HomeView() {
        PdfViewer pdfViewer = new PdfViewer();

        pdfViewer.setSrc("/Users/lukegollenstede/Downloads/Rechnung_3692157276.pdf");
        pdfViewer.setSizeFull();
        add(pdfViewer);
    }

}
