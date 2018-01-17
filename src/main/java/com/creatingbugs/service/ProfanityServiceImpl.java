package com.creatingbugs.service;

import com.creatingbugs.model.EntryType;
import com.creatingbugs.model.Profanity;
import com.creatingbugs.repository.ProfanityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of ProfanityService.
 *
 * Created by steve on 07/01/18.
 */
@Service
public class ProfanityServiceImpl implements ProfanityService {
    private static final Logger log = LoggerFactory.getLogger(ProfanityServiceImpl.class);

    private final ProfanityRepository profanityRepository;

    public ProfanityServiceImpl(ProfanityRepository profanityRepository) {
        this.profanityRepository = profanityRepository;
    }

    /**
     * Checks whether the supplied string contains any profanity.
     *
     * @param stringToCheck the string to check
     * @return whether the string contains profanity
     *
     * @should return true if stringToCheck exactly matches an element of profanity
     * @should return true if profanity is contained as a substring of stringToCheck
     * @should return false if stringToCheck does not exactly match an element of profanity
     * @should return false if stringToCheck is not contained as a substring of stringToCheck
     * @should return false if set of profanity is empty
     * @should be case insensitive
     * @should only call the database once
     * @should return false if a blacklisted word is on the whitelist
     * @should return false if a non-blacklisted word is on the whitelist
     */
    public boolean isStringContainingProfanity(String stringToCheck) {
        log.debug(String.format("Checking whether profanity exists in string: %s", stringToCheck));

        Set<String> blacklist = getAllWordsOfType(EntryType.BLACKLIST);
        Set<String> whitelist = getAllWordsOfType(EntryType.WHITELIST);

        blacklist.removeAll(whitelist);

        Set<String> foundProfanity = findAllProfanityWithinString(stringToCheck, blacklist);

        return foundProfanity.size() > 0;
    }


    /**
     * {@inheritDoc}
     *
     * @should add new words to the ProfanityRepository
     * @should check the ProfanityRepository if the word already exists
     * @should make no changes to the ProfanityRepository if the word already exists
     */
    public void addWordToBlacklist(String stringToAdd) {

    }

    /**
     * Gets all the words of a particular EntryType.
     *
     * @param entryType the EntryType to get
     * @return all words of the provided EntryType
     */
    private Set<String> getAllWordsOfType(EntryType entryType) {
        return profanityRepository.findAllByEntryType(entryType).stream()
                .map(Profanity::getWord).collect(Collectors.toSet());
    }

    /**
     * Finds all occurrences of the provided set of profanity within the provided string.
     *
     * @param stringToCheck the string to check for profanity items
     * @param blacklist the blacklist against which to check for the string entries
     * @return all occurring profanity words
     */
    private Set<String> findAllProfanityWithinString(String stringToCheck, Set<String> blacklist) {
        return blacklist.stream()
                .filter(word -> stringToCheck.toLowerCase().contains(word))
                .collect(Collectors.toSet());
    }
}
