package com.creatingbugs.repository;

import com.creatingbugs.model.Profanity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * A quick and dirty implementation of the profanity repository for prototype purposes.
 *
 * Created by steve on 07/01/18.
 */
@Component
public class ProfanityRepositoryStub implements ProfanityRepository {
    private static final Logger log = LoggerFactory.getLogger(ProfanityRepositoryStub.class);

    private Set<Profanity> profanities = new HashSet<>();

    public ProfanityRepositoryStub() {
        log.debug("Building stub list of profanities");

        Profanity foo = new Profanity(1L, "foo");
        Profanity bar = new Profanity(2L, "bar");
        Profanity shit = new Profanity(3L, "shit");

        profanities.add(foo);
        profanities.add(bar);
    }

    public Set<Profanity> getAllProfanity() {
        log.debug("Returning all profanities");

        return profanities;
    }
}
