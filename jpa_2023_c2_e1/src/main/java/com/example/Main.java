package com.example;

import com.example.entities.Employee;
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

//            Employee e1 = em.find(Employee.class, 1);
            var e1 = new Employee();
//            em.remove(e1); // remove instance from the context
            e1.setId(1L);
            e1.setName("Radahn");
            e1.setAddress("RedMane");
            em.merge(e1);

            System.out.println(e1.toString());
//            em.persist(e2);  // add this to the context  -> NOT AN INSERT QUERY
            em.getTransaction().commit(); // end of transaction
        }
    }
}
