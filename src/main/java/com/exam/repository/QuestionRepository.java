package com.exam.repository;


import com.exam.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findByQuizId(Long quizId);

    @Query(value = "SELECT * FROM exam.Questions q \n" +
            "join exam.Options o \n" +
            "on q.id = o.question_id\n" +
            "where o.id = :optionId\n" +
            " and q.id = :questionId\n" +
            "and o.is_right = true ", nativeQuery = true     )
    Object verifyAnswer(@Param("questionId") Long questionId,@Param("optionId") Long optionId);

}
