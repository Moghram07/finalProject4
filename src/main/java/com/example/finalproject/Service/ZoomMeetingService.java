package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.SessionRepository;
import com.example.finalproject.Repository.StudentRepository;
import com.example.finalproject.Repository.TutorRepository;
import com.example.finalproject.Repository.ZoomMeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoomMeetingService {

    private final ZoomMeetingRepository zoomRepository;
    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;
    private final TutorRepository tutorRepository;

    public List<ZoomMeeting> getAllZoomMeetings() {
        return zoomRepository.findAll();
    }

    //Reema
    // Assign Zoom meeting to Session >> authorized by tutor
    public void addZoom(ZoomMeeting zoom, Integer sessionId, User user) {
        Tutor tutor = tutorRepository.findTutorById(user.getId());
        if (tutor == null) {
            throw new ApiException("Only tutors can add Zoom meetings.");
        }
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found.");
        }
        // Ensure the tutor owns the session
        if (!session.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only add Zoom meetings to your own sessions.");
        }
        zoom.setSession(session);
        zoomRepository.save(zoom);
    }

    public ZoomMeeting updateZoom(Integer id, ZoomMeeting zoom, User user) {
        ZoomMeeting z = zoomRepository.findZoomMeetingByMeetingId(id);
        if (z == null) {
            throw new ApiException("Zoom meeting not found.");
        }
        if (!z.getSession().getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only update Zoom meetings for your own sessions.");
        }
        z.setPrice(zoom.getPrice());
        z.setUrl(zoom.getUrl());
        z.setMeetingDate(zoom.getMeetingDate());
        return zoomRepository.save(z);
    }

    public void deleteZoom(Integer id, User user) {
        ZoomMeeting z = zoomRepository.findZoomMeetingByMeetingId(id);
        if (z == null) {
            throw new ApiException("Zoom meeting not found.");
        }
        // Ensure the Zoom meeting belongs to the current tutor
        if (!z.getSession().getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only delete Zoom meetings from your own sessions.");
        }
        zoomRepository.deleteById(id);
    }
    //Reema
    //Assign zoom to student >> student
    public void assignZoomToStudent(Integer zoomId, Integer studentId, User user) {
        ZoomMeeting zoom = zoomRepository.findZoomMeetingByMeetingId(zoomId);
        if (zoom == null) {
            throw new ApiException("Zoom meeting not found.");
        }
        Tutor tutor = tutorRepository.findTutorById(user.getId());
        if (tutor == null) {
            throw new ApiException("Only tutors can assign Zoom meetings.");
        }
        // Ensure the tutor owns the session the Zoom meeting is related to
        if (!zoom.getSession().getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only assign Zoom meetings from your own sessions.");
        }

        Student student = studentRepository.findStudentById(studentId);
        if (student == null) {
            throw new ApiException("Student not found.");
        }
        if (!student.isEnrolled()) {
            throw new ApiException("Student is not enrolled, you can't assign this Zoom meeting.");
        }
        zoom.setStudent(student);
        zoomRepository.save(zoom);
    }

}

