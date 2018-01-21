package com.creatingbugs.controller;

import com.creatingbugs.service.ProfanityService;
import com.creatingbugs.service.WordAlreadyExistsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.creatingbugs.util.TestUtil.APPLICATION_JSON_UTF8;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by steve on 07/01/18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfanityControllerTest {

    ProfanityController profanityController;

    @Autowired
    WebApplicationContext webApplicationContext;

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
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(profanityController).build();

        when(profanityService.isStringContainingProfanity(anyString())).thenReturn(true);

        mockMvc.perform(get("/profanity/check?text=word"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().string("true"));
    }

    /**
     * @verifies return false when the string does not contain profanity
     * @see ProfanityController#checkWordForProfanity(String)
     */
    @Test
    public void checkWordForProfanity_shouldReturnFalseWhenTheStringDoesNotContainProfanity() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(profanityController).build();

        when(profanityService.isStringContainingProfanity(anyString())).thenReturn(false);

        mockMvc.perform(get("/profanity/check?text=word"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().string("false"));
    }

    /**
     * @verifies return a 400 on empty text value
     * @see ProfanityController#checkWordForProfanity(String)
     */
    @Test
    public void checkWordForProfanity_shouldReturnA400OnEmptyTextValue() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/profanity/check?text="))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\"error\":[\"value for 'text' must not be blank\"]}"));
    }

    /**
     * @verifies return a 400 on missing text value
     * @see ProfanityController#checkWordForProfanity(String)
     */
    @Test
    public void checkWordForProfanity_shouldReturnA400OnMissingTextValue() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/profanity/check?text="))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\"error\":[\"value for 'text' must not be blank\"]}"));
    }

    /**
     * @verifies add the provided word to the blacklist
     * @see ProfanityController#addToBlacklist(String)
     */
    @Test
    public void addToBlacklist_shouldAddTheProvidedWordToTheBlacklist() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(profanityController).build();

        doNothing().when(profanityService).addWordToBlacklist(anyString());

        mockMvc.perform(put("/profanity/blacklist/add/word"))
                .andExpect(status().is(204))
                .andExpect(content().string(""));

        verify(profanityService, times(1)).addWordToBlacklist("word");
    }

    /**
     * @verifies not duplicate existing blacklisted words
     * @see ProfanityController#addToBlacklist(String)
     */
    @Test
    public void addToBlacklist_shouldNotDuplicateExistingBlacklistedWords() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(profanityController).build();

        String wordAlreadyExists = "word already exists";

        doThrow(new WordAlreadyExistsException(wordAlreadyExists)).when(profanityService).addWordToBlacklist(anyString());

        mockMvc.perform(put("/profanity/blacklist/add/word"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(wordAlreadyExists));
    }

    /**
     * @verifies return a 400 on non alphabetic words
     * @see ProfanityController#addToBlacklist(String)
     */
    @Test
    public void addToBlacklist_shouldReturnA400OnNonAlphabeticWords() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(put("/profanity/blacklist/add/123"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\"error\":[\"value for 'word' must match regex: someregex\"]}"));
    }

    /**
     * @verifies return a 400 on words containing whitespace
     * @see ProfanityController#addToBlacklist(String)
     */
    @Test
    public void addToBlacklist_shouldReturnA400OnWordsContainingWhitespace() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(put("/profanity/blacklist/add/asdf fdsa"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\"error\":[\"value for 'word' must match regex: someregex\"]}"));
    }

    /**
     * @verifies return a 400 on empty word value
     * @see ProfanityController#addToBlacklist(String)
     */
    @Test
    public void addToBlacklist_shouldReturnA400OnEmptyWordValue() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(put("/profanity/blacklist/add/"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{\"error\":[\"value for 'word' must match regex: someregex\"]}"));
    }

}
