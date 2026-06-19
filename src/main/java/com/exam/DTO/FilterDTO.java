package com.exam.DTO;

import com.exam.model.QuizLevel;

import java.util.List;
import java.util.Map;

public class FilterDTO {

    List<Long> subjectIds;

    List<QuizLevel> levelNames;

    int rating;

    public List<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }

    public List<QuizLevel> getLevelNames() {
        return levelNames;
    }

    public void setLevelNames(List<QuizLevel> levelNames) {
        this.levelNames = levelNames;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
