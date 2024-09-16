package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Model.Exam;
import com.example.finalproject.Service.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exam")
public class ExamController {

    private final ExamService examService;

    @GetMapping("/get")
    public ResponseEntity getAllExams() {
        return ResponseEntity.status(200).body(examService.getAllExams());
    }

    @PostMapping("/add")
    public ResponseEntity addExam(@Valid @RequestBody Exam exam) {
        examService.addExam(exam);
        return ResponseEntity.status(200).body("Exam added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateExam(@PathVariable Integer id,@Valid @RequestBody Exam exam) {
        examService.updateExam(id, exam);
        return ResponseEntity.status(200).body("Exam updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteExam(@PathVariable Integer id) {
        examService.deleteExam(id);
        return ResponseEntity.status(200).body("Exam deleted");
    }

    @PutMapping("/assignExamToTutor/{exam_id}/{tutor_id}")
    public ResponseEntity assignExamToTutor(@PathVariable Integer exam_id,@PathVariable Integer tutor_id) {
        examService.assignExamToTutor(tutor_id,exam_id);
        return ResponseEntity.status(200).body(new ApiResponse("Exam assigned to tutor successfully"));
    }
}
