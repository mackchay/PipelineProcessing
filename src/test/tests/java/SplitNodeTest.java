package tests;

import org.junit.jupiter.api.Test;
import ru.haskov.Document;
import ru.haskov.nodes.SplitNode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SplitNodeTest {
    @Test
    void splitOneDocument() {
        SplitNode split = new SplitNode();

        Document d1 = new Document("txt", "Hello World", List.of());

        List<Document> result = split.process(d1);

        assertNotNull(result);
        assertEquals("World", result.get(1).content());
        assertTrue(result.get(0).history().contains("split"));
    }
}
