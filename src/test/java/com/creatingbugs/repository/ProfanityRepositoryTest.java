package com.creatingbugs.repository;

import com.creatingbugs.model.Profanity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

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
     * @see ProfanityRepository#getAllProfanity()
     */
    @Test
    public void getAllProfanity_shouldReturnASetOfProfanityObjectsStoredInTheRepository() throws Exception {
        Set<Profanity> profanitySet = profanityRepository.getAllProfanity();
        Assert.assertTrue(!profanitySet.isEmpty());
    }
}
