package edu.icet.Entity;

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
public class CustomerEntity {
        @Id
        private String id;
        private String title;
        private String name;
        private LocalDate dob;
        private Double salary;
        private String address;
        private String city;
        private String province;
        private String postalcode;
}
