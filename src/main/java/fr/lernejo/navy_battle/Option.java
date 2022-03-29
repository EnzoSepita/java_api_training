package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class Option<T> {
    private final List<T> list = new ArrayList<>();

    public Option() { }

    public Option(T obj) {
        set(obj);
    }

    public void set(T obj) {
        list.clear();
        list.add(obj);
    }

    public boolean isEmpty() {
        return  list.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public T get() {
        if(list.isEmpty())
            throw new RuntimeException("Option is empty!");

        return list.get(0);
    }

    public void unset() {
        list.clear();
    }
}