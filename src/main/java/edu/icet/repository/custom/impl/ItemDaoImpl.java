package edu.icet.repository.custom.impl;

import edu.icet.model.Item;
import edu.icet.repository.custom.ItemDao;
import edu.icet.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ItemDaoImpl  implements ItemDao {
    @Override
    public boolean save(Item item) {
        try {
            Session session=HibernateUtil.getItemSession();
            session.beginTransaction();
            session.persist(item);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            Session session=HibernateUtil.getItemSession();
            session.beginTransaction();
            session.delete(new Item(id,null,null,null,null));
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public ObservableList<Item> getAll() {
        ObservableList<Item> items= FXCollections.observableArrayList();
        try {
            Session session = HibernateUtil.getItemSession();
            session.beginTransaction();
            Query<Item> query = session.createQuery("FROM Item");
            List<Item> itemList =query.list();
            session.getTransaction().commit();
            session.close();
            for(Item list: itemList){
                items.add(list);
            }
            return items;
        } catch (HibernateException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public ObservableList<String> getIds() {
        ObservableList<String> itemcodes =FXCollections.observableArrayList();
        ObservableList<Item> items;
        items=getAll();
        for (Item item:items){
            itemcodes.add(item.getItemCode());
        }
        return itemcodes;
    }

    @Override
    public boolean update(Item item) {
        try {
            Session session = HibernateUtil.getItemSession();
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public Item search(String id) {
        try {
            Session session = HibernateUtil.getItemSession();
            session.beginTransaction();
            Query<Item> query = session.createQuery("FROM Item WHERE ItemCode = :itemCode", Item.class);
            query.setParameter("itemCode", id);
            Item item = query.uniqueResult();
            session.getTransaction().commit();
            session.close();
            return item;
        } catch (HibernateException e) {
            throw new RuntimeException(e);

        }
    }
}
