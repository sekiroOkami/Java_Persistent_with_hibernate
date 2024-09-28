package com.example;



import com.example.dto.CountedEnrollmentForStudent;
import com.example.dto.EnrolledStudent;
import com.example.entities.Student;
import com.example.interfaces.TransactionalOperation;
import com.example.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
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
            dto(em);
        });
    }

    private static void dto(EntityManager em) {
        String jpql = """
                SELECT NEW com.example.dto.CountedEnrollmentForStudent(s.name, count(s)) 
                FROM Student s
                GROUP BY s.name
                HAVING s.name LIKE '%a'
                ORDER BY s.name DESC
                """;
        TypedQuery<CountedEnrollmentForStudent> q = em.createQuery(jpql, CountedEnrollmentForStudent.class);
        q.getResultList().forEach(element-> System.out.println(element.name() + ", " + element.count()));
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
        props.put("hibernate.hbm2ddl.auto", "none"); // create, none, update

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);
        return emf;
    }
}
