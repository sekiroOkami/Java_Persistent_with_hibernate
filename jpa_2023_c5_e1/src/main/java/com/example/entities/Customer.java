package com.example.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Order> orders;
}
