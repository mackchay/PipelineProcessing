package ru.haskov;

public final class NormalizeNode implements Node<Document, Document> {
    @Override
    public Document process(Document doc) {
        return doc.withStep("normalize", doc.content().trim());
    }
}

