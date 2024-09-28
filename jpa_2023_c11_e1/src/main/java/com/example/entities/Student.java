package com.example.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@NamedQueries(
    @NamedQuery(
            name = "getAll",
            query = """
                    SELECT s FROM Student s, Enrollment WHERE s.id = e.student.id
                    """
    )
)
public class Student {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name +'}';
    }
}
