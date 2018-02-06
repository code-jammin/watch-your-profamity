package com.creatingbugs.model;

import java.util.Set;

/**
 * A response object from the ProfanityService.
 * 
 * Created by steve on 27/01/18.
 */
public class ProfanityResponse {
    private final boolean isContainsProfanity;

    private final Set<String> foundProfanity;

    ProfanityResponse(boolean isResponseContainingProfanity, Set<String> foundProfanity) {
        this.isContainsProfanity = isResponseContainingProfanity;
        this.foundProfanity = foundProfanity;
    }

    public boolean isContainsProfanity() {
        return isContainsProfanity;
    }

    public Set<String> getFoundProfanity() {
        return foundProfanity;
    }
}
