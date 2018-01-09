package com.creatingbugs.bootstrap;

import com.creatingbugs.model.Profanity;
import com.creatingbugs.repository.ProfanityRepository;
import com.creatingbugs.repository.ProfanityRepositoryStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 08/01/18.
 */
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(ProfanityRepositoryStub.class);

    private ProfanityRepository profanityRepository;

    public DevBootstrap(ProfanityRepository profanityRepository) {
        this.profanityRepository = profanityRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("Running bootstrap code to initialize data. Resetting database to clean state.");
        profanityRepository.deleteAll();
        profanityRepository.save(getProfanity());
        logAllProfanity();
    }

    private List<Profanity> getProfanity() {
        List<Profanity> profanityList = new ArrayList<>();

        Profanity foo = new Profanity("foo");
        Profanity bar = new Profanity("bar");
        Profanity shit = new Profanity("shit");
        Profanity cock = new Profanity("cock");

        profanityList.add(foo);
        profanityList.add(bar);
        profanityList.add(shit);
        profanityList.add(cock);

        return profanityList;
    }

    private void logAllProfanity() {
        log.debug("Fetching all profanity");
        List<Profanity> profanityList = profanityRepository.findAll();
        log.debug("Printing all profanity");
        log.debug("----------------------");
        for(Profanity profanity : profanityList) {
            log.debug(profanity.toString());
        }
    }

}
