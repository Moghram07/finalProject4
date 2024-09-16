package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private double duration;

    @NotEmpty(message = "name should not be empty!")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @NotEmpty
    @Pattern(regexp = "ZOOM|FACE_TO_FACE|VIDEO|DOCUMENT")
    private String learningMethod; // ZOOM, FACE_TO_FACE, VIDEO, DOCUMENT

    @NotNull
    private int maxParticipants;

    @NotNull
    @Positive
    private double price;

    @Pattern(regexp = "PENDING|ACTIVE|CANCELED|COMPLETED")
    private String status; // PENDING, ACTIVE, CANCELED, COMPLETED

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "session_student",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @JsonIgnore
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    @OneToMany(mappedBy = "session",cascade = CascadeType.ALL)
    private Set<Document> documents;

    @OneToMany(mappedBy = "session",cascade = CascadeType.ALL)
    private Set<Video> videos;

    @OneToOne(mappedBy = "session", cascade = CascadeType.ALL)
    @JsonIgnore
    private ZoomMeeting zoomMeeting;

    @OneToOne(mappedBy = "session", cascade = CascadeType.ALL)
    @JsonIgnore
    private FaceToFace faceToFace;

}


