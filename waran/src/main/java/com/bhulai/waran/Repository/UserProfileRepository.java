package com.bhulai.waran.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bhulai.waran.Entity.UserProfile;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, String>{
	
	UserProfile findByEmail(String email);

}
