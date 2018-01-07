package com.creatingbugs.service;

import com.creatingbugs.model.Profanity;
import com.creatingbugs.repository.ProfanityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of ProfanityService.
 *
 * Created by steve on 07/01/18.
 */
@Service
public class ProfanityServiceImpl implements ProfanityService {
    private static final Logger log = LoggerFactory.getLogger(ProfanityServiceImpl.class);

    ProfanityRepository profanityRepository;

    public ProfanityServiceImpl(ProfanityRepository profanityRepository) {
        this.profanityRepository = profanityRepository;
    }

    /**
     * Checks whether the supplied string contains any profanity.
     *
     * @param stringToCheck the string to check
     * @return whether the string contains profanity
     * @should return true if stringToCheck exactly matches an element of profanity
     * @should return true if profanity is contained as a substring of stringToCheck
     * @should return false if stringToCheck does not exactly match an element of profanity
     * @should return false if stringToCheck is not contained as a substring of stringToCheck
     * @should return false if set of profanity is empty
     * @should be case insensitive
     * @should only call the database once
     */
    public boolean isStringContainingProfanity(String stringToCheck) {
        log.debug(String.format("Checking whether profanity exists in string: %s", stringToCheck));

        Optional<Profanity> foundProfanity = profanityRepository.getAllProfanity().stream()
                .filter(profanity -> stringToCheck.toLowerCase().contains(profanity.getWord()))
                .findFirst();

        return foundProfanity.isPresent();
    }
}
