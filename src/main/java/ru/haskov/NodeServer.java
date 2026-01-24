package ru.haskov;

import ru.haskov.nodes.MergeNode;
import ru.haskov.nodes.Node;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class NodeServer<I, O> implements Runnable {

    private final Node<I, O> node;
    private final int listenPort;
    private final List<Integer> targetPorts;
    private volatile boolean running = true;

    public NodeServer(Node<I, O> node, int listenPort, List<Integer> targetPorts) {
        this.node = node;
        this.listenPort = listenPort;
        this.targetPorts = targetPorts;
    }

    public void stop() {
        running = false;
    }

    public Node<I, O> getNode() {
        return node;
    }


    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(listenPort)) {
            while (running) {
                Socket socket = server.accept();
                new Thread(() -> handle(socket), "node-" + listenPort).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void handle(Socket socket) {
        try (
                socket;
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            Object input = in.readObject();
            Object result = node.process((I) input);

            if (result == null) return;

            if (result instanceof List) {
                List<Object> list = (List<Object>) result;
                for (Object o : list) {
                    if (o != null) send(o);
                }
            } else {
                send(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send(Object obj) throws IOException {
        for (int port : targetPorts) {
            try (Socket s = new Socket("localhost", port);
                 ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream())) {
                out.writeObject(obj);
                out.flush();
            }
        }
    }
}

