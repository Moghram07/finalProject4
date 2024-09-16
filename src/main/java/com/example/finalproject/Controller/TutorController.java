
package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.DTO.TutorDTO;
import com.example.finalproject.Service.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tutor")
@RequiredArgsConstructor
public class TutorController {
    private final TutorService tutorService;

    @GetMapping("/get")
    public ResponseEntity getAllTutors(){
        return ResponseEntity.status(200).body(tutorService.getAllTutors());
    }

    @PostMapping("/register")
    public ResponseEntity tutorRegister(@RequestBody @Valid TutorDTO tutorDTO){
        tutorService.registerTutor(tutorDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Tutor registered successfully!"));
    }

    @PutMapping("/update/{auth_id}")
    public ResponseEntity updateTutors(@RequestBody @Valid TutorDTO tutorDTO, @PathVariable Integer auth_id){
        tutorService.updateTutor(tutorDTO,auth_id);
        return ResponseEntity.status(200).body(new ApiResponse("Tutor updated successfully!"));
    }

    @DeleteMapping("/delete/{auth_id}")
    public ResponseEntity deleteTutors( @PathVariable Integer auth_id){
        tutorService.deleteTutor(auth_id);
        return ResponseEntity.status(200).body(new ApiResponse("Tutor deleted successfully!"));
    }

    @GetMapping("/tutorsWithRecommendations")
    public ResponseEntity getAllTutorsWithRecommendations (){
        return ResponseEntity.status(200).body(tutorService.getAllTutorsWithRecommendations());
    }

    /*Renad*/
    @GetMapping("/get/name/{firstName}")
    public ResponseEntity getTutorDetailsByName(@PathVariable String firstName){
        return ResponseEntity.status(200).body(tutorService.getTutorDetailsByName(firstName));
    }
    /*Renad*/
    @GetMapping("/get/tutor/reviews/{tutor_id}")
    public ResponseEntity getTutorReviews(@PathVariable Integer tutor_id){
        return ResponseEntity.status(200).body(tutorService.getTutorReviews(tutor_id));
    }

    /*Renad*/
    @GetMapping("/get/avg/{tutor_id}")
    public ResponseEntity getAverageRatingForTutor(@PathVariable Integer tutor_id){
        double averageRating=tutorService.getAverageRatingForTutor(tutor_id);
        return ResponseEntity.status(200).body(averageRating);
    }
    /*Renad*/
    @GetMapping("/get/tutor/products/{tutor_id}")
    public ResponseEntity getTutorProducts(@PathVariable Integer tutor_id){
        return ResponseEntity.status(200).body(tutorService.getTutorProducts(tutor_id));
    }
    /*Renad*/
    @GetMapping("/get/tutors/{gpa}")
    public ResponseEntity getTutorsByGPA(@PathVariable double gpa){
        return ResponseEntity.status(200).body(tutorService.getTutorsByGPA(gpa));
    }

}
