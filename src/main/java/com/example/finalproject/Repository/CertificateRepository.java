package com.example.finalproject.Repository;

import com.example.finalproject.Model.Certificate;
import com.example.finalproject.Model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate , Integer> {
    Certificate findCertificateById(Integer id);

    List<Certificate> findAllByTutor(Tutor tutor);

    // Certificate findCertificateByTutorId(Integer tutor_id);

}
