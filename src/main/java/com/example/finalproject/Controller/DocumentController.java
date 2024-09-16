package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Model.Document;
import com.example.finalproject.Model.User;
import com.example.finalproject.Service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/get")
    public ResponseEntity getDocuments(){
        return ResponseEntity.status(200).body(documentService.getAllDocuments());
    }

    @PostMapping("/add")
    public ResponseEntity addDocument(@Valid @RequestBody Document document, @AuthenticationPrincipal User user) {
        documentService.addDocument(document, user.getId());
        return ResponseEntity.status(201).body(new ApiResponse("Document added successfully"));
    }

    @PutMapping("/update/{documentId}")
    public ResponseEntity updateDocument(@PathVariable Integer documentId, @Valid @RequestBody Document document, @AuthenticationPrincipal User user) {
        documentService.updateDocument(user.getId(), documentId, document);
        return ResponseEntity.status(200).body(new ApiResponse("Document updated successfully"));
    }

    @DeleteMapping("/delete/{documentId}")
    public ResponseEntity deleteDocument(@PathVariable Integer documentId, @AuthenticationPrincipal User user) {
        documentService.deleteDocument(user.getId(), documentId);
        return ResponseEntity.status(200).body(new ApiResponse("Document deleted successfully"));
    }

    @PutMapping("/assign/{documentId}/student/{studentId}")
    public ResponseEntity assignDocumentToStudent(@PathVariable Integer documentId, @PathVariable Integer studentId, @AuthenticationPrincipal User user) {
        documentService.assignDocToStudent(documentId, studentId, user);
        return ResponseEntity.status(200).body(new ApiResponse("Document assigned to student successfully"));
    }

    @GetMapping("/tutor")
    public ResponseEntity getDocumentsByTutor(@AuthenticationPrincipal User user) {
        List<Document> documents = documentService.getAllDocumentsByTutorId(user.getId());
        return ResponseEntity.status(200).body(documents);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Document> getDocumentByTitle(@PathVariable String title) {
        Document document = documentService.getDocumentByTitle(title);
        return ResponseEntity.status(200).body(document);
    }

}
