package ru.haskov;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class Channel<T> {

    private final Class<T> type;
    private final BlockingQueue<T> queue = new LinkedBlockingQueue<>();

    public Channel(Class<T> type) { this.type = type; }

    public Class<T> type() { return type; }

    public void send(T value) throws InterruptedException { queue.put(value); }

    public T receive() throws InterruptedException { return queue.take(); }

    public boolean hasData() { return !queue.isEmpty(); }
}



