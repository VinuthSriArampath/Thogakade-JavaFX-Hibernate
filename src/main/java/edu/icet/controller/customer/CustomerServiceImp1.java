package edu.icet.controller.customer;

import edu.icet.model.Customer;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerServiceImp1 implements CustomerService1 {
    @Override
    public Customer searchCustomer(String id) {
        String SQL="SELECT * FROM customer WHERE CustID=?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL,id);
            while (resultSet.next()){
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static CustomerServiceImp1 instance;
    private CustomerServiceImp1(){}

    public static CustomerServiceImp1 getInstance() {
        return instance==null?instance=new CustomerServiceImp1():instance;
    }

    @Override
    public ObservableList<Customer> getAllCustomer() {
        String sql="SELECT * FROM customer";
        try {
            ResultSet resultset = CrudUtil.execute(sql);
            ObservableList<Customer> Allcustomers=FXCollections.observableArrayList();
            while (resultset.next()) {
                Customer customer = new Customer(
                        resultset.getString(1),
                        resultset.getString(2),
                        resultset.getString(3),
                        resultset.getDate(4).toLocalDate(),
                        resultset.getDouble(5),
                        resultset.getString(6),
                        resultset.getString(7),
                        resultset.getString(8),
                        resultset.getString(9)
                );
                Allcustomers.add(customer);
            }
            return Allcustomers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> getCustomerIds(){
        ObservableList<String> customerid= FXCollections.observableArrayList();
        ObservableList<Customer> customers=getAllCustomer();
        customers.forEach(customer -> {
            customerid.add(customer.getId());
        });
        return customerid;
    }









}


