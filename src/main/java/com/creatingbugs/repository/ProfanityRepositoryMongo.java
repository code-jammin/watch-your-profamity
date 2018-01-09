package com.creatingbugs.repository;

import com.creatingbugs.model.Profanity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by steve on 08/01/18.
 */
public interface ProfanityRepositoryMongo extends MongoRepository<Profanity, String> {
}
