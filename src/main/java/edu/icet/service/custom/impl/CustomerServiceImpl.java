package edu.icet.service.custom.impl;


import edu.icet.model.Customer;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.CustomerDao;
import edu.icet.service.custom.CustomerService;
import edu.icet.util.DaoType;
import javafx.collections.ObservableList;


public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean updateCustomer(Customer customer) {
        return customerDao.update(customer);
    }

    @Override
    public Customer searchCustomer(String id) {
        return customerDao.search(id);
    }

    @Override
    public ObservableList<Customer> getAll() {
        return customerDao.getAll();
    }

    @Override
    public ObservableList<String> getCustomerIds() {
        return customerDao.getCustomerIds();
    }

    CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);

    @Override
    public boolean addCustomer(Customer customer) {
        return  customerDao.save(customer);
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        return customerDao.delete(customer);
    }
}
