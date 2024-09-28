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
//            dto(em);
//            rightJoin(em);
//            where(em);
//            join(em);
            innerJoin(em);
        });
    }
    private static void dto(EntityManager em) {
        String jpql = """
                SELECT NEW com.example.dto.EnrolledStudent(s, e) FROM Student s JOIN s.enrollments e
                """;

        TypedQuery<EnrolledStudent> q = em.createQuery(jpql, EnrolledStudent.class);
        List<EnrolledStudent> resultList = q.getResultList();
        resultList.forEach(enrolledStudent -> System.out.println(enrolledStudent.student() + ", " + enrolledStudent.enrollment()));
    }
    private static void rightJoin(EntityManager em) {
        /*
        A 'Left join'(also known as a left outer join) returns all records from the 'left' entity(in this case,
        'Student'), along with the matching record from the 'right' entity (Enrollment).
        - if there is no matching record int the right entity, the result will still include the record from the 'left'
        entity('student'), with 'null' values for the columns from the 'right' entity
         */

        String jpql = """
                SELECT s, e FROM Student s RIGHT JOIN s.enrollments e
                """;

        TypedQuery<Object[]> q = em.createQuery(jpql, Object[].class);
        List<Object[]> resultList = q.getResultList();
        resultList.forEach(objects -> {
            System.out.println(objects[0]+", " + objects[1]);
        });
    }

    private static void leftJoin(EntityManager em) {
        /*
        A 'Left join'(also known as a left outer join) returns all records from the 'left' entity(in this case,
        'Student'), along with the matching recored from the 'right' entity (Enrollment).
        - if there is no matching record int the right entity, the result will still include the record from the 'left'
        entity('student'), with 'null' values for the columns from the 'right' entity
         */

        String jpql = """
                SELECT s, e FROM Student s LEFT JOIN s.enrollments e
                """;

        TypedQuery<Object[]> q = em.createQuery(jpql, Object[].class);
        List<Object[]> resultList = q.getResultList();
        resultList.forEach(objects -> {
            System.out.println(objects[0]+", " + objects[1]);
        });
    }
    private static void where(EntityManager em) {
        String jpql = """
                SELECT s, e FROM Student s, Enrollment e WHERE s.id = e.student.id
                """;

        TypedQuery<Object[]> q = em.createQuery(jpql, Object[].class);
        List<Object[]> resultList = q.getResultList();
        resultList.forEach(objects -> {
            System.out.println(objects[0]+", " + objects[1]);
        });
    }

    private static void join(EntityManager em) {
        //JOIN and INNER JOIN in JPQL are equivalent. Both are used to perform inner joins,
        // which means that they return only those entities that have a match in both the left side (Student) and the right side (Enrollment) of the relationship.
        String jpql = """
                SELECT s, e FROM Student s JOIN s.enrollments e
                """;

        TypedQuery<Object[]> q = em.createQuery(jpql, Object[].class);
        List<Object[]> resultList = q.getResultList();
        resultList.forEach(objects -> {
            System.out.println(objects[0]+", " + objects[1]);
        });
    }

    private static void innerJoin(EntityManager em) {
        // innerJoin: The query only returns pairs of 'Student' and 'Enrollment' where the 'student' has associated
        // 'Enrollment' entries, if a 'Student' does not have any 'Enrollment', that 'Student' will not appear in the
        // result set.

//        String jpql = """
//                SELECT s, e FROM Student s INNER JOIN s.enrollments e
//                """;

//        String jpql = """
//                SELECT s FROM Student s WHERE
//                    (SELECT COUNT(e) FROM Enrollment e WHERE e.student.id = s.id) > 1
//                """;

        /*
        Condition WHERE e.student = s: The subquery counts only those enrollments
        where the student field in the Enrollment entity matches the Student entity s from the main query.
         */
        String jpql = """
                SELECT NEW com.example.dto.CountedEnrollmentForStudent(s, (SELECT count(e) FROM Enrollment e WHERE e.student = s)) FROM Student s
                """;

        TypedQuery<CountedEnrollmentForStudent> q = em.createQuery(jpql, CountedEnrollmentForStudent.class);
        List<CountedEnrollmentForStudent> resultList = q.getResultList();
        resultList.forEach(objects -> {
            System.out.println(objects);
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
        props.put("hibernate.hbm2ddl.auto", "none"); // create, none, update

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);
        return emf;
    }
}
