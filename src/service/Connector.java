package service;

import service.database.Data;
import java.util.HashMap;

public interface Connector {
    public Data readAll();

    public Boolean writeAll(HashMap<String, String> dictionary);
}