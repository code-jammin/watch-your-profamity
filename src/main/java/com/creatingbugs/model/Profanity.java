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
    private EntryType entryType;

    public Profanity() {
    }

    public Profanity(String id, String word, EntryType entryType) {
        this.id = id;
        this.word = word;
        this.entryType = entryType;
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

    public EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    @Override
    public String toString() {
        return "Profanity{" +
                "id='" + id + '\'' +
                ", word='" + word + '\'' +
                ", entryType='" + entryType.name().toLowerCase() + '\'' +
                '}';
    }
}
