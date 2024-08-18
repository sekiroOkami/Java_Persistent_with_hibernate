package com.example;


import com.example.entities.Group;
import com.example.entities.User;
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
            User u1 = new User();
            u1.setName("Sekiro");

            User u2 = new User();
            u2.setName("Marika");

            Group g1 = new Group();
            g1.setName("Group1");

            Group g2 = new Group();
            g2.setName("Group2");

            g1.setUsers(List.of(u1, u2));
            g2.setUsers(List.of(u2));

            u1.setGroups(List.of(g1));
            u2.setGroups(List.of(g1, g2));

            em.persist(g1);
            em.persist(g2);

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
