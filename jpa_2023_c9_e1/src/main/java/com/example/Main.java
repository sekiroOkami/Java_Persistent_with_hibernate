package com.example;


import com.example.entities.Product;
import com.example.entities.ProductRepository;
import com.example.interfaces.QueryFunction;
import com.example.interfaces.TransactionalOperation;
import com.example.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        postOperation();
    }

    private static void postOperation() {
        go(em -> {
            // SELECT, UPDATE, DELETE -- there is not INSERT operation in JPQL
            // The only way to insert is by persist operation by using EntityManager

//            String jpql = "SELECT p FROM Product p WHERE p.price > :price AND p.name LIKE :name";
            // SELECT * FROM Product ===> Fetch all the columns from the table Product
            // SELECT p FROM Product p ===> Fetch all the attributes of the Product entity from the current context

//            q.setParameter("price", 3);
//            q.setParameter("name", "%a%");
//            List<Product> resultList = q.getResultList();
//            resultList.forEach(System.out::println);

//            avoid this because stream operation can do at sql Level
//            Stream<Product> resultStream = q.getResultStream();
            //===================================================
//            String jpql = "SELECT AVG(p.price) FROM Product p "; // AVG, SUM, MIN, MAX

//            statistic(em);

//            String jpql = "SELECT p.name, p.price FROM Product p";
//            go1(em);
//            exception(em);

        });
    }

    private static void exception(EntityManager em) {
        String jpql = "SELECT p FROM Product p WHERE p.name LIKE 'Candy'";
        TypedQuery<Product> q = em.createQuery(jpql, Product.class);
        Optional<Product> product;
        try {
             product = Optional.of(q.getSingleResult()) ;// exception
        } catch (NoResultException e) {
            product = Optional.empty();
            System.out.println("No product found!");
        }
        product.ifPresentOrElse(System.out::println, ()-> System.out.println("Product not found!"));
    }

    private static void go1(EntityManager em) {
        String jpql = "SELECT p.name, AVG(p.price) FROM Product p GROUP BY p.name";
        TypedQuery<Object[]> q = em.createQuery(jpql, Object[].class);
        q.getResultList().forEach(objects -> {
            System.out.println(objects[0] + ", " + objects[1]);
        });
    }

    private static void statistic(EntityManager em) {
        ProductRepository p = new ProductRepository(em);
        QueryFunction queryFunction = (entityManager, aggregate) -> em.createQuery(aggregate,
                Object.class);

        String avgJpql = "SELECT AVG(p.price) FROM Product p";
        var avgPrice = p.executeQuery(queryFunction, avgJpql);
        System.out.println("Average Price: " + avgPrice);

        String maxJpql = "SELECT MAX(p.price) FROM Product p";
        var maxPrice = p.executeQuery(queryFunction, maxJpql);
        System.out.println("Max Price: " + maxPrice);

        String minJpql = "SELECT MIN(p.price) FROM Product p";
        var minPrice = p.executeQuery(queryFunction, minJpql);
        System.out.println("Min Price: " + minPrice);

        String sumJpql = "SELECT SUM(p.price) FROM Product p";
        var sumPrice = p.executeQuery(queryFunction, sumJpql);
        System.out.println("Total Sum: " + sumPrice);
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
