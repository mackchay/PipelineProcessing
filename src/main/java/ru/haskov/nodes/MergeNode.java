package ru.haskov.nodes;

import ru.haskov.Document;

import java.util.*;
import java.util.stream.Collectors;

public final class MergeNode implements Node<Document, Document> {

    private final int expectedParts;
    private final Map<Long, List<Document>> buffer = new HashMap<>();

    public MergeNode(int expectedParts) {
        this.expectedParts = expectedParts;
    }

    @Override
    public synchronized Document process(Document d) {
        long groupId = d.mergeGroupId();

        buffer.computeIfAbsent(groupId, k -> new ArrayList<>()).add(d);

        List<Document> group = buffer.get(groupId);

        if (group.size() < expectedParts) {
            System.out.println("MergeNode waiting for group " + groupId + " (" + group.size() + "/" + expectedParts + ")");
            return null;
        }
        buffer.remove(groupId);

        String mergedContent = group.stream()
                .map(Document::content)
                .collect(Collectors.joining(" "));

        List<String> mergedHistory = new ArrayList<>();
        group.forEach(doc -> mergedHistory.addAll(doc.history()));
        mergedHistory.add("merged");

        Document mergedDoc = new Document(
                group.get(0).sequenceId(),
                groupId,
                group.get(0).type(),
                mergedContent,
                mergedHistory
        );

        //System.out.println("MergeNode completed merge for group " + groupId + ": " + mergedContent);

        return mergedDoc;
    }
}





