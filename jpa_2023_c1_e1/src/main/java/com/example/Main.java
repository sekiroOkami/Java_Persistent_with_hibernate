package com.example;

import com.example.entities.Product;
import com.example.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), new HashMap<>());

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Product p = new Product();
            p.setId(3L);
            p.setName("Water");

            em.persist(p);  // add this to the context  -> NOT AN INSERT QUERY

            em.getTransaction().commit();
        }
    }
}
