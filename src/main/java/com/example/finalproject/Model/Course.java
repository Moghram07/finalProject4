package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name should not be EMPTY!")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @NotEmpty(message = "Description should not be EMPTY!")
    @Column(columnDefinition = "varchar(100) not null")
    private String description;

    @NotEmpty(message = "learning Method should not be EMPTY!")
    @Column(columnDefinition = "varchar(20) not null")
    private String learningMethod; //session type

    @NotEmpty(message = "Subject should not be EMPTY!")
    @Column(columnDefinition = "varchar(20) not null")
    private String subject;


    @NotNull(message = "Duration should not be null!")
    @Column(columnDefinition = "int not null")
    private int duration;

    @NotNull(message = "Price should not be null!")
    @Column(columnDefinition = "DOUBLE not null")
    private double price;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Session> sessions;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Review> reviews;

}
