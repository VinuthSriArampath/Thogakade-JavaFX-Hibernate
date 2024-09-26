package edu.icet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cart {
    private String itemcode;
    private String desc;
    private Integer qty;
    private Double unitprize;
    private Double total;
}
