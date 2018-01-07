package com.creatingbugs.service;

import com.creatingbugs.model.Profanity;
import com.creatingbugs.repository.ProfanityRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

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
     * @verifies return whether the supplied string contains profanity
     * @see ProfanityService#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldReturnWhetherTheSuppliedStringContainsProfanity() throws Exception {
        //given
        Set<Profanity> profanities = profanityRepository.getAllProfanity();
        String stringToCheck = "foo";
        assertTrue(profanities.stream().anyMatch(profanity -> stringToCheck.contains(profanity.getWord())));

        //when
        boolean outcome = profanityService.isStringContainingProfanity(stringToCheck);

        //then
        assertTrue(outcome);
    }
}
