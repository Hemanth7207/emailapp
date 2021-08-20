package com.wenable.hemanth.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.wenable.hemanth.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(String username);

	boolean existsByUsername(String username);

	User findByUsernameAndPassword(String username, String password);

}
