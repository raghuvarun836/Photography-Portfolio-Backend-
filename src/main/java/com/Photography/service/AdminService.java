package com.Photography.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Photography.entity.Admin;
import com.Photography.entity.Collection;
import com.Photography.entity.Image;
import com.Photography.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CollectionService collectionService;

    public boolean authenticateAdmin(String username, String password) {
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);
        return adminOptional.map(admin -> password.equals(admin.getPassword())).orElse(false);
    }

    public void addCollection(String collectionName) {
        collectionService.addCollection(collectionName);
    }

    public void removeCollection(String collectionId) {
        collectionService.removeCollection(collectionId);
    }

    public List<Collection> viewCollections() {
        return collectionService.viewCollections();
    }

    public void addImageToCollection(String collectionId, String imageUrl) {
        collectionService.addImageToCollection(collectionId, imageUrl);
    }

    public void removeImageFromCollection(String collectionId, String imageId) {
        collectionService.removeImageFromCollection(collectionId, imageId);
    }

    public boolean collectionExists(String collectionName) {
        return collectionService.collectionExists(collectionName);
    }

    public void renameCollection(String collectionId, String newCollectionName) {
        if (collectionService.collectionExists(newCollectionName)) {
            throw new RuntimeException("Collection with the new name already exists");
        }

        Optional<Collection> optionalCollection = collectionService.getCollectionById(collectionId);
        optionalCollection.ifPresent(collection -> {
            collection.setName(newCollectionName);
            collectionService.updateCollection(collection);
        });
    }

    public List<Image> getImagesInCollection(String collectionId) {
        return collectionService.getCollectionImages(collectionId);
    }
}