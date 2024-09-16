package com.example.finalproject.Controller;

import com.example.finalproject.Model.Club;
import com.example.finalproject.Service.ClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubController {

    private final ClubService clubService;

    @GetMapping("/get")
    public ResponseEntity getAllClubs() {
        return ResponseEntity.status(200).body(clubService.getAllClubs());
    }

    @PostMapping("/add/{studentId}")
    public ResponseEntity<String> addClub(@RequestBody Club club, @PathVariable Integer studentId) {
        clubService.addClub(club, studentId);
        return ResponseEntity.ok("Club added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateClub(@PathVariable Integer id,@Valid @RequestBody Club club) {
        clubService.updateClub(id,club);
        return ResponseEntity.status(200).body("Club updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClub(@PathVariable Integer id) {
        clubService.deleteClub(id);
        return ResponseEntity.status(200).body("Club deleted");
    }

    @PutMapping("/{club_id}/assign/{student_id}")
    public ResponseEntity joinClub(@PathVariable Integer club_id, @PathVariable Integer student_id) {
        clubService.joinClub(club_id,student_id);
        return ResponseEntity.status(200).body("join done");
    }

    /*Renad*/
    @GetMapping("/get/club/{name}")
    public ResponseEntity searchClubByName(@PathVariable String name){
        return ResponseEntity.status(200).body(clubService.searchClubByName(name));
    }

    /*Renad*/
    @GetMapping("/get/details/{club_id}")
    public ResponseEntity getClubDetails(@PathVariable Integer club_id){
        return ResponseEntity.status(200).body(clubService.getClubDetails(club_id));
    }

}
