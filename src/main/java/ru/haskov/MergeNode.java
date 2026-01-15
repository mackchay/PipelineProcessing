package ru.haskov;

import java.util.List;

public final class MergeNode implements Node<List<Document>, Document> {
    @Override
    public Document process(List<Document> docs) {
        StringBuilder sb = new StringBuilder();
        List<String> history = docs.get(0).history();
        for (Document d : docs) sb.append(d.content()).append(" ");
        return new Document("txt", sb.toString(), history);
    }
}

