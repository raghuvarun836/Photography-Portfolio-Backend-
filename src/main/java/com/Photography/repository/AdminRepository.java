package com.Photography.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Photography.entity.Admin;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String>{

	Admin findByUsername(String username);

}
