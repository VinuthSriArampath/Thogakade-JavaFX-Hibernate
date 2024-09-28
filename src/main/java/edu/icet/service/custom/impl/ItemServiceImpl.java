package edu.icet.service.custom.impl;

import edu.icet.model.Item;
import edu.icet.model.OrderDetail;

import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.ItemDao;
import edu.icet.service.custom.ItemService;
import edu.icet.util.DaoType;
import javafx.collections.ObservableList;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    ItemDao itemDao=DaoFactory.getInstance().getDaoType(DaoType.ITEM);
    @Override
    public Boolean addItem(Item item) {
        return itemDao.save(item);
    }

    @Override
    public Boolean deleteItem(String id) {
        return itemDao.delete(id);
    }

    @Override
    public Boolean updateItem(Item item) {
        return itemDao.update(item);
    }

    @Override
    public ObservableList<Item> getAllItems() {
        return itemDao.getAll();
    }

    @Override
    public ObservableList<String> getItemCodes() {
        return itemDao.getIds();
    }


    @Override
    public Boolean updateStock(List<OrderDetail> orderdetails) {
        return null;
    }
    @Override
    public Item saerchItem(String id) {
        return itemDao.search(id);
    }
}
