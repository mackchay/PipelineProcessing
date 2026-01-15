package ru.haskov;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public final class Main {

    public static void main(String[] args) throws Exception {

        String json = readResource();
        Pipeline pipeline = PipelineLoader.load(json);

        Channel<Document> input = pipeline.getChannel("c1");
        Channel<Document> output = pipeline.getChannel("c3");

        pipeline.start();

        input.send(new Document("txt","Doc ONE. Hello", List.of()));
        input.send(new Document("txt","Doc TWO. World", List.of()));
        input.send(new Document("txt","Doc THREE. Pipeline", List.of()));

        Thread.sleep(2000);
        pipeline.stop();

        System.out.println("\nFinal results:");
        while (output.hasData()) {
            Document d = output.receive();
            System.out.println(" - " + d.content());
            System.out.println("   history: " + d.history());
        }
    }

    private static String readResource() throws Exception {
        URI uri = Objects.requireNonNull(Main.class.getClassLoader().getResource("schema.json")).toURI();
        return Files.readString(Path.of(uri));
    }
}



