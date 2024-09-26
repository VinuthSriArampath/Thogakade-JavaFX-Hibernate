package edu.icet.repository;


import edu.icet.repository.custom.impl.CustomerDaoImpl;
import edu.icet.repository.custom.impl.ItemDaoImpl;
import edu.icet.repository.custom.impl.OrderDaoImpl;
import edu.icet.util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance() {
        if (instance==null){
            return instance=new DaoFactory();
        }
        return instance;
    }
    public <T extends SuperDao>T getDaoType(DaoType type){
        switch (type){
            case CUSTOMER : return (T) new CustomerDaoImpl();
            case ITEM:return (T) new ItemDaoImpl();
            case ORDER:return (T) new OrderDaoImpl();
            default:return null;
        }
    }
}
