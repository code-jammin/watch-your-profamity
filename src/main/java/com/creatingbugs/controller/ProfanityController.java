package com.creatingbugs.controller;

import com.creatingbugs.service.ProfanityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * A controller for the profanity endpoints.
 *
 * Created by steve on 07/01/18.
 */
@RestController
@RequestMapping("/profanity")
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
     */
    @GetMapping("check")
    public boolean checkWordForProfanity(@RequestParam("text") String stringToCheck) {
        log.debug(String.format("Calling ProfanityService to check for profanity in string: %s", stringToCheck));
        return profanityService.isStringContainingProfanity(stringToCheck);
    }
}
