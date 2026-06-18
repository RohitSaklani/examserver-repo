package com.exam.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Entity
@Table(name="Quizzes")
public class Quiz  extends  BasicEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private QuizLevel level;

    private Integer durationInSec;


    @Enumerated(EnumType.STRING)

    private QuizType quizType=QuizType.PUBLIC;



    private Integer rating;

    private Float passingScore;

    private boolean isEnabled;


    @ManyToOne
    @SQLRestriction("is_Enable =true")
    private Subject subject;



    @OneToMany(mappedBy = "quiz",cascade = CascadeType.REMOVE)
    private List<Question> questionsList;


    public QuizLevel getLevel() {
        return level;
    }
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Float getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(Float passingScore) {
        this.passingScore = passingScore;
    }


    @Override
    public String toString() {
        return "Quizs{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", level=" + level +
                ", durationInSec=" + durationInSec +
                ", rating=" + rating +
                ", passingScore=" + passingScore +
                ", questionsList=" + questionsList +
                ", subject=" + subject +
                ", quizType=" + quizType +
                ", id=" + id +
                '}';
    }


    public Quiz() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }


}
