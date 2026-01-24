package ru.haskov.nodes;

import ru.haskov.Document;

public final class NormalizeNode implements Node<Document, Document> {
    @Override
    public Document process(Document d) {
        return new Document(
                d.type(),
                d.content().trim().toLowerCase(),
                d.history()
        ).withHistory("normalize");
    }
}


