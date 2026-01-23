package ru.haskov.nodes;

import ru.haskov.Document;

public final class SinkNode implements Node<Document, Void> {
    @Override
    public Void process(Document d) {
        System.out.println("RESULT: " + d.content());
        System.out.println("HISTORY: " + d.history());
        return null;
    }
}

