package com.creatingbugs.repository;

import com.creatingbugs.model.EntryType;
import com.creatingbugs.model.Profanity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * A quick and dirty implementation of the profanity repository for prototype purposes.
 *
 * Created by steve on 07/01/18.
 */
@Component
public class ProfanityRepositoryStub {
    private static final Logger log = LoggerFactory.getLogger(ProfanityRepositoryStub.class);

    private List<Profanity> profanities = new ArrayList<>();

    public ProfanityRepositoryStub() {
        log.debug("Building stub list of profanities");

        Profanity foo = new Profanity("1", "foo", EntryType.BLACKLIST);
        Profanity bar = new Profanity("2", "bar", EntryType.BLACKLIST);
        Profanity shit = new Profanity("3", "shit", EntryType.BLACKLIST);

        profanities.add(foo);
        profanities.add(bar);
        profanities.add(shit);
    }

    public List<Profanity> findAll() {
        log.debug("Returning all profanities");

        return profanities;
    }
}
