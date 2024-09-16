package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Model.Certificate;
import com.example.finalproject.Service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/certificate")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping("/get")
    public ResponseEntity getAllCertificates() {
        return ResponseEntity.status(200).body(certificateService.getAllCertificates());
    }

//    @GetMapping("/myCertificates")
//    public ResponseEntity getMyCertificates(@AuthenticationPrincipal User user) {
//        return ResponseEntity.status(200).body(certificateService.getMyCertificate(user.getId()));
//    }


    @PostMapping("/add")
    public ResponseEntity addCertificate(@Valid @RequestBody Certificate certificate) {
        certificateService.addCertificate(certificate);
        return ResponseEntity.status(200).body(new ApiResponse("Certificate added successfully"));
    }

    @GetMapping("/issueCertificate/{certificate_id}/{tutor_id}")
    public ResponseEntity issueCertificate(@PathVariable Integer certificate_id, @PathVariable Integer tutor_id) {
        return ResponseEntity.status(200).body(certificateService.issueCertificate(certificate_id, tutor_id));
    }


    @PutMapping("/update/{certificate_id}")
    public ResponseEntity updateCertificate(@Valid @RequestBody Certificate certificate, @PathVariable Integer certificate_id) {
        certificateService.updateCertificate(certificate, certificate_id);
        return ResponseEntity.status(200).body(new ApiResponse("Certificate updated successfully"));
    }


    @DeleteMapping("/delete/{certificate_id}")
    public ResponseEntity deleteCertificate(@PathVariable Integer certificate_id) {
        certificateService.deleteCertificate(certificate_id);
        return ResponseEntity.status(200).body(new ApiResponse("Certificate deleted successfully"));
    }


//    @GetMapping("/issueCertificate/{certificate_id}/{tutor_id}")
//    public ResponseEntity issueCertificate (@PathVariable Integer certificate_id,@PathVariable Integer tutor_id){
//        return ResponseEntity.status(200).body(certificateService.issueCertificate(certificate_id,tutor_id));
//    }
}