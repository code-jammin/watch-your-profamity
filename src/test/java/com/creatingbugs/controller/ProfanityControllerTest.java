package com.creatingbugs.controller;

import com.creatingbugs.service.ProfanityService;
import com.creatingbugs.service.WordAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by steve on 25/01/18.
 */
public class ProfanityControllerTest {
    ProfanityController profanityController;

    @Mock
    ProfanityService profanityService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        profanityController = new ProfanityController(profanityService);
    }

    /**
     * @verifies check the supplied request parameter against the known profanities
     * @see ProfanityController#checkWordForProfanity(String)
     */
    @Test
    public void checkWordForProfanity_shouldCheckTheSuppliedRequestParameterAgainstTheKnownProfanitiesOnce() throws Exception {
        //given
        String stringToCheck = "string";

        //when
        profanityController.checkWordForProfanity(stringToCheck);

        //then
        verify(profanityService, times(1)).isStringContainingProfanity(stringToCheck);
    }

    /**
     * @verifies return the true when the string contains profanity
     * @see ProfanityController#checkWordForProfanity(String)
     */
    @Test
    public void checkWordForProfanity_shouldReturnTheTrueWhenTheStringContainsProfanity() throws Exception {
        when(profanityService.isStringContainingProfanity(anyString())).thenReturn(true);

        assertTrue(profanityController.checkWordForProfanity("string"));
    }

    /**
     * @verifies return false when the string does not contain profanity
     * @see ProfanityController#checkWordForProfanity(String)
     */
    @Test
    public void checkWordForProfanity_shouldReturnFalseWhenTheStringDoesNotContainProfanity() throws Exception {
        when(profanityService.isStringContainingProfanity(anyString())).thenReturn(false);

        assertFalse(profanityController.checkWordForProfanity("string"));
    }

    /**
     * @verifies add the provided word to the blacklist
     * @see ProfanityController#addToBlacklist(String)
     */
    @Test
    public void addToBlacklist_shouldAddTheProvidedWordToTheBlacklist() throws Exception {
        String stringToAdd = "theword";

        doNothing().when(profanityService).addWordToBlacklist(anyString());

        profanityController.addToBlacklist(stringToAdd);

        verify(profanityService, times(1)).addWordToBlacklist(stringToAdd);
    }

    /**
     * @verifies not duplicate existing blacklisted words
     * @see ProfanityController#addToBlacklist(String)
     */
    @Test(expected = CustomConflictException.class)
    public void addToBlacklist_shouldNotDuplicateExistingBlacklistedWords() throws Exception {
        //given
        String wordAlreadyExists = "word already exists";

        doThrow(new WordAlreadyExistsException(wordAlreadyExists)).when(profanityService).addWordToBlacklist(anyString());

        //when
        profanityController.addToBlacklist(wordAlreadyExists);

        //then
        verify(profanityService, never()).addWordToBlacklist(anyString());
    }

}
