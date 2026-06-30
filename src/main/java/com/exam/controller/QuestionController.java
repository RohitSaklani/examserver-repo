package com.exam.controller;


import com.exam.DTO.AddQuestionDTO;
import com.exam.DTO.QuestionDTO;
import com.exam.DTO.QuizDTO;
import com.exam.model.Question;
import com.exam.model.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.ResultService;
import com.exam.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ResultService resultService;

    @GetMapping("/user/quiz/{quizId}/questions")
    public ResponseEntity<ApiResponse> getQuestions(@PathVariable Long quizId){
        List<QuestionDTO> questionsList = questionService.getQuestionsWithOptions(quizId);
        ApiResponse res = new ApiResponse();
        res.setData(questionsList);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @PostMapping("/user/quiz/{quizId}")
    public ResponseEntity<ApiResponse> submitQuiz(@PathVariable Long quizId,@RequestBody Map<Long,Long> body){

        Map result =    resultService.getResult(quizId,body);
        ApiResponse res= new ApiResponse();
        res.setData(result);
        res.setMessage("success");
        return ResponseEntity.status(HttpStatus.OK).body(res);

    }






    @GetMapping("/admin/quiz/{quizId}/question")
    public ResponseEntity<ApiResponse> getQuestionsForAdmin(@PathVariable Long quizId){

      List<Question> questionList =  questionService.getQuestionsWithOptionsForAdmin(quizId);
        ApiResponse res = new ApiResponse();
        res.setData(questionList);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);

    }

    @PostMapping("/admin/quiz/{quizId}/question")
    public ResponseEntity<ApiResponse> addQuestion(@PathVariable Long quizId,@RequestBody AddQuestionDTO addQuestionDTO){
        Question createdQuestion = questionService.addQuestionWithOptionByQuizId(quizId,addQuestionDTO);
        ApiResponse res = new ApiResponse();
        res.setData(createdQuestion);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/admin/quiz/{quizId}/question/upload")
    public ResponseEntity<ApiResponse> addQuestionByFile(@PathVariable Long quizId, @RequestParam("file")MultipartFile file){

       questionService.addQuestionByFile(quizId,file);
        ApiResponse res = new ApiResponse();
        res.setData(null);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("/admin/quiz/{quizId}/question")
    public ResponseEntity<ApiResponse> updateQuestion(@PathVariable Long quizId,@RequestBody AddQuestionDTO updateQuestionDTO){
        Question updatedQuestion = questionService.updateQuestionWithOptionByQuizId(quizId,updateQuestionDTO);
        ApiResponse res = new ApiResponse();
        res.setData(updatedQuestion);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @DeleteMapping("/admin/quiz/{quizId}/question/{questionId}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Long quizId,@PathVariable Long questionId){
        questionService.deleteQuestion(questionId);
        ApiResponse res = new ApiResponse();
        res.setData(null);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }




}
