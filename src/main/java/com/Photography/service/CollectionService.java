package com.Photography.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Photography.entity.Collection;
import com.Photography.entity.Image;
import com.Photography.repository.CollectionRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ImageService imageService;

    public List<Collection> viewCollections() {
        return collectionRepository.findAll();
    }

    public Optional<Collection> getCollectionById(String collectionId) {
        return collectionRepository.findById(collectionId);
    }
    
    public List<Image> getCollectionImages(String collectionId) {
        return getCollectionById(collectionId).map(Collection::getImages).orElse(Collections.emptyList());
    }

    public void addCollection(String collectionName) {
        Optional<Collection> existingCollection = collectionRepository.findByName(collectionName);
        existingCollection.ifPresent(collection -> {
            throw new RuntimeException("Collection with the same name already exists");
        });

        Collection newCollection = new Collection();
        newCollection.setName(collectionName);
        collectionRepository.save(newCollection);
    }

    public void removeCollection(String collectionId) {
        collectionRepository.deleteById(collectionId);
    }

    public void addImageToCollection(String collectionId, String imageUrl) {
        Optional<Collection> optionalCollection = collectionRepository.findById(collectionId);
        optionalCollection.ifPresent(collection -> {
            // if the image already exists
            Optional<Image> optionalImage = imageService.getImageByImageUrl(imageUrl);

            // If the image doesn't exist, create image
            Image newImage = optionalImage.orElseGet(() -> {
                Image image = new Image();
                image.setImageUrl(imageUrl);
                return imageService.addImage(image);
            });

            // Increment the reference count
            imageService.incrementReferenceCount(newImage.getId());
            // Adding the image reference to the collection
            collection.getImages().add(newImage);
            collectionRepository.save(collection);
        });
    }

    public void removeImageFromCollection(String collectionId, String imageId) {
        getCollectionById(collectionId).ifPresent(collection -> {
            collection.getImages().removeIf(image -> image.getId().equals(imageId));

            imageService.decrementReferenceCount(imageId);
            imageService.remove(imageId);
            collectionRepository.save(collection);
        });
    }


    public boolean collectionExists(String collectionName) {
        return collectionRepository.existsByName(collectionName);
    }

    public void updateCollection(Collection collection) {
        collectionRepository.save(collection);
    }
}
