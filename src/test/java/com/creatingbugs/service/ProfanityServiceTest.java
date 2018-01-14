package com.creatingbugs.service;

import com.creatingbugs.model.EntryType;
import com.creatingbugs.model.Profanity;
import com.creatingbugs.repository.ProfanityRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
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
}
