package net.benfro.callcenter.util;


import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FIFOQueue<T> {

    private final LinkedList<T> backend = Lists.newLinkedList();
    private final int capacity;

    public FIFOQueue(int capacity) {
        this.capacity = capacity;
    }

    public boolean enqueue(T item) {
        synchronized (backend) {
            if (backend.size() < capacity) {
                return backend.add(item);
            } else {
                return false;
            }
        }
    }

    public Optional<T> dequeue() {
        synchronized (backend) {
            if (backend.isEmpty()) {
                return Optional.empty();
            }
            Optional<T> item = Optional.ofNullable(backend.getFirst());
            item.ifPresent(backend::remove);
            return item;
        }
    }

    public int size() {
        return backend.size();
    }

    public void remove(T item) {
        this.backend.remove(item);
    }

    public List<T> getBackingCollection() {
        return backend;
    }

    public Optional<T> peek() {
        synchronized (backend) {
            if (backend.isEmpty()) {
                return Optional.empty();
            }
            return Optional.ofNullable(backend.getFirst());
        }
    }
}
