package com.creatingbugs.controller;

import com.creatingbugs.service.ProfanityService;
import com.creatingbugs.util.TestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.creatingbugs.util.TestUtil.APPLICATION_JSON_UTF8;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by steve on 07/01/18.
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
}
