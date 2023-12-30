package com.Photography.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Photography.entity.Collection;
import com.Photography.entity.Image;
import com.Photography.service.CollectionService;

@RestController
@RequestMapping("/api/collections")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @GetMapping
    public ResponseEntity<List<Collection>> viewCollections() {
        return ResponseEntity.ok(collectionService.viewCollections());
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable String collectionId) {
        Optional<Collection> optionalCollection = collectionService.getCollectionById(collectionId);
        return optionalCollection.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{collectionId}/images")
    public ResponseEntity<List<Image>> getCollectionImages(@PathVariable String collectionId) {
        return ResponseEntity.ok(collectionService.getCollectionImages(collectionId));
    }

    @PostMapping
    public ResponseEntity<String> addCollection(@RequestParam String collectionName) {
        try {
            collectionService.addCollection(collectionName);
            return ResponseEntity.ok("Collection added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity<String> removeCollection(@PathVariable String collectionId) {
        try {
            collectionService.removeCollection(collectionId);
            return ResponseEntity.ok("Collection removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/{collectionId}/images")
    public ResponseEntity<String> addImageToCollection(
            @PathVariable String collectionId,
            @RequestParam String imageUrl) {
        try {
            collectionService.addImageToCollection(collectionId, imageUrl);
            return ResponseEntity.ok("Image added to collection successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{collectionId}/images/{imageId}")
    public ResponseEntity<String> removeImageFromCollection(
            @PathVariable String collectionId,
            @PathVariable String imageId) {
        try {
            collectionService.removeImageFromCollection(collectionId, imageId);
            return ResponseEntity.ok("Image removed from collection successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}