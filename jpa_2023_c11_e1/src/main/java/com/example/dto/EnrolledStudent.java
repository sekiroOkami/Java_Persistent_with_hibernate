package com.example.dto;

import com.example.entities.Enrollment;
import com.example.entities.Student;

public record EnrolledStudent(
        Student student,
        Enrollment enrollment
) {
}
