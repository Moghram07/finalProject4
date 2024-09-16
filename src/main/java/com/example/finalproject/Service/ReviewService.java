package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.Course;
import com.example.finalproject.Model.Review;

import com.example.finalproject.Model.Student;
import com.example.finalproject.Model.Tutor;
import com.example.finalproject.Repository.CourseRepository;
import com.example.finalproject.Repository.ReviewRepository;

import com.example.finalproject.Repository.StudentRepository;
import com.example.finalproject.Repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TutorRepository tutorRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void addReview(Review review) {
        review.setDateCreated(LocalDate.now()); //edit
        reviewRepository.save(review);
    }

    public void updateReview(Integer id,Review review) {
        Review review1=reviewRepository.findReviewById(id);
        if(review1==null){
            throw new ApiException("Review not found");
        }
        review1.setComment(review.getComment());
        review1.setRating(review.getRating());
        review1.setDateCreated(LocalDate.now());
        reviewRepository.save(review1);
    }

    public void deleteReview(Integer id) {
        Review review1=reviewRepository.findReviewById(id);
        if(review1==null){
            throw new ApiException("Review not found");
        }
        reviewRepository.delete(review1);
    }

    //Reema
    public void assignReviewToTutor(Integer review_id, Integer tutor_id) {
        Review review1=reviewRepository.findReviewById(review_id);
        Tutor tutor1=tutorRepository.findTutorById(tutor_id);
        if(review1==null){
            throw new ApiException("Review not found");
        }
        if(tutor1==null){
            throw new ApiException("Tutor not found");
        }
        review1.setTutor(tutor1);
        review1.setDateCreated(LocalDate.now());
        reviewRepository.save(review1);
    }

    //Reema
    public void assignReviewToCourse(Integer student_id,Integer review_id, Integer course_id) {
        Student student1=studentRepository.findStudentById(student_id);
        if(student1==null){
            throw new ApiException("Student not found");
        }
        if(!student1.isEnrolled()){
            throw new ApiException("Student is not enrolled in this course ");
        }
        Review review1=reviewRepository.findReviewById(review_id);
        Course course1=courseRepository.findCourseById(course_id);
        if(review1==null){
            throw new ApiException("Review not found");
        }
        if(course1==null){
            throw new ApiException("Course not found");
        }
        review1.setCourse(course1);
        review1.setDateCreated(LocalDate.now());
        reviewRepository.save(review1);
    }

}
