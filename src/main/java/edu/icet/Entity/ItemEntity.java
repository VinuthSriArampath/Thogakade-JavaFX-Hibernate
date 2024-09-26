package edu.icet.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemEntity {
    private String itemcode;
    private String desc;
    private String packsize;
    private Double unitprize;
    private Integer qty;
}
