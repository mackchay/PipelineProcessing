package ru.haskov;

public interface Node<I, O> {
    O process(I input);
}

