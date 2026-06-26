package com.exam.repository;

import com.exam.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {

    public Page<Subject> findAll(Pageable pageable);

    @Query(value = "SELECT s.name,count(s.id) AS quizCount FROM " +
            " exam.subjects s inner join exam.quizzes q " +
            "where q.subject_id = s.id group by s.id",nativeQuery = true    )
    public List<Object[]> getSubjectsWithQuizCount();
}
