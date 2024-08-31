package com.example.interfaces;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@FunctionalInterface
public interface QueryFunction<T> {
    public TypedQuery<T> apply(EntityManager em, String function);
}
