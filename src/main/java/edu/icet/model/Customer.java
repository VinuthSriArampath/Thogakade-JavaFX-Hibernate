package edu.icet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Customer {
    @Id
    private String CustID;
    private String CustTitle;
    private String CustName;
    private LocalDate DOB;
    private Double salary;
    private String CustAddress;
    private String City;
    private String Province;
    private String PostalCode;
}
