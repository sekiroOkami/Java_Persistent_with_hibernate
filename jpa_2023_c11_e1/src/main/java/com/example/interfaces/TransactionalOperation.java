package com.example.interfaces;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface TransactionalOperation {
    void execute(EntityManager em);
}
