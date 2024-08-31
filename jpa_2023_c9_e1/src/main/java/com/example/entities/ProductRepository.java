package com.example.entities;

import com.example.interfaces.QueryFunction;
import jakarta.persistence.EntityManager;

public class ProductRepository {
    private EntityManager em;

    public ProductRepository(EntityManager em) {
        this.em = em;
    }

    public <T> T executeQuery(QueryFunction<T> function, String aggregateFunction) {
        var q = function.apply(em, aggregateFunction);
        return q.getSingleResult();
    }
}
