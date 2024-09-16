package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.Document;
import com.example.finalproject.Model.Student;
import com.example.finalproject.Model.Tutor;
import com.example.finalproject.Model.User;
import com.example.finalproject.Repository.DocumentRepository;
import com.example.finalproject.Repository.StudentRepository;
import com.example.finalproject.Repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final TutorRepository tutorRepository;
    private final StudentRepository studentRepository;

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public void addDocument(Document document, Integer tutorId) {
        Tutor tutor = tutorRepository.findTutorById(tutorId);
        if(tutor == null) {
            throw new ApiException("Tutor not found");
        }
        document.setTutor(tutor);
        documentRepository.save(document);
    }

    public Document updateDocument(Integer userId, Integer id, Document document) {
        Document d = findDocumentById(id);
        if(d == null) {
            throw new ApiException("Document not found");
        }
        if (!d.getTutor().getUser().getId().equals(userId)) {
            throw new ApiException("You do not have permission to perform this action on this document");
        }
        d.setTitle(document.getTitle());
        d.setPrice(document.getPrice());
        return documentRepository.save(d);
    }

    public void deleteDocument(Integer userId, Integer documentId) {
        Document document = documentRepository.findDocumentById(documentId);
        if (document == null) {
            throw new ApiException("Document not found");
        }
        if (!document.getTutor().getUser().getId().equals(userId)) {
            throw new ApiException("You do not have permission to perform this action on this document");
        }
        documentRepository.deleteById(documentId);
    }
    // added by Omar
    public Document findDocumentById(Integer id) {
        Document document = documentRepository.findDocumentById(id);
        if (document == null) {
            throw new ApiException("Document not found");
        }
        return document;
    }
    public List<Document> getAllDocumentsByTutorId(Integer tutorId) {
        List<Document> documents = documentRepository.findAllByTutorId(tutorId);
        if (documents.isEmpty()) {
            throw new ApiException("No documents found for this tutor");
        }
        Tutor tutor = tutorRepository.findTutorById(tutorId);
        if (tutor == null) {
            throw new ApiException("Tutor not found");
        }
        return documents;
    }

    public Document getDocumentByTitle(String title) {
        Document document = documentRepository.findDocumentByTitle(title);
        if (document == null) {
            throw new ApiException("Document not found");
        }
        return document;
    }
    //Reema - Omar
    //Assign Doc to student >> student
    public void assignDocToStudent(Integer docId, Integer studentId, User user) {
        Document document = documentRepository.findDocumentById(docId);
        if (document == null) {
            throw new ApiException("Document not found");
        }

        // Ensure only the tutor who owns the document can assign it
        if (!document.getTutor().getId().equals(user.getId())) {
            throw new ApiException("Access denied: You can only assign your own documents");
        }

        Student student = studentRepository.findStudentById(studentId);
        if (student == null) {
            throw new ApiException("Student not found");
        }
        if (!student.isEnrolled()) {
            throw new ApiException("Student is not enrolled, you can't assign this Document!");
        }

        document.setStudent(student);
        documentRepository.save(document);
    }
}

