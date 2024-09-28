package com.example.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Enrollment {

    @Id
    private Long id;

    private Date enrollmentDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    /*
    A StackOverflowError when overriding the toString() method in an entity class typically happens due to circular references between entities.
    In your case, the Enrollment entity references a Student, which might reference back to Enrollment,
    causing an infinite loop when trying to print the toString() representation of an object.
     */
    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }

}
