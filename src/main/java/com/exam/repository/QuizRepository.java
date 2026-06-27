package com.exam.repository;

import com.exam.model.QuizType;
import com.exam.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {

    public List<Quiz> findBySubjectIdAndIsEnabledTrue(Long subjectId);

    public List<Quiz>  findByIsEnabledTrue();

    public Page<Quiz> findByIsEnabledTrue(Pageable pageable);


    @Query("""
    SELECT q
    FROM Quiz q
    WHERE (:subjectIds IS NULL OR q.subject.id IN :subjectIds)
    AND (:levels IS NULL OR q.level IN :levels)
    AND (:rating IS NULL OR q.rating = :rating)
    AND isEnabled = true
""")
    List<Quiz> findByFilters(
            @Param("subjectIds") List<String> subjectIds,
            @Param("levels") List<String> levelNames,
            @Param("rating") Integer rating);


}
