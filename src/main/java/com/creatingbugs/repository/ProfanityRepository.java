package com.creatingbugs.repository;

import com.creatingbugs.model.Profanity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by steve on 08/01/18.
 */
public interface ProfanityRepository extends MongoRepository<Profanity, String> {
}
