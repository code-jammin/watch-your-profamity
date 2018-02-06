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
     * @throws WordAlreadyExistsException when the word already exists
     *
     * @should add the provided word to the ProfanityRepository as a blacklisted word
     * @should throw WordAlreadyExistsException if word already exists
     * @should not duplicate existing words on the database
     */
    void addWordToBlacklist(String stringToAdd) throws WordAlreadyExistsException;

    /**
     * Attempts to delete the provided word from the ProfanityRepository blacklisted words if it exists.
     *
     * @param stringToDelete the string to delete
     *
     * @throws WordDoesNotExistException when the word does not exist
     *
     * @should delete the provided word from the ProfanityRepository blacklist
     * @should throw WordDoesNotExistException if the word doesn't exist
     */
    void deleteWordFromBlacklist(String stringToDelete) throws WordDoesNotExistException;
}
