package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.CourseRepository;
import com.example.finalproject.Repository.SessionRepository;
import com.example.finalproject.Repository.StudentRepository;
import com.example.finalproject.Repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final TutorRepository tutorRepository;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    //Reema
    //assign session to course
    public void addSession(Session session, Integer course_id, User user) {
        Course course = courseRepository.findCourseById(course_id);
        if (course == null) {
            throw new ApiException("Course not found");
        }
        Tutor tutor = tutorRepository.findTutorById(user.getId());
        if (tutor == null) {
            throw new ApiException("Only tutors can add sessions");
        }
        session.setCourse(course);
        session.setStatus("PENDING");
        course.getSessions().add(session);
        sessionRepository.save(session);
    }

    public Session updateSession(Integer id, Session session, User user) {
        Session s = sessionRepository.findSessionById(id);
        if (s == null) {
            throw new ApiException("Session not found");
        }
        if (!s.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only update your own sessions");
        }
        s.setPrice(session.getPrice());
        s.setLearningMethod(session.getLearningMethod());
        s.setDuration(session.getDuration());
        return sessionRepository.save(s);
    }

    public void deleteSession(Integer sessionId, User user) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if (!session.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only delete your own sessions");
        }
        sessionRepository.deleteById(sessionId);
    }

    //Reema
    //assign session to tutor
    public void assignSessionToTutor(Integer sessionId, Integer tutorId, User user) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        Tutor tutor = tutorRepository.findTutorById(tutorId);
        if (tutor == null) {
            throw new ApiException("Tutor not found");
        }
        if (!session.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only assign tutors to your own sessions");
        }
        session.setTutor(tutor);
        sessionRepository.save(session);
    }

    //Reema - Omar
    public void assignSessionToStudent(Integer sessionId, Integer studentId, User user) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if (!session.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only assign students to your own sessions");
        }
        Student student = studentRepository.findStudentById(studentId);
        if (student == null) {
            throw new ApiException("Student not found");
        }
        if (session.getStudents().size() >= session.getMaxParticipants()) {
            throw new ApiException("Maximum number of participants reached");
        }
        session.getStudents().add(student);
        sessionRepository.save(session);
    }

    public void startSession(Integer sessionId, User user) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if (!session.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only start your own sessions");
        }
        if ("PENDING".equals(session.getStatus())) {
            session.setStatus("ACTIVE");
            sessionRepository.save(session);
        } else {
            throw new ApiException("Session cannot be started. Current status: " + session.getStatus());
        }
    }

    public void cancelSession(Integer sessionId, User user) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if (!session.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only cancel your own sessions");
        }
        if ("ACTIVE".equals(session.getStatus()) || "PENDING".equals(session.getStatus())) {
            session.setStatus("CANCELED");
            session.getStudents().clear();
            sessionRepository.save(session);
        } else {
            throw new ApiException("Session cannot be canceled. Current status: " + session.getStatus());
        }
    }

    // end a session if it's "ACTIVE"
    public void endSession(Integer sessionId, User user) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if (!session.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only end your own sessions");
        }
        if ("ACTIVE".equals(session.getStatus())) {
            session.setStatus("COMPLETED");
            sessionRepository.save(session);
        } else {
            throw new ApiException("Only active sessions can be completed");
        }
    }

    // Remove a student from a session (effectively blocking them from attending)
    public void blockStudentFromSession(Integer sessionId, Integer studentId, User user) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found");
        }

        // Ensure that the authenticated user is the tutor who owns the session
        if (!session.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("Access denied: You can only block students from sessions you own");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ApiException("Student not found"));

        if (session.getStudents().contains(student)) {
            session.getStudents().remove(student);
            sessionRepository.save(session);
        } else {
            throw new ApiException("The student is not part of this session.");
        }
    }


    public Set<Student> getStudentsInSession(Integer sessionId, User user) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found");
        }
        if (!session.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only view students in your own sessions");
        }
        return session.getStudents();
    }

    public List<Session> getSessionsByMaxParticipants(int maxParticipants) {
        return sessionRepository.findAllByMaxParticipants(maxParticipants);
    }
    // All Done by Omar
}
