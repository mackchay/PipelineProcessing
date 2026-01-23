package ru.haskov;

import java.io.Serializable;
import java.util.*;

public final class Document implements Serializable {
    private final long sequenceId;
    private final long mergeGroupId;
    private final String type;
    private final String content;
    private final List<String> history;

    public Document(long sequenceId, long mergeGroupId, String type, String content, List<String> history) {
        this.sequenceId = sequenceId;
        this.mergeGroupId = mergeGroupId;
        this.type = type;
        this.content = content;
        this.history = new ArrayList<>(history);
    }

    public long sequenceId() { return sequenceId; }
    public long mergeGroupId() { return mergeGroupId; }
    public String type() { return type; }
    public String content() { return content; }
    public List<String> history() { return history; }

    public Document withHistory(String step) {
        List<String> newHistory = new ArrayList<>(history);
        newHistory.add(step);
        return new Document(sequenceId, mergeGroupId, type, content, newHistory);
    }
}


