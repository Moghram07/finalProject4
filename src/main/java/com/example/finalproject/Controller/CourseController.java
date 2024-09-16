package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Model.Course;
import com.example.finalproject.Model.Student;
import com.example.finalproject.Service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/get")
    public ResponseEntity getAllCourses(){
        return ResponseEntity.status(200).body(courseService.getAllCourses());
    }

    @PostMapping("/add/{tutor_id}")
    public ResponseEntity addCourse(@Valid @RequestBody Course course, @PathVariable Integer tutor_id){
        courseService.addCourse(course, tutor_id);
        return ResponseEntity.status(200).body(new ApiResponse("Course added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCourse(@PathVariable Integer id,@Valid @RequestBody Course course){
        courseService.updateCourse(course, id);
        return ResponseEntity.status(200).body(new ApiResponse("Course updated successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCourse(@PathVariable Integer id){
        courseService.deleteCourse(id);
        return ResponseEntity.status(200).body(new ApiResponse("Course deleted successfully!"));
    }
    @GetMapping("/courseFilter/{minPrice}/{maxPrice}")
    public ResponseEntity CourseFilter(@PathVariable double minPrice ,@PathVariable double maxPrice){
        return ResponseEntity.status(200).body(courseService.courseFilter(minPrice,maxPrice));
    }


    @GetMapping("/findCoursesByLearningMethod/{learningMethod}")
    public ResponseEntity findCoursesByLearningMethod (@PathVariable String learningMethod){
        return ResponseEntity.status(200).body(courseService.findCoursesByLearningMethod(learningMethod));
    }

    /*Renad*/
    @GetMapping("/get/course/{course_id}")
    public ResponseEntity enrolledStudentsWithCourse(@PathVariable Integer course_id){
        List<Student> students= courseService.enrolledStudentsWithCourse(course_id);
        return ResponseEntity.status(200).body(students);
    }
    /*Renad*/
    @GetMapping("/get/tutor/{tutor_id}")
    public ResponseEntity enrolledStudentsWithTutor(@PathVariable Integer tutor_id){
        List<Student> students= courseService.enrolledStudentsWithTutor(tutor_id);
        return ResponseEntity.status(200).body(students);
    }
    /*Renad*/
    @GetMapping("/get/subject/{subject}")
    public ResponseEntity searchCourse(@PathVariable String subject){
        return ResponseEntity.status(200).body(courseService.searchCourse(subject));
    }
    /*Renad*/
    @GetMapping("/get/details/{course_id}")
    public ResponseEntity getCourseDetails(@PathVariable Integer course_id){
        return ResponseEntity.status(200).body(courseService.getCourseDetails(course_id));
    }

    /*Renad*/
    @GetMapping("/get/course/reviews/{course_id}/")
    public ResponseEntity getCourseReviews(@PathVariable Integer course_id,@PathVariable Integer review_id){
        return ResponseEntity.status(200).body(courseService.getCourseReviews(course_id));
    }

}