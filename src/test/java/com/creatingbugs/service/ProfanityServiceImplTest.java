package com.creatingbugs.service;

import com.creatingbugs.model.EntryType;
import com.creatingbugs.model.Profanity;
import com.creatingbugs.repository.ProfanityRepository;
import com.creatingbugs.util.TestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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
        Profanity fooProfanity = new Profanity("1", FOO, EntryType.BLACKLIST);
        Profanity barProfanity = new Profanity("2", BAR, EntryType.BLACKLIST);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAllByEntryType(EntryType.BLACKLIST)).thenReturn(profanities);

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
        Profanity fooProfanity = new Profanity("1", FOO, EntryType.BLACKLIST);
        Profanity barProfanity = new Profanity("2", BAR, EntryType.BLACKLIST);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAllByEntryType(EntryType.BLACKLIST)).thenReturn(profanities);

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
        Profanity fooProfanity = new Profanity("1", FOO, EntryType.BLACKLIST);
        Profanity barProfanity = new Profanity("2", BAR, EntryType.BLACKLIST);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAllByEntryType(EntryType.BLACKLIST)).thenReturn(profanities);

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
        Profanity fooProfanity = new Profanity("1", FOO, EntryType.BLACKLIST);
        Profanity barProfanity = new Profanity("2", BAR, EntryType.BLACKLIST);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAllByEntryType(EntryType.BLACKLIST)).thenReturn(profanities);

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

        when(profanityRepository.findAllByEntryType(EntryType.BLACKLIST)).thenReturn(profanities);

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
        Profanity fooProfanity = new Profanity("1", "a string", EntryType.BLACKLIST);
        Profanity barProfanity = new Profanity("2", BAR, EntryType.BLACKLIST);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAllByEntryType(EntryType.BLACKLIST)).thenReturn(profanities);

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
        Profanity fooProfanity = new Profanity("3", FOO, EntryType.BLACKLIST);
        Profanity barProfanity = new Profanity("4", BAR, EntryType.BLACKLIST);
        profanities.add(fooProfanity);
        profanities.add(barProfanity);

        when(profanityRepository.findAllByEntryType(EntryType.BLACKLIST)).thenReturn(profanities);

        //when
        boolean outcome = profanityService.isStringContainingProfanity(FOO);

        //then
        verify(profanityRepository, times(1)).findAllByEntryType(EntryType.BLACKLIST);
    }

    /**
     * @verifies return false if a blacklisted word is on the whitelist
     * @see ProfanityServiceImpl#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldReturnFalseIfABlacklistedWordIsOnTheWhitelist() throws Exception {
        //given
        List<Profanity> blacklist = new ArrayList<>();
        List<Profanity> whitelist = new ArrayList<>();

        Profanity blacklistedBar = new Profanity("3", BAR, EntryType.BLACKLIST);
        Profanity blacklistedFoo = new Profanity("4", FOO, EntryType.BLACKLIST);

        Profanity whitelistedFoo = new Profanity("5", FOO, EntryType.WHITELIST);

        blacklist.add(blacklistedBar);
        blacklist.add(blacklistedFoo);

        whitelist.add(whitelistedFoo);

        when(profanityRepository.findAllByEntryType(EntryType.BLACKLIST)).thenReturn(blacklist);
        when(profanityRepository.findAllByEntryType(EntryType.WHITELIST)).thenReturn(whitelist);

        //when
        boolean outcome = profanityService.isStringContainingProfanity(FOO);

        //then
        assertFalse(outcome);

    }

    /**
     * @verifies return false if a non-blacklisted word is on the whitelist
     * @see ProfanityServiceImpl#isStringContainingProfanity(String)
     */
    @Test
    public void isStringContainingProfanity_shouldReturnFalseIfANonblacklistedWordIsOnTheWhitelist() throws Exception {
        //given
        List<Profanity> blacklist = new ArrayList<>();
        List<Profanity> whitelist = new ArrayList<>();

        Profanity whitelistedFoo = new Profanity("5", FOO, EntryType.WHITELIST);

        whitelist.add(whitelistedFoo);

        when(profanityRepository.findAllByEntryType(EntryType.BLACKLIST)).thenReturn(blacklist);
        when(profanityRepository.findAllByEntryType(EntryType.WHITELIST)).thenReturn(whitelist);

        //when
        boolean outcome = profanityService.isStringContainingProfanity(FOO);

        //then
        assertFalse(outcome);
    }

    /**
     * @verifies add new words to the ProfanityRepository
     * @see ProfanityServiceImpl#addWordToBlacklist(String)
     */
    @Test
    public void addWordToBlacklist_shouldAddNewWordsToTheProfanityRepository() throws Exception {
        //given
        String profanityString = "foo";

        when(profanityRepository.findDistinctByWordAndEntryType(any(), any())).thenReturn(null);

        //when
        profanityService.addWordToBlacklist(profanityString);

        ArgumentCaptor<Profanity> profanityArgumentCaptor = ArgumentCaptor.forClass(Profanity.class);

        //then
        verify(profanityRepository, times(1)).save(profanityArgumentCaptor.capture());
        assertTrue(profanityArgumentCaptor.getValue().getWord().equals(profanityString));
    }

    /**
     * @verifies check the ProfanityRepository if the word already exists
     * @see ProfanityServiceImpl#addWordToBlacklist(String)
     */
    @Test
    public void addWordToBlacklist_shouldCheckTheProfanityRepositoryIfTheWordAlreadyExists() throws Exception {
        //given
        String profanityString = "foo";

        //when
        profanityService.addWordToBlacklist(profanityString);

        ArgumentCaptor<String> profanityWordArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<EntryType> entryTypeArgumentCaptor = ArgumentCaptor.forClass(EntryType.class);

        //then
        verify(profanityRepository, times(1)).findDistinctByWordAndEntryType(profanityWordArgumentCaptor.capture(), entryTypeArgumentCaptor.capture());
        assertEquals(profanityString, profanityWordArgumentCaptor.getValue());
        assertEquals(EntryType.BLACKLIST, entryTypeArgumentCaptor.getValue());
    }

    /**
     * @verifies make no changes to the ProfanityRepository if the word already exists
     * @see ProfanityServiceImpl#addWordToBlacklist(String)
     */
    @Test
    public void addWordToBlacklist_shouldMakeNoChangesToTheProfanityRepositoryIfTheWordAlreadyExists() throws Exception {
        //given
        String profanityString = "foo";
        EntryType profanityEntryType = EntryType.BLACKLIST;

        Profanity returnedProfanity = new Profanity("1", profanityString, profanityEntryType);

        when(profanityRepository.findDistinctByWordAndEntryType(any(), any())).thenReturn(returnedProfanity);

        //when
        try {
            profanityService.addWordToBlacklist(profanityString);
        } catch (WordAlreadyExistsException e) {
            //swallow exception
        }

        //then
        verify(profanityRepository, never()).save(any(Profanity.class));
    }

    /**
     * @verifies throw WordAlreadyExistsException if word already exists
     * @see ProfanityServiceImpl#addWordToBlacklist(String)
     */
    @Test(expected = WordAlreadyExistsException.class)
    public void addWordToBlacklist_shouldThrowWordAlreadyExistsExceptionIfWordAlreadyExists() throws Exception {
        //given
        String profanityString = "foo";

        EntryType profanityEntryType = EntryType.BLACKLIST;

        Profanity returnedProfanity = new Profanity("1", profanityString, profanityEntryType);

        when(profanityRepository.findDistinctByWordAndEntryType(any(), any())).thenReturn(returnedProfanity);

        //when
        profanityService.addWordToBlacklist(profanityString);
    }

    /**
     * @verifies delete the word from the ProfanityRepository
     * @see ProfanityServiceImpl#deleteWordFromBlacklist(String)
     */
    @Test
    public void deleteWordFromBlacklist_shouldDeleteTheWordFromTheProfanityRepository() throws Exception {
        //given
        String profanityString = "foo";
        EntryType profanityEntryType = EntryType.BLACKLIST;

        Profanity profanityToDelete = new Profanity(profanityString, profanityEntryType);
        List<Profanity> deletedProfanityList = Arrays.asList(profanityToDelete);

        when(profanityRepository.deleteByWordAndEntryType(profanityString, profanityEntryType)).thenReturn(deletedProfanityList);

        //when
        profanityService.deleteWordFromBlacklist(profanityString);

        //then
        verify(profanityRepository, times(1)).deleteByWordAndEntryType(profanityString, profanityEntryType);
    }

    /**
     * @verifies throw WordDoesNotExistException if the word does not exist
     * @see ProfanityServiceImpl#deleteWordFromBlacklist(String)
     */
    @Test(expected = WordDoesNotExistException.class)
    public void deleteWordFromBlacklist_shouldThrowWordDoesNotExistExceptionIfTheWordDoesNotExist() throws Exception {
        //given
        String profanityString = "foo";
        EntryType profanityEntryType = EntryType.BLACKLIST;

        List<Profanity> emptyDeletedProfanityList = new ArrayList<>();

        when(profanityRepository.deleteByWordAndEntryType(profanityString, profanityEntryType)).thenReturn(emptyDeletedProfanityList);

        //when
        profanityService.deleteWordFromBlacklist(profanityString);

        //then
        verify(profanityRepository, times(1)).deleteByWordAndEntryType(profanityString, profanityEntryType);
    }
}
