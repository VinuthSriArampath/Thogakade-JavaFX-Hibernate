package edu.icet.repository.custom;


import edu.icet.model.Customer;
import edu.icet.repository.CrudDao;
import javafx.collections.ObservableList;

public interface CustomerDao extends CrudDao<Customer> {
    ObservableList<String> getCustomerIds();
}
