package com.Photography.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "images")
public class Image {

    @Id
    private String id;
    private String imageUrl;
    private int referenceCount = 0;

    public Image() {
        // Default constructor
    }

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(int referenceCount) {
        this.referenceCount = referenceCount;
    }

    public void incrementReferenceCount() {
        this.referenceCount++;
    }

    public void decrementReferenceCount() {
        if (this.referenceCount > 0) {
            this.referenceCount--;
        }
    }

    @Override
    public String toString() {
        return "Image [id=" + id + ", imageUrl=" + imageUrl + ", referenceCount=" + referenceCount + "]";
    }
}
