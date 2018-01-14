package com.creatingbugs.repository;

import com.creatingbugs.model.EntryType;
import com.creatingbugs.model.Profanity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository for storing the known profanity.
 *
 * Created by steve on 08/01/18.
 */
public interface ProfanityRepository extends MongoRepository<Profanity, String> {

    public List<Profanity> findAllByEntryType(EntryType entryType);
}
