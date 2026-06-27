package com.exam.controller;


import com.exam.DTO.FilterDTO;
import com.exam.DTO.QuizDTO;
import com.exam.model.*;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import com.exam.service.ResultService;
import com.exam.service.SubjectService;
import com.exam.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ResultService resultService;



    @GetMapping("/user/quiz-data")
    public ResponseEntity<ApiResponse> getQuizDataForHomePage(){
        List<Map<String,Object>> subjectwithQuizCount = subjectService.getSubjectsWithQuizCount();
        List<Quiz> trendingQuizs = quizService.findAll();
        System.out.println("trendingQuizs"+trendingQuizs);

        for(Quiz item : trendingQuizs){
            System.out.println("trendingQuizs"+item.toString());

        }
         Map data = new HashMap();
        data.put("subjectWithQUizCount",subjectwithQuizCount);

        data.put("trendingQuizs",trendingQuizs);
        ApiResponse res = new ApiResponse();
        res.setData(data);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);

    }

    @GetMapping("/user/quiz")
    public  ResponseEntity<ApiResponse> getQuizes(){
        List<Quiz> quizsList = quizService.findAll();
        ApiResponse res = new ApiResponse();
        res.setData(quizsList);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }





    @GetMapping("/user/filter")
    public  ResponseEntity<ApiResponse> getFilters(){
        List<Subject> subjectList = subjectService.getAllSubjects();

        Object[] quizLevels =  Stream.of( QuizLevel.values()).map(val->val.name()).toArray();

        Map dataMap = new HashMap();
        dataMap.put("subjectList",subjectList);

        dataMap.put("levelList",quizLevels);

        ApiResponse res = new ApiResponse();
        res.setData(dataMap);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/user/quiz/filter")
    public ResponseEntity<ApiResponse> getQuizsByFilter(@RequestBody FilterDTO filterDTO){

        System.out.println("filterDTO"+filterDTO.toString());
        System.out.println("subjectlsit"+filterDTO.getSubjectIds()
               .toString());

        System.out.println("levelList"+filterDTO.getLevelNames().toString());

        System.out.println("rating"+filterDTO.getRating());

        List<Quiz> quizs = quizService.findByFilters(filterDTO);

        for(Quiz quiz : quizs){
            System.out.println("quiz "+quiz.toString());
        }

        ApiResponse res = new ApiResponse();
        res.setData(quizs);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);

    }






//    @GetMapping("/admin/quiz")
//    public ResponseEntity<ApiResponse> getQuizForAdmin(){
//        List<Quiz> quizList = quizService.findAll();
//        ApiResponse res = new ApiResponse();
//        res.setData(quizList);
//        res.setMessage("success");
//        return  ResponseEntity.status(HttpStatus.OK).body(res);
//    }

    @GetMapping("/admin/quiz")
    public ResponseEntity<ApiResponse> getPagedQuizesForAdmin(@RequestParam (defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size){

        PagedModel<Quiz> quizPage =new PagedModel<>( quizService.getPagedQuiz(page,size));
        ApiResponse res = new ApiResponse();
        res.setData(quizPage);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);

    }

    @PostMapping("/admin/quiz")
    public ResponseEntity<ApiResponse> addQuiz(@RequestBody QuizDTO quizDTO){
        Quiz createdQuiz = quizService.addQuiz(quizDTO);
        ApiResponse res = new ApiResponse();
        res.setData(createdQuiz);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("/admin/quiz")
    public ResponseEntity<ApiResponse> updateQuiz(@RequestBody QuizDTO quizDTO){
        Quiz updatedQuiz = quizService.updateQuiz(quizDTO);
        ApiResponse res = new ApiResponse();
        res.setData(updatedQuiz);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @DeleteMapping("/admin/quiz/{quizId}")
    public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable Long quizId){
        Quiz updatedQuiz = quizService.disbaleQuiz(quizId);
        ApiResponse res = new ApiResponse();
        res.setData(updatedQuiz);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }

    

}
