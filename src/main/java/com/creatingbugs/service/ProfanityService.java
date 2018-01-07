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
     * @should return whether the supplied string contains profanity
     */
    public boolean isStringContainingProfanity(String stringToCheckForProfanity);
}
