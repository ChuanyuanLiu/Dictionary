package service;

import javafx.util.Pair;

import java.util.HashMap;

public interface Parser {
    public Pair<Boolean, HashMap<String, String>> readAll();

    public Boolean writeAll(HashMap<String, String> dictionary);
}