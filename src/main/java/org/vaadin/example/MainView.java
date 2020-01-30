package org.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {
    private CustomerService service = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private TextField filterText = new TextField();
    private Button addCustomerBtn = new Button("Add new customer");

    private CustomerForm form = new CustomerForm(this);

    public MainView() {

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
        grid.setColumns("firstName", "lastName", "status");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.recalculateColumnWidths();
        // grid.setRowsDraggable(true);
        // add(filterText, grid);
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCustomerBtn);
        mainContent.setSizeFull();
        grid.setSizeFull();
        addCustomerBtn.addClickListener(event -> {
            // clear removes a possible previous selection from the Grid.
            grid.asSingleSelect().clear();
            // setCustomer instantiates a new customer object and passes it to the CustomerForm for editing.
            form.setCustomer(new Customer());
        });
        add(toolbar, mainContent);
        setSizeFull();
        updateList();
        form.setCustomer(null);
        grid.asSingleSelect().addValueChangeListener(event -> form.setCustomer(grid.asSingleSelect().getValue()));
    }

    public void updateList() {
        // Notification.show("Numbers to update: " + service.count());
        //grid.deselectAll();
        grid.setItems(service.findAll(filterText.getValue()));
    }
}
