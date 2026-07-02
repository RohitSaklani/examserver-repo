package com.exam.service;


import com.exam.DTO.ResultDTO;
import com.exam.model.Quiz;
import com.exam.model.Result;
import com.exam.model.Users;
import com.exam.repository.QuestionRepository;
import com.exam.repository.QuizRepository;
import com.exam.repository.ResultRepository;
import com.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResultService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    QuizRepository quizRepository;

    public List<ResultDTO> findResultByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        System.out.println(" authentication.isAuthenticated() : " + authentication.isAuthenticated() + " principal instanceof UserDetails " + principal.toString());


        if (authentication.isAuthenticated() && principal instanceof UserDetails) {
            String userName;
            userName = ((UserDetails) principal).getUsername();
            Optional<Users> user =   userRepository.findByUsername(userName);
            if(user.isPresent()){

                return mapToResultDto( resultRepository.findByUserId(user.get().getId()));

            }else{
                throw new UsernameNotFoundException("username not found");
            }

        }

        return null;
    }


    public Map getResult(Long quizId, Map<Long, Long> body) {

        int rightCount = 0;
        int attemptedCount = 0;
        Map<String, Object> map = new HashMap<>();

        Set<Long> keys = body.keySet();

        for (Long key : keys) {
            if (body.get(key) != null) {
                attemptedCount++;
            }

            Object result = questionRepository.verifyAnswer(key, body.get(key));
            if (result != null) {
                rightCount++;
            }

        }

        int scorePercentage = rightCount * 100 / body.size();

        map.put("rightCount", rightCount);
        map.put("attemptedCount", attemptedCount);
        map.put("scorePercentage", scorePercentage);
        map.put("totalQuestion", body.size());
        map.put("passingScorePercentage", "40%");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        System.out.println(" authentication.isAuthenticated() : " + authentication.isAuthenticated() + " principal instanceof UserDetails " + principal.toString());


        if (authentication.isAuthenticated() && principal instanceof UserDetails) {
            String userName;


            userName = ((UserDetails) principal).getUsername();


            System.out.println("founded username inside userDetail instance of " + userName);


            Optional<Quiz> quiz = quizRepository.findById(quizId);
            Optional<Users> user = userRepository.findByUsername(userName);

            if (!quiz.isPresent() || !user.isPresent()) {
                throw new RuntimeException("quiz / user not found");

            }
            Result result = new Result();
            result.setQuiz(quiz.get());
            result.setTotalQuestions(body.size());
            result.setAttemptedquestions(attemptedCount);
            result.setRightQuestions(rightCount);
            result.setUser(user.get());

            saveResult(result);
        }

        return map;

    }

    public void saveResult(Result result) {
        resultRepository.save(result);
    }



    private List<ResultDTO> mapToResultDto(List<Result> results ){
        List<ResultDTO> resultDTOList =  new ArrayList<>();
        for(Result result:results){
            ResultDTO resultDTO =  new ResultDTO();

            resultDTO.setId(result.getId());
            resultDTO.setQuiz(result.getQuiz());
            resultDTO.setAttemptedquestions(result.getAttemptedquestions());
            resultDTO.setRightQuestions(result.getAttemptedquestions());
            resultDTO.setTotalQuestions(result.getTotalQuestions());
            resultDTO.setScore( (float) (result.getRightQuestions() * 100) /result.getTotalQuestions());
            resultDTOList.add(resultDTO);

        }
        return resultDTOList;
    }
}
