package edu.icet.controller.item;

import edu.icet.model.Item;
import edu.icet.model.OrderDetail;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemService1Impl implements ItemService1 {
    @Override
    public Boolean updateStock(List<OrderDetail> orderdetails) {
        for (OrderDetail orderDetail:orderdetails){
            Boolean isAdded = updateStock(orderDetail);
            if (!isAdded){
                return false;
            }
        }
        return true;
    }

    public Boolean updateStock(OrderDetail orderdetails) {
        String sql="UPDATE item SET QtyOnHand=QtyOnHand-? WHERE ItemCode=?";
        try {
            return CrudUtil.execute(sql, orderdetails.getQty(), orderdetails.getItemcode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static ItemService1Impl instance;
    private ItemService1Impl(){}

    public static ItemService1Impl getInstance() {
        return instance==null?instance=new ItemService1Impl():instance;
    }

//    public Boolean addItem(Item item){
//        String sql="INSERT INTO item VALUES(?,?,?,?,?)";
//        try {
//            boolean b = CrudUtil.execute(sql,item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand());
//            if (b){
//                return true;
//            }
//            return false;
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR,"Item Adding Failed! due to %s"+e.getMessage()).show();
//            return false;
//        }
//    }

//    public Boolean deleteItem(String id){
//        String sql="DELETE FROM item WHERE ItemCode=?";
//        try {
//            if (CrudUtil.execute(sql,id)){
//                return true;
//            }
//            return false;
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR,String.format("Item ID :- %s Deletion Failed! due to %s",id,e.getMessage()));
//            return false;
//        }
//
//    }

//    @Override
//    public Boolean updateItem(Item item) {
//        String sql="UPDATE item SET Description = ?,PackSize = ?,UnitPrice = ?,QtyOnHand = ? WHERE ItemCode = ? ;";
//        try {
//            if (CrudUtil.execute(sql,item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand(),item.getItemCode())){
//                return true;
//            }
//            return false;
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR,String.format("Item ID :- %s Updating Failed! due to %s",item.getItemCode(),e.getMessage()));
//            return false;
//        }
//    }

//    @Override
//    public ObservableList<Item> getAllItems() {
//        ObservableList<Item> items= FXCollections.observableArrayList();
//        String sql="SELECT * FROM item";
//        try {
//            ResultSet resultset = CrudUtil.execute(sql);
//            while(resultset.next()){
//                Item item=new Item(
//                        resultset.getString(1),
//                        resultset.getString(2),
//                        resultset.getString(3),
//                        resultset.getDouble(4),
//                        resultset.getInt(5)
//                );
//                items.add(item);
//            }
//            return items;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Override
//    public ObservableList<String> getItemCodes() {
//        ObservableList<Item> items=getAllItems();
//        ObservableList<String> itemcode=FXCollections.observableArrayList();
//        items.forEach(item ->{
//            itemcode.add(item.getItemCode());
//        });
//        return itemcode;
//    }

    @Override
    public Item saerchItem(String id) {
        String SQL="SELECT * FROM item WHERE ItemCode=?";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL,id);
            while(resultSet.next()){
                return new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                );

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
