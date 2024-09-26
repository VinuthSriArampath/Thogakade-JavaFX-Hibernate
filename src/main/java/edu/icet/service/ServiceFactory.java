package edu.icet.service;

import edu.icet.service.custom.impl.CustomerServiceImpl;
import edu.icet.service.custom.impl.ItemServiceImpl;
import edu.icet.service.custom.impl.OrderServiceImpl;
import edu.icet.util.ServiceType;

public class ServiceFactory {
    public static  ServiceFactory instance;

    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        if (instance==null){
            return instance=new ServiceFactory();
        }
        return instance;
    }

    public <T extends SuperService>T getService(ServiceType type){
        switch (type){
            case CUSTOMER: return (T) new CustomerServiceImpl();
            case ITEM:return (T) new ItemServiceImpl();
            case ORDER:return (T) new OrderServiceImpl();
            default:return null;
        }
    }
}
