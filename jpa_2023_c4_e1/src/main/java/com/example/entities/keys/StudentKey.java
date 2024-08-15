package com.example.entities.keys;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class StudentKey implements Serializable {
    private long number;
    private String code;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
