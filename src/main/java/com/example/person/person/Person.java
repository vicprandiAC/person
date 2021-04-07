package com.example.person.person;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Person {

    @Id
    @SequenceGenerator(
            name = "person_sequence",
            sequenceName = "person_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "person_sequence"
    )
    private Long id;
    private String name;
    private Integer age;
    private String career;
    private String hairColor;
    private String email;


    public Person() {
    }

    public Person(String name,
                  Integer age,
                  String career,
                  String hairColor,
                  String email){

        this.name = name;
        this.age = age;
        this.career = career;
        this.hairColor = hairColor;
        this.email = email;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}