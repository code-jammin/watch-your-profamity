package com.creatingbugs.service;

import com.creatingbugs.model.Profanity;
import com.creatingbugs.repository.ProfanityRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 07/01/18.
 */
public class ProfanityServiceImplTest {
    private static final String FOO = "foo";
    private static final String BAR = "bar";

    @Mock
    private ProfanityRepository profanityRepository;

    private ProfanityService profanityService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        profanityService = new ProfanityServiceImpl(profanityRepository);
    }

    /**
     * @verifies return true if stringToCheck exactly matches an element of profanity
     * @see ProfanityServiceImpl#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldReturnTrueIfStringToCheckExactlyMatchesAnElementOfProfanity() throws Exception {
        //given
        List<Profanity> profanities = new ArrayList<>();
        Profanity fooProfanity = new Profanity("1", FOO);
        Profanity barProfanity = new Profanity("2", BAR);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAll()).thenReturn(profanities);

        //when
        boolean outcome = profanityService.isStringContainingProfanity(FOO);

        //then
        assertTrue(outcome);
    }

    /**
     * @verifies return true if profanity is contained as a substring of stringToCheck
     * @see ProfanityServiceImpl#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldReturnTrueIfProfanityIsContainedAsASubstringOfStringToCheck() throws Exception {
        //given
        List<Profanity> profanities = new ArrayList<>();
        Profanity fooProfanity = new Profanity("1", FOO);
        Profanity barProfanity = new Profanity("2", BAR);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAll()).thenReturn(profanities);

        //when
        boolean outcome = profanityService.isStringContainingProfanity("something" + FOO + "something");

        //then
        assertTrue(outcome);
    }

    /**
     * @verifies return false if stringToCheck does not exactly match an element of profanity
     * @see ProfanityServiceImpl#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldReturnFalseIfStringToCheckDoesNotExactlyMatchAnElementOfProfanity() throws Exception {
        //given
        List<Profanity> profanities = new ArrayList<>();
        Profanity fooProfanity = new Profanity("1", FOO);
        Profanity barProfanity = new Profanity("2", BAR);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAll()).thenReturn(profanities);

        //when
        boolean outcome = profanityService.isStringContainingProfanity("something");

        //then
        assertFalse(outcome);
    }

    /**
     * @verifies return false if stringToCheck is not contained as a substring of stringToCheck
     * @see ProfanityServiceImpl#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldReturnFalseIfStringToCheckIsNotContainedAsASubstringOfStringToCheck() throws Exception {
        //given
        List<Profanity> profanities = new ArrayList<>();
        Profanity fooProfanity = new Profanity("1", FOO);
        Profanity barProfanity = new Profanity("2", BAR);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAll()).thenReturn(profanities);

        //when
        boolean outcome = profanityService.isStringContainingProfanity("something with something else");

        //then
        assertFalse(outcome);

    }

    /**
     * @verifies return false if set of profanity is empty
     * @see ProfanityServiceImpl#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldReturnFalseIfSetOfProfanityIsEmpty() throws Exception {
        //given
        List<Profanity> profanities = new ArrayList<>();

        when(profanityRepository.findAll()).thenReturn(profanities);

        //when
        boolean outcome = profanityService.isStringContainingProfanity("something");

        //then
        assertFalse(outcome);
    }

    /**
     * @verifies be case insensitive TODO tricky words eg. 'shittake'  TODO workaround tricks eg. 'sh1t'
     * @see ProfanityServiceImpl#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldBeCaseInsensitive() throws Exception {
        //given
        List<Profanity> profanities = new ArrayList<>();
        Profanity fooProfanity = new Profanity("1", "a string");
        Profanity barProfanity = new Profanity("2", BAR);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAll()).thenReturn(profanities);

        //when
        boolean outcome = profanityService.isStringContainingProfanity("A STRING");

        //then
        assertTrue(outcome);
    }

    /**
     * @verifies only call the database once
     * @see ProfanityServiceImpl#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldOnlyCallTheDatabaseOnce() throws Exception {
        //given
        List<Profanity> profanities = new ArrayList<>();
        Profanity fooProfanity = new Profanity("3", FOO);
        Profanity barProfanity = new Profanity("4", BAR);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAll()).thenReturn(profanities);

        //when
        boolean outcome = profanityService.isStringContainingProfanity(FOO);

        //then
        verify(profanityRepository, times(1)).findAll();
    }
}
