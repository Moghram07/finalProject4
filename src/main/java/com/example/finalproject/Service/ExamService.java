package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.Exam;
import com.example.finalproject.Model.Tutor;
import com.example.finalproject.Repository.ExamRepository;
import com.example.finalproject.Repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final TutorRepository tutorRepository;

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public void addExam(Exam exam) {
        examRepository.save(exam);
    }

    public void updateExam(Integer id,Exam exam) {
        Exam exam1 = examRepository.findExamById(id);
        if (exam1 == null) {
            throw new ApiException("Exam not found");

        }
        exam1.setName(exam.getName());
        exam1.setScore(exam.getScore());
        exam1.setMaxScore(exam.getMaxScore());
        exam1.setDateTaken(exam.getDateTaken());
        examRepository.save(exam1);
    }

    public void deleteExam(Integer id) {
        Exam exam1=examRepository.findExamById(id);
        if (exam1 == null) {
            throw new ApiException("Exam not found");
        }
        examRepository.delete(exam1);
    }

    //Reema
    //Assign exam to tutor
    public void assignExamToTutor(Integer tutor_id, Integer exam_id) {
        Tutor tutor = tutorRepository.findTutorById(tutor_id);
        if (tutor == null) {
            throw new ApiException("Tutor not found");
        }
        Exam exam= examRepository.findExamById(exam_id);
        if (exam == null) {
            throw new ApiException("Exam not found");
        }
        if(tutor.getExams().contains(exam)){
            throw new ApiException("Tutor already assigned to exam");
        }
        exam.setDateTaken(LocalDate.now());
        tutor.setHasTakenExam(true);

        exam.setTutor(tutor);
        examRepository.save(exam);
    }
}
