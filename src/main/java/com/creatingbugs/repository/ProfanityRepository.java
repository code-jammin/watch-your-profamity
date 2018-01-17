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

    /**
     * Find all words by EntryType
     *
     * @param entryType the EntryType to find
     * @return the list of words
     *
     * @should only return the specified EntryType of word
     * @should return all words of the specified EntryType
     */
    public List<Profanity> findAllByEntryType(EntryType entryType);

    /**
     * Find a specific word of a specific type
     *
     * @param word the word to find
     * @param entryType the entryType to find
     * @return a word that matches the word and entry type
     *
     * @should return a Profanity object matching the word of the specified type
     */
    public Profanity findDistinctByWordAndEntryType(String word, EntryType entryType);
}
