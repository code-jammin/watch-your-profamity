package com.creatingbugs.repository;

import com.creatingbugs.model.Profanity;

import java.util.List;

/**
 * An interface for a repository for storing profanity objects.
 *
 * Created by steve on 07/01/18.
 */
public interface ProfanityRepository {

    /**
     * @return all profanity objects stored in the repository
     * @should return a set of profanity objects stored in the repository
     */
    public List<Profanity> findAll();
}
