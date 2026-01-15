package ru.haskov;

import java.util.ArrayList;
import java.util.List;

public final class NodeInstance implements Runnable {

    public final String id;
    public final Object node;
    public final List<Channel<Document>> inputs;
    public final List<Channel<Document>> outputs;

    private volatile boolean running = true;

    public NodeInstance(String id, Object node,
                        List<Channel<Document>> inputs,
                        List<Channel<Document>> outputs) {
        this.id = id;
        this.node = node;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public void stop() { running = false; }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try {
            while (running) {
                if (inputs.size() == 1) {
                    Document in = inputs.get(0).receive();
                    System.out.println("[" + id + "] input: " + in.content());

                    Object result = ((Node<Document, ?>) node).process(in);
                    emit(result);

                } else {
                    List<Document> batch = new ArrayList<>();
                    for (Channel<Document> c : inputs) batch.add(c.receive());

                    Object result = ((Node<List<Document>, ?>) node).process(batch);
                    emit(result);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void emit(Object result) throws InterruptedException {
        if (result instanceof Document) {
            Document d = (Document) result;
            System.out.println("[" + id + "] output: " + d.content());
            outputs.get(0).send(d);
        } else if (result instanceof List) {
            @SuppressWarnings("unchecked")
            List<Document> list = (List<Document>) result;
            for (Document d : list) {
                System.out.println("[" + id + "] output: " + d.content());
                outputs.get(0).send(d);
            }
        }
    }
}


