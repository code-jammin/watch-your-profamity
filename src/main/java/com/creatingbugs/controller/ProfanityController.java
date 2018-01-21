package com.creatingbugs.controller;

import com.creatingbugs.service.ProfanityService;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

/**
 * A controller for the profanity endpoints.
 *
 * Created by steve on 07/01/18.
 */
@RestController
@RequestMapping("/profanity")
@Validated
public class ProfanityController {
    private static final Logger log = LoggerFactory.getLogger(ProfanityController.class);

    private ProfanityService profanityService;

    public ProfanityController(ProfanityService profanityService) {
        this.profanityService = profanityService;
    }

    /**
     * Check whether the supplied string contains any profanity
     *
     * @param stringToCheck the string to check
     * @return whether the string contains profanity
     *
     * @should check the supplied request parameter against the known profanities once
     * @should return the true when the string contains profanity
     * @should return false when the string does not contain profanity
     * @should return a 400 on empty text value
     * @should return a 400 on missing text value
     */
    @GetMapping(value = "check", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public boolean checkWordForProfanity(
            @NotBlank(message = "value for 'text' must not be blank")
            @RequestParam("text") String stringToCheck) {
        log.debug(String.format("Calling ProfanityService to check for profanity in string: %s", stringToCheck));
        return profanityService.isStringContainingProfanity(stringToCheck);
    }

    /**
     * Add a word to the blacklist.
     *
     * @param wordToBlacklist the word to add to the blacklist.
     *
     * @should add the provided word to the blacklist
     * @should return a 400 on non alphabetic words
     * @should return a 400 on words containing whitespace
     * @should return a 400 on empty word value
     */
    @PutMapping("blacklist/add/{word}")
    public void addToBlacklist(@PathVariable("word") String wordToBlacklist) {

    }
}
