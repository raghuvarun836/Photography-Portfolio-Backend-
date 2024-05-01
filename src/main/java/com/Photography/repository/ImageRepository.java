package com.Photography.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Photography.entity.Image;

@Repository
public interface ImageRepository extends MongoRepository<Image, String>{

	Image findByImageUrl(String imageUrl);
}
