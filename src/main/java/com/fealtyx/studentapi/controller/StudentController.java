package com.fealtyx.studentapi.controller;

import com.fealtyx.studentapi.model.Student;
import com.fealtyx.studentapi.service.OllamaService;
import com.fealtyx.studentapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    @Autowired
    private OllamaService ollamaService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student s) {
        if (s.getName() == null || s.getEmail() == null || s.getAge() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.create(s));
    }

    @GetMapping
    public List<Student> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @RequestBody Student s) {
        return service.update(id, s)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<String> getSummary(@PathVariable int id) {
        return service.getById(id).map(student -> {
            try {
                String summary = ollamaService.getStudentSummary(student.getName(), student.getAge(), student.getEmail());
                return ResponseEntity.ok(summary);
            } catch (IOException | InterruptedException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"error\": \"Failed to contact Ollama\"}");
            }
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"error\": \"Student not found\"}"));
    }
}