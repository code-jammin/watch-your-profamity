package com.creatingbugs.repository;

import com.creatingbugs.model.EntryType;
import com.creatingbugs.model.Profanity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by steve on 07/01/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfanityRepositoryTest {

    @Autowired
    private ProfanityRepository profanityRepository;

    /**
     * @verifies return a set of profanity objects stored in the repository
     * @see ProfanityRepository#findAll()
     */
    @Test
    @DirtiesContext
    public void findAll_shouldReturnASetOfProfanityObjectsStoredInTheRepository() throws Exception {
        //given
        Profanity testProfanity = new Profanity("1", "blacklist", EntryType.BLACKLIST);
        profanityRepository.save(testProfanity);

        //when
        List<Profanity> profanitySet = profanityRepository.findAll();

        //then
        Assert.assertTrue(!profanitySet.isEmpty());
    }

    /**
     * @verifies only return the specified EntryType of word
     * @see ProfanityRepository#findAllByEntryType(com.creatingbugs.model.EntryType)
     */
    @Test
    @DirtiesContext
    public void findAllByEntryType_shouldOnlyReturnTheSpecifiedEntryTypeOfWord() throws Exception {
        //given
        Profanity testProfanity = new Profanity("1", "blacklist", EntryType.BLACKLIST);
        profanityRepository.save(testProfanity);

        //when
        List<Profanity> profanityList = profanityRepository.findAllByEntryType(EntryType.BLACKLIST);

        //then
        assertTrue(profanityList.stream()
                .allMatch(profanity -> profanity.getEntryType().equals(EntryType.BLACKLIST)));
    }

    /**
     * @verifies return all words of the specified EntryType
     * @see ProfanityRepository#findAllByEntryType(com.creatingbugs.model.EntryType)
     */
    @Test
    @DirtiesContext
    public void findAllByEntryType_shouldReturnAllWordsOfTheSpecifiedEntryType() throws Exception {
        //given
        Profanity testProfanity = new Profanity("1", "blacklist", EntryType.BLACKLIST);
        profanityRepository.save(testProfanity);

        List<Profanity> allProfanity = profanityRepository.findAll();

        List<Profanity> blacklistedProfanity = allProfanity.stream()
                .filter(profanity -> profanity.getEntryType().equals(EntryType.BLACKLIST))
                .collect(Collectors.toList());

        //when
        List<Profanity> profanityList = profanityRepository.findAllByEntryType(EntryType.BLACKLIST);

        //then
        assertTrue(blacklistedProfanity.containsAll(profanityList));
    }

    /**
     * @verifies return a Profanity object matching the word of the specified type
     * @see ProfanityRepository#findDistinctByWordAndEntryType(String, EntryType)
     */
    @Test
    @DirtiesContext
    public void findDistinctByWordAndEntryType_shouldReturnAProfanityObjectMatchingTheWordOfTheSpecifiedType() throws Exception {
        //given
        String profanityWord = "blacklist";
        EntryType entryTypeOfWord = EntryType.BLACKLIST;

        Profanity testProfanity = new Profanity("1", profanityWord, entryTypeOfWord);
        profanityRepository.save(testProfanity);

        Profanity whitelistedProfanity = new Profanity("2", profanityWord, EntryType.WHITELIST);

        //when
        Profanity foundProfanity = profanityRepository.findDistinctByWordAndEntryType(profanityWord, entryTypeOfWord);

        //then
        assertTrue(foundProfanity.getWord().equals(profanityWord));
        assertTrue(foundProfanity.getEntryType().equals(entryTypeOfWord));
    }
}
