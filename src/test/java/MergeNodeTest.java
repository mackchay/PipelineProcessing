package tests;

import org.junit.jupiter.api.Test;
import ru.haskov.Document;
import ru.haskov.nodes.MergeNode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MergeNodeTest {
    @Test
    void mergesTwoDocumentsWithSameGroupId() {
        MergeNode merge = new MergeNode(2);

        Document d1 = new Document("txt", "Hello", List.of("split"));
        Document d2 = new Document("txt", "World", List.of("split"));

        assertNull(merge.process(d1));

        Document result = merge.process(d2);

        assertNotNull(result);
        assertEquals("Hello World", result.content());
        assertTrue(result.history().contains("merged"));
    }
}
