package com.exam.model;


import jakarta.persistence.*;

@Entity
@Table(name="options")
public class Option extends  BasicEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String value;

    private boolean isRight;

  @ManyToOne
  private Question question;

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

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }



    public void setQuestion(Question question) {
        this.question = question;
    }
}
