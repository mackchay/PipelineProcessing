package ru.haskov.nodes;

public interface Node<I, O> {
    O process(I input);
}

