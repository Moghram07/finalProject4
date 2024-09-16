package com.example.finalproject.Repository;

import com.example.finalproject.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course findCourseById(Integer id);

    List<Course> findCourseByPriceBetween(double minPrice, double maxPrice);


    @Query("SELECT c FROM Course c WHERE SIZE(c.students) >= 4 ORDER BY SIZE(c.students) DESC")
    List<Course> findMostPopularCourse();

    @Query("SELECT c FROM Course c WHERE c.learningMethod LIKE %:learningMethod%")
    List<Course> findCoursesByLearningMethod(@PathVariable("learningMethod") String learningMethod);

    Course findCourseByTutorId(Integer id);

    Course findCourseBySubject(String subject);
}
