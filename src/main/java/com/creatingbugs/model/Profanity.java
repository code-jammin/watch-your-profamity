package com.creatingbugs.model;

import org.springframework.data.annotation.Id;

/**
 * A model class to represent an individual word of profanity.
 *
 * Created by steve on 07/01/18.
 */
public class Profanity {
    @Id private final String id;
    private final String word;
    private final EntryType entryType;

    public Profanity(String id, String word, EntryType entryType) {
        this.id = id;
        this.word = word;
        this.entryType = entryType;
    }

    public String getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    @Override
    public String toString() {
        return "Profanity{" +
                "id='" + id + '\'' +
                ", word='" + word + '\'' +
                ", entryType='" + entryType.name().toLowerCase() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profanity profanity = (Profanity) o;

        if (id != null ? !id.equals(profanity.id) : profanity.id != null) return false;
        if (word != null ? !word.equals(profanity.word) : profanity.word != null) return false;
        return entryType == profanity.entryType;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (entryType != null ? entryType.hashCode() : 0);
        return result;
    }
}
