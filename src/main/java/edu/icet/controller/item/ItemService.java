package edu.icet.controller.item;

import edu.icet.model.Item;
import edu.icet.model.OrderDetail;
import javafx.collections.ObservableList;


import java.util.List;

public interface ItemService {
   Boolean addItem(Item item);
   Boolean deleteItem(String id);
   Boolean updateItem(Item item);
   ObservableList<Item> getAllItems();
   ObservableList<String> getItemCodes();
   Item saerchItem(String id);

   Boolean updateStock(List<OrderDetail> orderdetails);
}
