package com.creatingbugs.controller;

import com.creatingbugs.service.ProfanityService;
import com.creatingbugs.service.WordAlreadyExistsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@Api(value = "profanity", description = "Operations pertaining to checking profanity and managing the profanity lists")
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
    @ApiOperation(value = "Check whether the supplied string contains any profanity")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully checked whether string contains profanity"),
            @ApiResponse(code = 400, message = "Empty or missing textvalue")
    })
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
     */
    @PutMapping(value = "blacklist/add/{word}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiOperation(value = "Add the provided word to the blacklist.", notes = "The word must be alphabetic.", code = 204, response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully added word to blacklist"),
            @ApiResponse(code = 400, message = "Word does not match required format"),
            @ApiResponse(code = 409, message = "Word already exists on the blacklist"),
            })
    public ResponseEntity addToBlacklist(
            @NotBlank(message = "path variable for 'word' must not be blank")
            @Pattern(regexp = "^[a-zA-Z]+$")
            @PathVariable("word") String wordToBlacklist) {
        try {
            profanityService.addWordToBlacklist(wordToBlacklist);
        } catch (WordAlreadyExistsException e) {
            throw new CustomConflictException(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
