package com.exam.service;


import com.exam.model.Quiz;
import com.exam.model.Subject;
import com.exam.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Page<Subject> getPagedSubject(int page, int size){

        Pageable pageable = PageRequest.of(page,size);
        return subjectRepository.findAll(pageable);
    }

    public List<Map<String, Object>> getSubjectsWithQuizCount() {
        return subjectRepository.getSubjectsWithQuizCount().stream()
                .map((row) -> {
                            return Map.of("name", row[0], "quizCount", row[1]);
                        }
                ).toList();
    }

    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject updateSubject(Subject subject) {
        Optional<Subject> optionalFoundSubject = subjectRepository.findById(subject.getId());
        Subject updatedSubject = null;
        if (optionalFoundSubject.isPresent()) {
            Subject foundSubject = optionalFoundSubject.get();
            foundSubject.setName(subject.getName());
            return updatedSubject = subjectRepository.save(foundSubject);

        } else {
            throw new RuntimeException("Subject not found");
        }

    }

//    public boolean deleteSubject(Long id ){
//        Optional<Subject> optionalFoundSubject = subjectRepository.findById(id);
//        Subject updatedSubject=null;
//        if(optionalFoundSubject.isPresent()){
//            Subject foundSubject = optionalFoundSubject.get();
//            subjectRepository.delete(foundSubject);
//
//        }else{
//            throw new RuntimeException("Subject not found");
//        }
//        return true;
//    }


}
