package ru.haskov.nodes;

import ru.haskov.Document;

import java.util.*;
import java.util.stream.Collectors;

public final class MergeNode implements Node<Document, Document> {

    private final int batchSize;
    private final List<Document> buffer = new ArrayList<>();

    public MergeNode(int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public synchronized Document process(Document d) {
        buffer.add(d);

        if (buffer.size() < batchSize) {
            return null;
        }

        List<Document> batch = new ArrayList<>(buffer);
        buffer.clear();

        String content = batch.stream()
                .map(Document::content)
                .collect(Collectors.joining(" "));

        List<String> history = new ArrayList<>();
        batch.forEach(doc -> history.addAll(doc.history()));
        history.add("merged");

        return new Document(
                d.type(),
                content,
                history
        );
    }
}





