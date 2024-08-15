package com.example;


import com.example.entities.Passport;
import com.example.entities.Person;
import com.example.interfaces.TransactionalOperation;
import com.example.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        personOperation();
    }

    private static void personOperation() {
        go(em -> {
            Person person = new Person();
            person.setName("Sekiro");
            Passport passport = new Passport();
            passport.setNumber("ABC123");
            person.setPassport(passport);
            passport.setPerson(person);

            em.persist(person);
//            em.persist(passport); // person has cascade.persist
//            TypedQuery<Person> q = em.createQuery("SELECT p FROM Person WHERE p.passport.number = :number",
//                    Person.class);
//            q.setParameter("number", "ABC123");
//            System.out.println(q.getResultList());
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
