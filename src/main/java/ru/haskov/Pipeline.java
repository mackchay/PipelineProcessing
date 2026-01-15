package ru.haskov;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Pipeline {

    private final List<NodeInstance> nodes;
    private final Map<String, Channel<Document>> channels;
    private final List<Thread> threads = new ArrayList<>();

    public Pipeline(List<NodeInstance> nodes,
                    Map<String, Channel<Document>> channels) {
        this.nodes = nodes;
        this.channels = channels;
    }

    public Channel<Document> getChannel(String id) { return channels.get(id); }

    public void start() {
        for (NodeInstance n : nodes) {
            Thread t = new Thread(n, "node-" + n.id);
            threads.add(t);
            t.start();
        }
    }

    public void stop() {
        for (NodeInstance n : nodes) n.stop();
        for (Thread t : threads) t.interrupt();
    }
}


