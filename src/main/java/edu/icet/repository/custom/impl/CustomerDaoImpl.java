package edu.icet.repository.custom.impl;


import edu.icet.model.Customer;
import edu.icet.repository.custom.CustomerDao;
import edu.icet.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean delete(String id) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.delete(new Customer(id,null,null,null,null,null,null,null,null));
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public ObservableList<String> getIds() {
        ObservableList<String> customerIds=FXCollections.observableArrayList();
        ObservableList<Customer> customers;
        customers=getAll();
        for (Customer cust:customers){
            customerIds.add(cust.getCustID());
        }
        return customerIds;
    }

    @Override
    public boolean save(Customer customer) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.persist(customer);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public ObservableList<Customer> getAll() {
        ObservableList<Customer> customers= FXCollections.observableArrayList();
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            Query<Customer> query = session.createQuery("FROM Customer");
            List<Customer> customerList=query.list();
            session.getTransaction().commit();
            session.close();
            for(Customer list:customerList){
                customers.add(list);
            }
            return customers;
        } catch (HibernateException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public boolean update(Customer customer) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.update(customer);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public Customer search(String id) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            Query<Customer> query = session.createQuery("FROM Customer WHERE CustID = :customerId", Customer.class);
            query.setParameter("customerId", id);
            Customer customer = query.uniqueResult();
            session.getTransaction().commit();
            session.close();
            return customer;
        } catch (HibernateException e) {
            throw new RuntimeException(e);

        }
    }
}
