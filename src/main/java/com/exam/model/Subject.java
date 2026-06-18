package com.exam.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;


@Entity
@Table(name="subjects")
public class Subject extends  BasicEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.REMOVE)
    private List<Quiz> quizList;


    public Subject() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
