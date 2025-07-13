package com.fealtyx.studentapi.service;

import com.fealtyx.studentapi.model.Student;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentService {

    private final Map<Integer, Student> studentMap = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public Student create(Student student) {
        int id = idCounter.getAndIncrement();
        student.setId(id);
        studentMap.put(id, student);
        return student;
    }

    public List<Student> getAll() {
        return new ArrayList<>(studentMap.values());
    }

    public Optional<Student> getById(int id) {
        return Optional.ofNullable(studentMap.get(id));
    }

    public Optional<Student> update(int id, Student student) {
        if (!studentMap.containsKey(id)) return Optional.empty();
        student.setId(id);
        studentMap.put(id, student);
        return Optional.of(student);
    }

    public boolean delete(int id) {
        return studentMap.remove(id) != null;
    }
}