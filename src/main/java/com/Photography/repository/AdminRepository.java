package com.Photography.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Photography.entity.Admin;

public interface AdminRepository extends MongoRepository<Admin, String>{

	Admin findByUsername(String username);

}
