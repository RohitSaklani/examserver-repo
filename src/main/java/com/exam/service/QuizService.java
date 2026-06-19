package com.exam.service;


import com.exam.DTO.FilterDTO;
import com.exam.DTO.QuizDTO;
import com.exam.model.*;
import com.exam.repository.QuizRepository;
import com.exam.repository.ResultRepository;
import com.exam.repository.SubjectRepository;
import com.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ResultRepository resultRepository;





    public List<Quiz> findAll() {
        return quizRepository.findByIsEnabledTrue();
    }

    public List<Quiz> findBySubjectId(Long subjectId) {
        return quizRepository.findBySubjectIdAndIsEnabledTrue(subjectId);
    }


    public List<Quiz> findByFilters(FilterDTO filterDTO) {

        List subjectIds = filterDTO.getSubjectIds().isEmpty() ? null : filterDTO.getSubjectIds();

        List levelNames = filterDTO.getLevelNames().isEmpty() ? null : filterDTO.getLevelNames();

        Integer rating = filterDTO.getRating() == 0 ? null : filterDTO.getRating();

        return quizRepository.findByFilters(subjectIds, levelNames, rating);

    }


    public Page<Quiz> getPagedQuiz(int page, int size){

        Pageable pageable = PageRequest.of(page,size);
        return quizRepository.findByIsEnabledTrue(pageable);
    }

    public Quiz addQuiz(QuizDTO quizDTO) {
        Optional <Subject> optionalSubject = subjectRepository.findById(quizDTO.getSubject());

        if(optionalSubject.isEmpty()){
            throw new RuntimeException("Subject not found ");

        }

        Quiz quiz =  new Quiz();

        quiz.setName(quizDTO.getName());
        quiz.setSubject(optionalSubject.get());
        quiz.setQuizType(quizDTO.getQuizType());
        quiz.setLevel(quizDTO.getLevel());
        quiz.setEnabled(quizDTO.isEnabled());
        quiz.setPassingScore(quizDTO.getPassingScore());
        quiz.setRating(quizDTO.getRating());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setDurationInSec(quizDTO.getDurationInSec());
        return quizRepository.save(quiz);





    }

    public Quiz updateQuiz(QuizDTO quizDTO) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizDTO.getId());
        Optional <Subject> optionalSubject = subjectRepository.findById(quizDTO.getSubject());

        if(optionalQuiz.isEmpty()){
            throw new RuntimeException("Quiz not found ");

        }
        if(optionalSubject.isEmpty()){
            throw new RuntimeException("Subject not found ");

        }

        Quiz quiz =  optionalQuiz.get();

        quiz.setName(quizDTO.getName());
        quiz.setSubject(optionalSubject.get());
        quiz.setQuizType(quizDTO.getQuizType());
        quiz.setLevel(quizDTO.getLevel());
        quiz.setEnabled(quizDTO.isEnabled());
        quiz.setPassingScore(quizDTO.getPassingScore());
        quiz.setRating(quizDTO.getRating());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setDurationInSec(quizDTO.getDurationInSec());
        return quizRepository.save(quiz);


    }

    public Quiz disbaleQuiz(Long quizId) {
        Optional<Quiz> optionalFoundQuiz = quizRepository.findById(quizId);
        if (optionalFoundQuiz.isPresent()) {
            Quiz foundQuiz = optionalFoundQuiz.get();
            foundQuiz.setEnabled(false);
            return quizRepository.save(foundQuiz);

        } else {
            throw new RuntimeException("Quiz not found ");
        }
    }

    Quiz DTOtoQuiz(QuizDTO quizDTO){
         Optional <Subject> optionalSubject = subjectRepository.findById(quizDTO.getSubject());

        Quiz quiz =  new Quiz();

        quiz.setId(quizDTO.getId());
        quiz.setName(quizDTO.getName());
        quiz.setSubject(optionalSubject.get());
        quiz.setQuizType(quizDTO.getQuizType());
        quiz.setLevel(quizDTO.getLevel());
        quiz.setEnabled(quizDTO.isEnabled());
        quiz.setPassingScore(quizDTO.getPassingScore());
        quiz.setRating(quizDTO.getRating());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setDurationInSec(quizDTO.getDurationInSec());
        return quiz;
    }

}
