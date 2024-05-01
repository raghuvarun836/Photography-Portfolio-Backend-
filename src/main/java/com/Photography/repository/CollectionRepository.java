package com.Photography.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Photography.entity.Collection;

@Repository
public interface CollectionRepository extends MongoRepository<Collection, String>{

	Collection findByName(String collectionName);

	boolean existsByName(String collectionName);
}
