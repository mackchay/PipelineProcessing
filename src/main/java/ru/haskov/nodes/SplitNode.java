package ru.haskov.nodes;

import ru.haskov.Document;

import java.util.ArrayList;
import java.util.List;

public final class SplitNode implements Node<Document, List<Document>> {
    @Override
    public List<Document> process(Document d) {
        //System.out.println("splitnode");
        String[] parts = d.content().split("\\s+");
        List<Document> out = new ArrayList<>();
        for (String p : parts) {
            out.add(new Document(
                    d.type(),
                    p,
                    d.history()
            ).withHistory("split"));
        }
        return out;
    }
}





