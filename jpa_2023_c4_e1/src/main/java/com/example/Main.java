package com.example;

import com.example.entities.Employee;
import com.example.entities.Product;
import com.example.entities.Student;
import com.example.entities.keys.StudentKey;
import com.example.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        findVsRef();
    }

    private static void runStudent() {
        var emf = getEntityManagerFactory();
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            StudentKey id = new StudentKey();
            id.setCode("ABC");
            id.setNumber(10);

            Student s1 = new Student();
            s1.setId(id);
            s1.setName("Sekiro");
            em.persist(s1);
            em.getTransaction().commit();
        }
    }

    private static void runProduct() {
        var emf = getEntityManagerFactory();
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Product p1 = new Product();
            p1.setCode("abc");
            p1.setNumber(10);
            p1.setCode("Red");
            em.persist(p1);
            em.getTransaction().commit();
        }
    }

    private static void go(String name, String add) {
        EntityManagerFactory emf = getEntityManagerFactory();
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            var e1 = new Employee();
            e1.setName(name);
            e1.setAddress(add);
            em.persist(e1);
            em.getTransaction().commit();
        }
    }

    private static void refresh() {
        EntityManagerFactory emf = getEntityManagerFactory();
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            var e1 = em.getReference(Employee.class, 1);
            System.out.println("Before ! e1 = " + e1);
            e1.setName("Mogh");
            e1.setAddress("Erdtree");
            em.refresh(e1);
            System.out.println("After ! e1 = " + e1);
            em.getTransaction().commit();
        }
    }

    private static void findVsRef() {
        EntityManagerFactory emf = getEntityManagerFactory();

        // EntityManager em = emf.createEntityManager -> this creates a context
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            StudentKey id = new StudentKey();
            id.setCode("ABC");
            id.setNumber(10);

            // find vs getReference
//            var e1 = em.getReference(Employee.class, 1); // lazy , create shell and will sent query when it used
//            var e1 = em.find(Employee.class, 1); // eager approach

            Student s = em.find(Student.class, id);
            System.out.println("s = " + s);
//            em.persist(e2);  // add this to the context  -> NOT AN INSERT QUERY
            em.getTransaction().commit(); // end of transaction
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
