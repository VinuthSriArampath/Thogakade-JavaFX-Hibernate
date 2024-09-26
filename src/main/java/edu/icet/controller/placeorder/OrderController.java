package edu.icet.controller.placeorder;

import edu.icet.controller.item.ItemServiceImpl;
import edu.icet.db.DBConnecttion;
import edu.icet.model.Order;
import javafx.scene.control.Alert;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    OrderDetailsControll orderDetailsControll=new OrderDetailsControll();
    Connection connection = DBConnecttion.getGetInstance().getConnection();

    public boolean placeOrder(Order order) throws SQLException{
        try {
            connection.setAutoCommit(false);
            String sql="INSERT INTO orders VALUES(?,?,?);";
            PreparedStatement prst = connection.prepareStatement(sql);
            prst.setObject(1,order.getOrderid());
            prst.setObject(2,order.getOrderdate());
            prst.setObject(3,order.getCustomerid());
            boolean orderadded = prst.executeUpdate() > 0;
            if (orderadded){
                boolean detailsadded = orderDetailsControll.addOrderDetails(order.getOrderdetails());
                if (detailsadded){
                   Boolean stockupdated= ItemServiceImpl.getInstance().updateStock(order.getOrderdetails());
                   if (stockupdated){
                       new Alert(Alert.AlertType.CONFIRMATION,"OrderPlaced Successfully").show();
                   }
                }
            }
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }

    }
}
