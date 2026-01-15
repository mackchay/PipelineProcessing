package ru.haskov;

import java.util.*;

public final class Document {
    private final String format;
    private final String content;
    private final List<String> history;

    public Document(String format, String content, List<String> history) {
        this.format = format;
        this.content = content;
        this.history = List.copyOf(history);
    }

    public Document withStep(String step, String newContent) {
        List<String> h = new ArrayList<>(history);
        h.add(step);
        return new Document(format, newContent, h);
    }

    public String format() { return format; }
    public String content() { return content; }
    public List<String> history() { return history; }
}
