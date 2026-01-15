package ru.haskov;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.haskov.dto.ChannelDto;
import ru.haskov.dto.NodeDto;
import ru.haskov.dto.PipelineSchemaDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class PipelineLoader {

    public static Pipeline load(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PipelineSchemaDto schema = mapper.readValue(json, PipelineSchemaDto.class);

        Map<String, Channel<Document>> channels = new HashMap<>();
        for (ChannelDto c : schema.getChannels()) {
            channels.put(c.getId(), new Channel<>(Document.class));
        }

        List<NodeInstance> nodes = new ArrayList<>();
        for (NodeDto n : schema.getNodes()) {
            Object node = createNode(n.getType());

            List<Channel<Document>> ins = new ArrayList<>();
            for (String inId : n.getInputs()) ins.add(channels.get(inId));

            List<Channel<Document>> outs = new ArrayList<>();
            for (String outId : n.getOutputs()) outs.add(channels.get(outId));

            nodes.add(new NodeInstance(n.getId(), node, ins, outs));
        }

        return new Pipeline(nodes, channels);
    }

    private static Object createNode(String type) {
        switch (type) {
            case "NormalizeNode": return new NormalizeNode();
            case "SplitNode": return new SplitNode();
            case "MergeNode": return new MergeNode();
            default: throw new IllegalArgumentException("Unknown node type: " + type);
        }
    }
}



