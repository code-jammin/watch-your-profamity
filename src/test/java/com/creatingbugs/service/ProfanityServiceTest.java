package com.creatingbugs.service;

import com.creatingbugs.model.EntryType;
import com.creatingbugs.model.Profanity;
import com.creatingbugs.repository.ProfanityRepository;
import com.creatingbugs.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by steve on 07/01/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfanityServiceTest {
    @Autowired
    ProfanityService profanityService;

    @Autowired
    ProfanityRepository profanityRepository;

    /**
     * @verifies return blacklisted items as profanity
     * @see ProfanityService#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldReturnBlacklistedItemsAsProfanity() throws Exception {
        //given
        String stringToCheck = "badword";
        Profanity profanity = new Profanity("1", stringToCheck, EntryType.BLACKLIST);
        profanityRepository.save(profanity);

        //when
        boolean outcome = profanityService.isStringContainingProfanity(stringToCheck);

        //then
        assertTrue(outcome);
    }

    /**
     * @verifies return whitelisted items to not be marked as profanity
     * @see ProfanityService#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldReturnWhitelistedItemsToNotBeMarkedAsProfanity() throws Exception {
        //given
        String stringToCheck = "shit";

        Profanity blacklisted = new Profanity("test1", stringToCheck, EntryType.BLACKLIST);
        profanityRepository.save(blacklisted);

        Profanity whitelisted = new Profanity("test2", stringToCheck, EntryType.WHITELIST);
        profanityRepository.save(whitelisted);

        //when
        boolean outcome = profanityService.isStringContainingProfanity(stringToCheck);

        //then
        assertFalse(outcome);
    }

    /**
     * @verifies add the provided word to the ProfanityRepository as a blacklisted word
     * @see ProfanityService#addWordToBlacklist(String)
     */
    @Test
    public void addWordToBlacklist_shouldAddTheProvidedWordToTheProfanityRepositoryAsABlacklistedWord() throws Exception {
        //given
        String stringToBlacklist = TestUtil.generateRandomAlphabeticStringOfLength(12);
        EntryType blacklist = EntryType.BLACKLIST;

        while (isProfanityExistOnRepository(stringToBlacklist, blacklist)) {
            stringToBlacklist = TestUtil.generateRandomAlphabeticStringOfLength(12);
        }

        //when
        profanityService.addWordToBlacklist(stringToBlacklist);

        //then
        assertTrue(isProfanityExistOnRepository(stringToBlacklist, blacklist));
    }

    /**
     * @verifies not duplicate existing words on the database
     * @see ProfanityService#addWordToBlacklist(String)
     */
    @Test
    public void addWordToBlacklist_shouldNotDuplicateExistingWordsOnTheDatabase() throws Exception{
        //given
        String stringToBlacklist = TestUtil.generateRandomAlphabeticStringOfLength(12);
        Profanity profanityToBeBlacklisted = new Profanity("1", stringToBlacklist, EntryType.BLACKLIST);

        profanityRepository.save(profanityToBeBlacklisted);

        //when
        try {
            profanityService.addWordToBlacklist(stringToBlacklist);
        } catch (WordAlreadyExistsException e) {
            //swallow exception
        }

        //then
        List<Profanity> matchingProfanity = profanityRepository.findAllByWordAndEntryType(profanityToBeBlacklisted.getWord(), profanityToBeBlacklisted.getEntryType());
        assertThat(matchingProfanity, hasSize(1));
    }

    /**
     * @verifies throw WordAlreadyExistsException if word already exists
     * @see ProfanityService#addWordToBlacklist(String)
     */
    @Test(expected = WordAlreadyExistsException.class)
    public void addWordToBlacklist_shouldThrowWordAlreadyExistsExceptionIfWordAlreadyExists() throws Exception {
        //given
        String stringToBlacklist = TestUtil.generateRandomAlphabeticStringOfLength(12);
        Profanity profanityToBeBlacklisted = new Profanity("1", stringToBlacklist, EntryType.BLACKLIST);

        profanityRepository.save(profanityToBeBlacklisted);

        //when
        profanityService.addWordToBlacklist(stringToBlacklist);
    }

    /**
     * Convenience method to check whether the Profanity already exists on the database.
     */
    private boolean isProfanityExistOnRepository(String wordToCheck, EntryType entryType) {
        Profanity foundProfanity = profanityRepository.findDistinctByWordAndEntryType(wordToCheck, entryType);
        return foundProfanity != null;
    }
}
