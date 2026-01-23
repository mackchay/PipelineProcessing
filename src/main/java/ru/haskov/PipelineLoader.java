package ru.haskov;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.haskov.nodes.*;

import java.util.ArrayList;
import java.util.List;


public final class PipelineLoader {

    public static List<NodeServer<?, ?>> load(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        List<NodeServer<?, ?>> servers = new ArrayList<>();

        for (JsonNode n : root.get("nodes")) {
            String type = n.get("type").asText();
            int port = n.get("listenPort").asInt();

            List<Integer> targets = new ArrayList<>();
            for (JsonNode t : n.get("targets")) {
                targets.add(t.asInt());
            }

            Node<?, ?> node = createNode(type, n);
            servers.add(new NodeServer<>(node, port, targets));
        }
        return servers;
    }

    private static Node<?, ?> createNode(String type, JsonNode n) {
        return switch (type) {
            case "NormalizeNode" -> new NormalizeNode();
            case "SplitNode" -> new SplitNode();
            case "MergeNode" -> {
                int expectedParts = n.has("expectedParts") ? n.get("expectedParts").asInt() : 2;
                yield new MergeNode(expectedParts);
            }
            case "SinkNode" -> new SinkNode();
            default -> throw new IllegalArgumentException(type);
        };
    }
}



