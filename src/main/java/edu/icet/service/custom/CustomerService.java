package edu.icet.service.custom;


import edu.icet.model.Customer;
import edu.icet.service.SuperService;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer customer);
}
