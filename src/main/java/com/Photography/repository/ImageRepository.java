package com.Photography.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Photography.entity.Image;

public interface ImageRepository extends MongoRepository<Image, String>{

	Optional<Image> findByImageUrl(String imageUrl);
}
