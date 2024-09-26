package edu.icet.repository.custom.impl;


import edu.icet.Entity.CustomerEntity;
import edu.icet.repository.custom.CustomerDao;
import edu.icet.util.HibernateUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;


public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity customer) {
        System.out.println("Reapository Layer" + customer);
        String SQL="INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(customer);
        session.getTransaction().commit();
        session.close();
        return false;
    }

    @Override
    public boolean delete(CustomerEntity customer) {
        return false;
    }

    @Override
    public ObservableList<CustomerEntity> getAll() {
        return null;
    }

    @Override
    public boolean update(CustomerEntity customer) {
        return false;
    }

    @Override
    public CustomerEntity search(String id) {
        return null;
    }
}
