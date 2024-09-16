package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Model.Session;
import com.example.finalproject.Model.Student;
import com.example.finalproject.Model.User;
import com.example.finalproject.Service.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/get")
    public ResponseEntity<List<Session>> getAllSessions(@AuthenticationPrincipal User user) {
        List<Session> sessions = sessionService.getAllSessions();
        return ResponseEntity.status(200).body(sessions);
    }

    @PostMapping("/add/{courseId}")
    public ResponseEntity<ApiResponse> addSession(@RequestBody @Valid Session session, @PathVariable Integer courseId, @AuthenticationPrincipal User user) {
        sessionService.addSession(session, courseId, user);
        return ResponseEntity.status(201).body(new ApiResponse("Session created successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateSession(@PathVariable Integer id, @RequestBody @Valid Session session, @AuthenticationPrincipal User user) {
        sessionService.updateSession(id, session, user);
        return ResponseEntity.status(200).body(new ApiResponse("Session updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSession(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        sessionService.deleteSession(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("Session deleted successfully"));
    }

    @PutMapping("/assign/tutor/{sessionId}/{tutorId}")
    public ResponseEntity<ApiResponse> assignSessionToTutor(@PathVariable Integer sessionId, @PathVariable Integer tutorId, @AuthenticationPrincipal User user) {
        sessionService.assignSessionToTutor(sessionId, tutorId, user);
        return ResponseEntity.status(200).body(new ApiResponse("Session assigned to tutor successfully"));
    }

    @PutMapping("/assign/student/{sessionId}/{studentId}")
    public ResponseEntity<ApiResponse> assignSessionToStudent(@PathVariable Integer sessionId, @PathVariable Integer studentId, @AuthenticationPrincipal User user) {
        sessionService.assignSessionToStudent(sessionId, studentId, user);
        return ResponseEntity.status(200).body(new ApiResponse("Session assigned to student successfully"));
    }

    @PutMapping("/start/{id}")
    public ResponseEntity<ApiResponse> startSession(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        sessionService.startSession(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("Session started successfully"));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<ApiResponse> cancelSession(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        sessionService.cancelSession(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("Session canceled successfully"));
    }

    @PutMapping("/end/{id}")
    public ResponseEntity<ApiResponse> endSession(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        sessionService.endSession(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("Session completed successfully"));
    }

    @PutMapping("/block/{sessionId}/{studentId}")
    public ResponseEntity<ApiResponse> blockStudentFromSession(@PathVariable Integer sessionId, @PathVariable Integer studentId, @AuthenticationPrincipal User user) {
        sessionService.blockStudentFromSession(sessionId, studentId, user);
        return ResponseEntity.status(200).body(new ApiResponse("Student blocked from session successfully"));
    }

    @GetMapping("/students/{sessionId}")
    public ResponseEntity<Set<Student>> getStudentsInSession(@PathVariable Integer sessionId, @AuthenticationPrincipal User user) {
        Set<Student> students = sessionService.getStudentsInSession(sessionId, user);
        return ResponseEntity.status(200).body(students);
    }

    @GetMapping("/max-participants/{maxParticipants}")
    public ResponseEntity<List<Session>> getSessionsByMaxParticipants(@PathVariable int maxParticipants) {
        List<Session> sessions = sessionService.getSessionsByMaxParticipants(maxParticipants);
        return ResponseEntity.status(200).body(sessions);
    }
}
