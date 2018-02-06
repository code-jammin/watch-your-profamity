package com.creatingbugs.service;

import com.creatingbugs.model.EntryType;
import com.creatingbugs.model.Profanity;
import com.creatingbugs.repository.ProfanityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
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
     * @should throw WordAlreadyExistsException if word already exists
     */
    public void addWordToBlacklist(String stringToAdd) throws WordAlreadyExistsException {
        Profanity profanityToAdd = new Profanity(stringToAdd, EntryType.BLACKLIST);
        log.info(String.format("Attempting to add word: '%s' to the blacklist", stringToAdd));

        if (isWordOfEntryTypeAlreadyExists(profanityToAdd)) {
            log.error(String.format("Word: '%s' already exists on the blacklist, unable to add", stringToAdd));
            throw new WordAlreadyExistsException(String.format("Word: '%s' already exists on the blacklist, unable to add", stringToAdd));
        } else {
            profanityRepository.save(profanityToAdd);
            log.info(String.format("Word: '%s' added successfully to the blacklist", stringToAdd));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @should delete the word from the ProfanityRepository
     * @should throw WordDoesNotExistException if the word does not exist
     */
    public void deleteWordFromBlacklist(String stringToDelete) throws WordDoesNotExistException {
        log.info(String.format("Attempting to delete word: '%s' from the blacklist", stringToDelete));
        List<Profanity> deletedProfanityList = profanityRepository.deleteByWordAndEntryType(stringToDelete, EntryType.BLACKLIST);

        if (deletedProfanityList.isEmpty()) {
            log.error(String.format("Word '%s' does not exist on the blacklist, unable to delete", stringToDelete));
            throw new WordDoesNotExistException(String.format("Word '%s' does not exist on the blacklist, unable to delete", stringToDelete));
        }

        log.info(String.format("Successfully deleted word: '%s' from the blacklist", stringToDelete));
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

    /**
     * Checks whether a word of a provided EntryType already exists on the database
     *
     * @param profanityToCheck the profanity to check for
     *
     * @return whether the word and entrytype combination already exist on the database
     */
    private boolean isWordOfEntryTypeAlreadyExists(Profanity profanityToCheck) {
        return profanityRepository.findDistinctByWordAndEntryType(profanityToCheck.getWord(), profanityToCheck.getEntryType()) != null;
    }
}
