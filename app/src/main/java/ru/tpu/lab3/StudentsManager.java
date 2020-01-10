package ru.tpu.lab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentsManager {

    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
        Collections.sort(students);
    }

    public List<Student> removeStudent(int position) {
        students.remove(position);
        return students;
    }
}
