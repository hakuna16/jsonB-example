package com.test.jsonb.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.jsonb.models.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, String> {

    List<Student> findAllByIdIn(List<String> ids);
     
}
