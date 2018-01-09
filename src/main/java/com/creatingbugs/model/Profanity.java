package com.creatingbugs.model;

import org.springframework.data.annotation.Id;

/**
 * A model class to represent an individual word of profanity.
 *
 * Created by steve on 07/01/18.
 */
public class Profanity {
    @Id private String id;
    private String word;

    public Profanity() {
        
    }

    public Profanity(String word) {
        this.word = word;
    }

    public Profanity(String id, String word) {
        this.id = id;
        this.word = word;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Profanity{" +
                "id='" + id + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
