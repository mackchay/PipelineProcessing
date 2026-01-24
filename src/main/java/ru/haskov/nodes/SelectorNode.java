package ru.haskov.nodes;

import ru.haskov.Document;

import java.util.Comparator;
import java.util.List;

public final class SelectorNode implements Node<List<Document>, Document> {

    @Override
    public Document process(List<Document> inputs) {
        return inputs.stream()
                .min(Comparator.comparingInt(d -> d.history().size()))
                .orElseThrow();
    }
}
