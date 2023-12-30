package com.Photography.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.Photography.entity.Image;
import com.Photography.repository.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Optional<Image> getImageByImageUrl(String imageUrl) {
        return imageRepository.findByImageUrl(imageUrl);
    }
    
    public boolean imageExists(String imageUrl) {
        return getImageByImageUrl(imageUrl).isPresent();
    }

    public Image addImage(Image image) {
        return imageRepository.save(image);
    }
    
    public void remove(String imageId) {
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        optionalImage.ifPresent(image -> {
            if (image.getReferenceCount() == 0) {
                imageRepository.deleteById(imageId);
            }
        });
    }

    public void updateImage(Image image) {
        imageRepository.save(image);
    }

    public void incrementReferenceCount(String imageId) {
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        optionalImage.ifPresent(image -> {
            image.incrementReferenceCount();
            imageRepository.save(image);
        });
    }

    public void decrementReferenceCount(String imageId) {
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        optionalImage.ifPresent(image -> {
            if (image.getReferenceCount() > 0) {
                image.decrementReferenceCount();
                imageRepository.save(image);
            }
        });
    }
}
