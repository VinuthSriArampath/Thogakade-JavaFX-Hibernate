package edu.icet.controller.placeorder;


import edu.icet.model.OrderDetail;
import edu.icet.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailsControll {
    public boolean addOrderDetails(List<OrderDetail> orderDetail){
        for (OrderDetail orderdetails:orderDetail){
            boolean isadded = addOrderDetails(orderdetails);
            if (!isadded){
                return false;
            }
        }
        return true;
    }
    public boolean addOrderDetails(OrderDetail orderDetail){
        String sql="INSERT INTO orderdetail VALUES(?,?,?,?)";
        try {
            return CrudUtil.execute(sql,
                    orderDetail.getOrderid(),
                    orderDetail.getItemcode(),
                    orderDetail.getQty(),
                    orderDetail.getDiscount()
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
