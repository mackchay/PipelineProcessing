package ru.haskov;

import java.util.ArrayList;
import java.util.List;

public final class SplitNode implements Node<Document, List<Document>> {
    @Override
    public List<Document> process(Document doc) {
        List<Document> out = new ArrayList<>();
        for (String part : doc.content().split("\\.")) {
            out.add(doc.withStep("split", part));
        }
        return out;
    }
}




