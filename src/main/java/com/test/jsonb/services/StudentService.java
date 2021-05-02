package com.test.jsonb.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.test.jsonb.models.InputPojo;
import com.test.jsonb.models.Student;
import com.test.jsonb.repo.StudentRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentRepo studentRepo;

    public List<Student> findAllStudents() {
        return studentRepo.findAll();
    }

    public Student findStudentById(final String id) {

        Optional<Student> student = studentRepo.findById(id);
        if (!student.isPresent()) {
            throw new RuntimeException("Student not present");
        }
        return student.get();
    }

    public List<Student> saveStudents(final List<InputPojo> inputPojo) {

        List<String> ids = inputPojo.stream().map(input -> input.getId()).collect(Collectors.toList());

        List<Student> students = studentRepo.findAllByIdIn(ids);

        Map<String, Student> studentMap = students.stream()
                .collect(Collectors.toMap(student -> student.getId(), student -> student));

        List<Student> studentsUpdated = new ArrayList<>();
        inputPojo.forEach(input -> {

            Student student = studentMap.get(input.getId());

            Map<String, Map<String, Object>> exsistingAttr = student.getAttributes();

            if (exsistingAttr.containsKey(input.getClassification())) {
                // update the map with new attribute values
                Map<String, Object> map = exsistingAttr.get(input.getClassification());
                map.put(input.getAttributeName(), input.getAttributeValue());

            } else {
                Map<String, Object> map = new HashMap<>();
                map.put(input.getAttributeName(), input.getAttributeValue());
                exsistingAttr.putIfAbsent(input.getClassification(), map);
            }
            studentsUpdated.add(student);

        });
        return studentRepo.saveAll(studentsUpdated);
    }
}