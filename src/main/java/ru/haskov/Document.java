package ru.haskov;

import java.io.Serializable;
import java.util.*;

public record Document(String type, String content, List<String> history) implements Serializable {
    public Document(String type, String content, List<String> history) {
        this.type = type;
        this.content = content;
        this.history = new ArrayList<>(history);
    }

    public Document withHistory(String step) {
        List<String> newHistory = new ArrayList<>(history);
        newHistory.add(step);
        return new Document(type, content, newHistory);
    }
}


