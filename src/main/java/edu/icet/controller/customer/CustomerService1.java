package edu.icet.controller.customer;

import edu.icet.model.Customer;
import javafx.collections.ObservableList;

public interface CustomerService1 {
    ObservableList<Customer> getAllCustomer();
    ObservableList<String> getCustomerIds();
    Customer searchCustomer(String id);
}
