package com.example;


import com.example.entities.Comment;
import com.example.entities.Post;
import com.example.interfaces.TransactionalOperation;
import com.example.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        postOperation();
    }

    private static void postOperation() {
        go(em -> {
            Post p = new Post();
            p.setTitle("Sekiro game");
            p.setContent("Post 1");

            Comment c1 = new Comment();
            c1.setContent("Content comment1");
            c1.setPost(p);

            Comment c2 = new Comment();
            c2.setContent("Content comment2");
            p.setComments(List.of(c1, c2));

            em.persist(p);
        });
    }

    private static void go(TransactionalOperation operation) {
        var emf = getEntityManagerFactory();
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            operation.execute(em);
            em.getTransaction().commit();
        }
    }

    private static EntityManagerFactory getEntityManagerFactory() {
        String puName = "pu-name";
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create"); // create, none, update

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);
        return emf;
    }
}
