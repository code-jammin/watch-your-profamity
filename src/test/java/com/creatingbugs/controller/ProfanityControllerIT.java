package com.creatingbugs.controller;

import com.creatingbugs.service.ProfanityService;
import com.creatingbugs.service.WordAlreadyExistsException;
import com.creatingbugs.util.TestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.creatingbugs.util.TestUtil.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by steve on 07/01/18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfanityControllerIT {

    ProfanityController profanityController;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ProfanityService profanityService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        profanityController = new ProfanityController(profanityService);
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
     * @verifies return a 204 on successfully adding a word
     * @see ProfanityController#addToBlacklist(String)
     */
    @Test
    @DirtiesContext
    public void addToBlacklist_shouldReturnA204OnAddingWord() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String wordToAdd = TestUtil.generateRandomAlphabeticStringOfLength(20);

        mockMvc.perform(put("/profanity/blacklist/add/" + wordToAdd))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
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
                .andExpect(content().json("{\n" +
                        "    \"error\": [\n" +
                        "        \"must match \\\"^[a-zA-Z]+$\\\"\"\n" +
                        "    ]\n" +
                        "}"));
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
                .andExpect(content().json("{\n" +
                        "    \"error\": [\n" +
                        "        \"must match \\\"^[a-zA-Z]+$\\\"\"\n" +
                        "    ]\n" +
                        "}"));
    }

}
