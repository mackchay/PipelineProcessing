import org.junit.jupiter.api.Test;
import ru.haskov.Document;
import ru.haskov.nodes.NormalizeNode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NormalizeNodeTest {
    @Test
    void normalizeOneDocument() {
        NormalizeNode norm = new NormalizeNode();

        Document d1 = new Document("txt", "Hello WORLD", List.of());

        Document result = norm.process(d1);

        assertNotNull(result);
        assertEquals("hello world", result.content());
        assertTrue(result.history().contains("normalize"));
    }
}
