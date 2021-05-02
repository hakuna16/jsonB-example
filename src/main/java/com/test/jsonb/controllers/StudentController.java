package com.test.jsonb.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.jsonb.models.InputPojo;
import com.test.jsonb.models.Student;
import com.test.jsonb.services.StudentService;


@RestController
@RequestMapping(value = "/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
       
    
    @PostMapping
    public Student saveStudent(@RequestBody final InputPojo inputPojo) {
        
        return studentService.saveStudent(inputPojo);
    }
    
    @PutMapping
    public Student updateStudent(@RequestBody final InputPojo inputPojo) {
        return studentService.updateStudent(inputPojo);
    }
    
    @GetMapping
    public List<Student> findAllStudents() {
        return studentService.findAllStudents();
    }
    
    @GetMapping("/{id}")
    public Student findStudentById(@PathVariable final String id) {
        return studentService.findStudentById(id);
    }
    
    @PostMapping("/students")
    public List<Student> saveStudents(@RequestBody final List<InputPojo> inputPojo) {
        
        return studentService.saveStudents(inputPojo);
    }
}
