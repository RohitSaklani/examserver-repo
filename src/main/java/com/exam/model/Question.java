package com.exam.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="questions")
public class Question extends  BasicEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String value;

    @ManyToOne
    private Quiz quiz;

    @OneToMany(mappedBy = "question",cascade = CascadeType.REMOVE)
    private List<Option> optionList;

    public Question() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }
}
