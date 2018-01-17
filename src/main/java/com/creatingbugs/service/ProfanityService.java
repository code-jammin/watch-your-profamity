package com.creatingbugs.service;

/**
 * A service that handles interactions concerning profanities.
 *
 * Created by steve on 07/01/18.
 */
public interface ProfanityService {

    /**
     * Checks whether the supplied string contains any profanity.
     *
     * @param stringToCheckForProfanity the string to check
     * @return whether the string contains profanity
     *
     * @should return blacklisted items as profanity
     * @should return whitelisted items to not be marked as profanity
     */
    boolean isStringContainingProfanity(String stringToCheckForProfanity);

    /**
     * Attempts to add the provided word to the ProfanityRepository as a blacklisted word if it doesn't already exist.
     *
     * @param stringToAdd the string to add to the ProfanityRepository
     *
     * @should add the provided word to the ProfanityRepository as a blacklisted word
     * @should not duplicate existing words
     */
    void addWordToBlacklist(String stringToAdd);
}
