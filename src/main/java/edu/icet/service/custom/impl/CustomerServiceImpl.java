package edu.icet.service.custom.impl;


import edu.icet.Entity.CustomerEntity;
import edu.icet.model.Customer;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.CustomerDao;
import edu.icet.service.custom.CustomerService;
import edu.icet.util.DaoType;
import org.modelmapper.ModelMapper;


public class CustomerServiceImpl implements CustomerService {

    @Override
    public boolean addCustomer(Customer customer) {
        CustomerEntity entity=new ModelMapper().map(customer,CustomerEntity.class);
        CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        return  customerDao.save(entity);
    }
}
