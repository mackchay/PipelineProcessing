import org.junit.jupiter.api.Test;
import ru.haskov.NodeServer;
import ru.haskov.PipelineLoader;
import ru.haskov.nodes.MergeNode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PipelineLoaderTest {
    @Test
    void loadPipelineFromJson() throws Exception {
        String json = """
        {
          "dataType": "Document",
          "nodes": [
            {
              "id": "normalize",
              "type": "NormalizeNode",
              "listenPort": 5001,
              "targets": [5002]
            },
            {
              "id": "merge",
              "type": "MergeNode",
              "listenPort": 5002,
              "targets": [5003],
              "expectedParts": 2
            }
          ]
        }
        """;

        List<NodeServer<?, ?>> servers = PipelineLoader.load(json);

        assertEquals(2, servers.size());
        assertTrue(servers.get(1).getNode() instanceof MergeNode);
    }
}
