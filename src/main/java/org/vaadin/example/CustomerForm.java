package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;

// To make this class a Vaadin UI component, you need to extend an existing component.
// Extend the FormLayout component as follows:
public class CustomerForm extends FormLayout {
    /*
    The form needs an input field for each editable property in the Customer class.
    Vaadin provides different kinds of fields for editing different kinds of values.
    We use the TextField, ComboBox, and DatePicker components.
    Define the following instance variables in the CustomerForm class:
     */

    @PropertyId("firstName")
    private TextField firstName = new TextField("First name");
    @PropertyId("lastName")
    private TextField lastName = new TextField("Last name");
    @PropertyId("status")
    private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    @PropertyId("birthDate")
    private DatePicker birthDate = new DatePicker("Birthdate");

    /*
    The form also needs two buttons to save and delete Customer instances.
    Add the buttons to the CustomerForm class as follows:
     */
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");


    /**
     * To display specific customer data in the form, you need to connect the properties of a given
     * Customer instance to the input fields in the form. This is known as data binding,
     * and Vaadin provides the Binder helper class for this purpose.
     * To bind the data, add a new property of type Binder to the CustomerForm class and configure it
     * in the constructor as follows:
     */
    private Binder<Customer> binder = new Binder<>(Customer.class);

    /*
    To ensure the save and the delete actions update the list of customers in the MainView class,
    we need to add a reference to this class. Y
    ou can receive this reference in the constructor of the CustomerForm as follows:
     */
    private MainView mainView;

    /*
    The save and the delete actions also need to reference the CustomerService class.
    Add a reference in the CustomerForm class as follows:
     */
    private CustomerService service = CustomerService.getInstance();

    /*
 With the components in place, you can configure and add them to the form.
 A good place to do this is the constructor.
 Add the following constructor to the CustomerForm class:
  */
    public CustomerForm(MainView mainView) {
        this.mainView = mainView;
        binder.bindInstanceFields(this);
        // status.setItems adds all the enum values as options to the ComboBox.
        status.setItems(CustomerStatus.values());
        save.addClickListener(event -> save());
        delete.addClickListener(event -> {
            // Notification.show("DELETE: " + binder.getBean());
            delete();

        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(save, delete);

        // addThemeVariants makes the save button prominent by decorating it with a style name.

        add(firstName, lastName, status, birthDate, buttons);

        // With the service and mainView variables in place, implement the save action as follows:

    }

       private void delete() {
        Customer customer = binder.getBean();
        // Notification.show("Delete  NOW: " + customer, 2000, Notification.Position.MIDDLE);
        // Notification.show("Service  NOW: " + service, 2000, Notification.Position.MIDDLE);
        service.delete(customer);
        mainView.updateList();
        setCustomer(null);
    }

    private void save() {
        // getBean gets the customer instance that was bound to the input fields of the form.
        Customer customer = binder.getBean();
        // service.save(customer) performs the save action in the backend.
         // Notification.show("Save  NOW: " + customer, 2000, Notification.Position.MIDDLE);
         // Notification.show("Service  NOW: " + service, 2000, Notification.Position.MIDDLE);
        service.save(customer);
        // updateList updates the list of customers in the main view.
        mainView.updateList();
        // setCustomer(null) hides the form.
        setCustomer(null);
    }

    /**
     * You can implement the logic to show or hide the form in a single public method.
     * To do so, add the following code to the CustomerForm class:
     *
     * @param customer
     */
    public void setCustomer(Customer customer) {
        binder.setBean(customer);
        if (customer == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }
}
