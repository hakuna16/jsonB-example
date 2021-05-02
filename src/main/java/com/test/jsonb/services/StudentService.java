package com.test.jsonb.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
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

    public Student saveStudent(final InputPojo inputPojo) {

        Student student = new Student();
        student.setAge(inputPojo.getAge());
        student.setName(inputPojo.getName());
        student.setId(inputPojo.getId());

        Map<String, Map<String, ?>> singleOne = new HashMap<>();
        Map<String, Object> classificationValue = new HashMap<>();
        classificationValue.put(inputPojo.getAttributeName(), inputPojo.getAttributeValue());
        singleOne.put(inputPojo.getClassification(), classificationValue);
        student.setAttributes(Collections.singletonList(singleOne));

        return studentRepo.save(student);
    }

    public Student updateStudent(final InputPojo inputPojo) {

        Optional<Student> student = studentRepo.findById(inputPojo.getId());
        List<Map<String, Map<String, ?>>> attributes = student.get().getAttributes();

        Map<String, Map<String, ?>> attribute = new HashMap<>();
        Map<String, Object> classificationValue = new HashMap<>();
        classificationValue.put(inputPojo.getAttributeName(), inputPojo.getAttributeValue());
        attribute.put(inputPojo.getClassification(), classificationValue);
        attributes.add(attribute);
        student.get().setAttributes(attributes);
        return studentRepo.save(student.get());
    }

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

    public List<Student> saveStudents(List<InputPojo> inputPojo) {

        List<String> ids = inputPojo.stream().map(input -> input.getId()).collect(Collectors.toList());

        List<Student> students = studentRepo.findAllByIdIn(ids);

        Map<String, Student> studentMap = students.stream()
                .collect(Collectors.toMap(student -> student.getId(), student -> student));

        List<Student> studentsUpdated = new ArrayList<>();
        inputPojo.forEach(input->{
            
            Student student = studentMap.get(input.getId());
            
            Map<String, Map<String, ?>> attribute = new HashMap<>();
            Map<String, Object> classificationValue = new HashMap<>();
            
            classificationValue.put(input.getAttributeName(), input.getAttributeValue());
            attribute.put(input.getClassification(), classificationValue);
            List<Map<String, Map<String, ?>>> attributes = student.getAttributes();
            attributes.add(attribute);
            student.setAttributes(attributes);
            studentsUpdated.add(student);
        });
        return studentRepo.saveAll(studentsUpdated);
    }
}