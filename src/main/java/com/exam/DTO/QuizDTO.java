package com.exam.DTO;

import com.exam.model.Question;
import com.exam.model.QuizLevel;
import com.exam.model.QuizType;
import com.exam.model.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

public class QuizDTO {
    @NotNull
    private long id;
    @NotNull
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private QuizLevel level;

    @NotNull
    private Integer durationInSec;

    @NotNull
    private Long subject;

    @Enumerated(EnumType.STRING)
    @NotNull
    private QuizType quizType;


    @NotNull
    private Integer rating;

    @NotNull
    private Float passingScore;

    @NotNull
    private boolean isEnabled;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuizLevel getLevel() {
        return level;
    }

    public void setLevel(QuizLevel level) {
        this.level = level;
    }

    public Integer getDurationInSec() {
        return durationInSec;
    }

    public void setDurationInSec(Integer durationInSec) {
        this.durationInSec = durationInSec;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }


    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Float getPassingScore() {
        return passingScore;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setPassingScore(Float passingScore) {
        this.passingScore = passingScore;
    }

}
