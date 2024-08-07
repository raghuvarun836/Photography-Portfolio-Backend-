package com.Photography.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.Photography.dto.StringRequest;
import com.Photography.entity.Admin;
import com.Photography.entity.Collection;
import com.Photography.entity.Image;
import com.Photography.service.AdminJWTService;
import com.Photography.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private AdminJWTService adminJWTService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.save(admin);
        return ResponseEntity.ok(savedAdmin);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Admin admin)
    {
    	System.out.println("Received login request");
    	Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword()));
    	if (auth.isAuthenticated()) {
            String token = adminJWTService.generateToken(admin.getUsername());
            adminService.updateTokenInvalidationTime(admin.getUsername(), LocalDateTime.now().plusHours(1));
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String username = adminJWTService.extractUserName(token.substring(7));
        adminService.updateTokenInvalidationTime(username, LocalDateTime.now().minusHours(1));
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/collections")
    public ResponseEntity<List<Collection>> viewCollections() {
        try {
            List<Collection> collections = adminService.viewCollections();
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PostMapping("/addCollection")
    public ResponseEntity<String> addCollection(@RequestBody StringRequest stringRequest) {
        try {
            String collectionName = stringRequest.getString();
            // Check if a collection with the same name already exists
            if (adminService.collectionExists(collectionName)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Collection with the same name already exists");
            }

            adminService.addCollection(collectionName);
            return ResponseEntity.status(HttpStatus.CREATED).body("Collection added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/removeCollection/{collectionId}")
    public ResponseEntity<String> removeCollection(@PathVariable String collectionId) {
        try {
            adminService.removeCollection(collectionId);
            return ResponseEntity.status(HttpStatus.OK).body("Collection removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/renameCollection/{collectionId}")
    public ResponseEntity<String> renameCollection(
            @PathVariable String collectionId,
            @RequestBody StringRequest newCollectionName
    ) {
        try {
            // Check if a collection with the new name already exists
            if (adminService.collectionExists(newCollectionName.getString())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Collection with the new name already exists");
            }

            adminService.renameCollection(collectionId, newCollectionName.getString());
            return ResponseEntity.status(HttpStatus.OK).body("Collection renamed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/collection/{collectionId}/images")
    public ResponseEntity<List<Image>> getImagesInCollection(@PathVariable String collectionId) {
        try {
            List<Image> images = adminService.getImagesInCollection(collectionId);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/addImageToCollection/{collectionId}")
    public ResponseEntity<String> addImageToCollection(
            @PathVariable String collectionId,
            @RequestBody StringRequest imageUrl
    ) {
        try {
            adminService.addImageToCollection(collectionId, imageUrl.getString());
            return ResponseEntity.status(HttpStatus.CREATED).body("Image added to collection successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/removeImageFromCollection/{collectionId}/{imageId}")
    public ResponseEntity<String> removeImageFromCollection(
            @PathVariable String collectionId,
            @PathVariable String imageId
    ) {
        try {
            adminService.removeImageFromCollection(collectionId, imageId);
            return ResponseEntity.status(HttpStatus.OK).body("Image removed from collection successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}