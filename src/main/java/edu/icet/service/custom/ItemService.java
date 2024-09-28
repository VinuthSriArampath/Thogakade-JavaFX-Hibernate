package edu.icet.service.custom;


import edu.icet.model.Item;
import edu.icet.model.OrderDetail;
import edu.icet.service.SuperService;
import javafx.collections.ObservableList;

import java.util.List;

public interface ItemService extends SuperService {
    Boolean addItem(Item item);
    Boolean deleteItem(String id);
    Boolean updateItem(Item item);
    Boolean updateStock(List<OrderDetail> orderdetails);

    ObservableList<Item> getAllItems();
    ObservableList<String> getItemCodes();

    Item saerchItem(String id);

}
