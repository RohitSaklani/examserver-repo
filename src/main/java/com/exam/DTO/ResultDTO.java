package com.exam.DTO;

import com.exam.model.Quiz;

public class ResultDTO {



    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Quiz quiz;

    private float score;


    private int attemptedquestions;

    private int rightQuestions;

    private int totalQuestions;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
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
