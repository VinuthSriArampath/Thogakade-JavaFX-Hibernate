package edu.icet.Entity;

import edu.icet.model.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEntity {
    private String orderid;
    private LocalDate orderdate;
    private String customerid;
    private List<OrderDetail> orderdetails;
}
