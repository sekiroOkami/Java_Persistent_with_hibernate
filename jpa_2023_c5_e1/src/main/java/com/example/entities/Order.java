package com.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Order {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    private Long id;
}
