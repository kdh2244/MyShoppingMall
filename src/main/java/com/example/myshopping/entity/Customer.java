package com.example.myshopping.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@NotNull
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private LocalDate createdAt;

    @OneToMany(mappedBy = "customer")
    private List<Order> orderList;

}
