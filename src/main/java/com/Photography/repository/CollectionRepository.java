package com.Photography.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Photography.entity.Collection;

public interface CollectionRepository extends MongoRepository<Collection, String>{

	Optional<Collection> findByName(String collectionName);

	boolean existsByName(String collectionName);
}
