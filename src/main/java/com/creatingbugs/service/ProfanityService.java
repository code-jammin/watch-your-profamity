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
     * @should return blacklisted items as profanity
     * @should return whitelisted items to not be marked as profanity
     * @should return whitelisted and blacklisted items as not profanity
     */
    public boolean isStringContainingProfanity(String stringToCheckForProfanity);
}
