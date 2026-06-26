package com.exam.controller;


import com.exam.model.Quiz;
import com.exam.model.Subject;
import com.exam.service.SubjectService;
import com.exam.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;


    @GetMapping("/user/subject")
    public ResponseEntity<ApiResponse> getSubjectsforUser(){
        List<Subject> subjectList = subjectService.getAllSubjects();
        ApiResponse res = new ApiResponse();
        res.setData(subjectList);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/admin/subject-all")
    public ResponseEntity<ApiResponse> getSubjectsForAdmin(@RequestParam (defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size){

        List<Subject> subjectList = subjectService.getAllSubjects();
        ApiResponse res = new ApiResponse();
        res.setData(subjectList);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/admin/subject")
    public ResponseEntity<ApiResponse> getPagedSubjectsForAdmin(@RequestParam (defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size){

        PagedModel<Subject> subjectPage =new PagedModel<>( subjectService.getPagedSubject(page,size));

        ApiResponse res = new ApiResponse();
        res.setData(subjectPage);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/admin/subject")
    public ResponseEntity<ApiResponse> addSubject(@RequestBody Subject subject){
        Subject createdSubject = subjectService.addSubject(subject);
        ApiResponse res = new ApiResponse();
        res.setData(createdSubject);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("/admin/subject")
    public ResponseEntity<ApiResponse> updateSubject(@RequestBody Subject subject){
        Subject updatedSubject = subjectService.updateSubject(subject);
        ApiResponse res = new ApiResponse();
        res.setData(updatedSubject);
        res.setMessage("success");
        return  ResponseEntity.status(HttpStatus.OK).body(res);
    }

//    @DeleteMapping("/admin/subject/{subjectId}")
//    public ResponseEntity<ApiResponse> deleteSubject(@PathVariable Long subjectId){
//        boolean createdSubject = subjectService.deleteSubject(subjectId );
//        ApiResponse res = new ApiResponse();
//        res.setData(null);
//        res.setMessage("success");
//        return  ResponseEntity.status(HttpStatus.OK).body(res);
//    }





}
