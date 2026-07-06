package com.pcveldi.rag.controller;

import com.pcveldi.rag.model.SearchRequest;
import com.pcveldi.rag.model.SearchResult;
import com.pcveldi.rag.service.RAGService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final RAGService ragService;

    @PostMapping("/query")
    public ResponseEntity<SearchResult> query(@Valid @RequestBody SearchRequest request) {
        SearchResult result = ragService.query(request.getQuestion(), request.getTopK());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/documents")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) {
        String documentId = ragService.ingestDocument(file);
        return ResponseEntity.ok(documentId);
    }

    @GetMapping("/documents")
    public ResponseEntity<List<String>> listDocuments() {
        return ResponseEntity.ok(ragService.listDocuments());
    }

    @DeleteMapping("/documents/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String documentId) {
        ragService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}
