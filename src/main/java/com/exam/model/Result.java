package com.exam.model;


import jakarta.persistence.*;

@Entity

@Table(name="results")
public class Result extends  BasicEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private Users user;

    private int attemptedquestions;

    private int rightQuestions;

    private int totalQuestions;



    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getAttemptedquestions() {
        return attemptedquestions;
    }

    public void setAttemptedquestions(int attemptedquestions) {
        this.attemptedquestions = attemptedquestions;
    }

    public int getRightQuestions() {
        return rightQuestions;
    }

    public void setRightQuestions(int rightQuestions) {
        this.rightQuestions = rightQuestions;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
