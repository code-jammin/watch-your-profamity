package com.creatingbugs.model;

/**
 * A model class to represent an individual word of profanity.
 *
 * Created by steve on 07/01/18.
 */
public class Profanity {
    private Long id;
    private String word;

    public Profanity(Long id) {
        this.id = id;
    }

    public Profanity(Long id, String word) {
        this.id = id;
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
