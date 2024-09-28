package edu.icet.service.custom;


import edu.icet.model.Customer;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer customer);
    boolean deleteCustomer(String id);
    boolean updateCustomer(Customer customer);
    ObservableList<Customer> getAll();
    ObservableList<String> getCustomerIds();
    Customer searchCustomer(String id);

}
