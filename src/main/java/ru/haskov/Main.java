package ru.haskov;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {

        String json = Files.readString(
                Path.of(Objects.requireNonNull(Main.class.getResource("/schema.json")).toURI())
        );

        List<NodeServer<?, ?>> servers = PipelineLoader.load(json);
        servers.forEach(s -> new Thread(s).start());
        for (int i = 1; i <= 3; i++) {
            try (Socket s = new Socket("localhost", 5001);
                 ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream())) {

                out.writeObject(new Document("txt", "THis is doc " + i, List.of()));
                out.flush();
            }
        }
    }
}




