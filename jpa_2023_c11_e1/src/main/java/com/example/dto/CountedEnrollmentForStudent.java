package com.example.dto;

import com.example.entities.Student;

public record CountedEnrollmentForStudent(
        String name,
        Long count
) {
}
