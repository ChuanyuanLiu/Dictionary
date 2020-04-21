package service;

import protocol.Response;
import protocol.ResponseCode;
import service.database.Data;
import service.database.DatabaseCode;

import java.util.HashMap;

public class DictionaryService implements Service {
    private HashMap<String, String> dictionary;
    private Connector connector;

    public DictionaryService(Connector connector) {
        this.connector = connector;
    }

    public DatabaseCode load() {
        Data data = this.connector.readAll();
        if (data.status == DatabaseCode.DATABASE_READ) {
            this.dictionary = data.dictionary;
        }
        return data.status;
    }

    public synchronized Response query(String word) {
        if (word == null || word == "") {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        if (this.dictionary.containsKey(word)) {
            String meaning = this.dictionary.get(word);
            return new Response(ResponseCode.FOUND, word, meaning);
        }
        return new Response(ResponseCode.NOT_FOUND);
    }

    public synchronized Response add(String word, String meaning) {
        if ((word == null) || (meaning == null) ||
                (word.equals("")) || (meaning.equals(""))) {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        if (this.dictionary.containsKey(word)) {
            String existing_meaning = this.dictionary.get(word);
            return new Response(ResponseCode.ALREADY_EXIST, word, existing_meaning);
        }
        word = word.toLowerCase();
        meaning = meaning.toLowerCase();
        this.dictionary.put(word, meaning);
        this.save();
        return new Response(ResponseCode.ADDED, word, meaning);
    }

    public synchronized Response remove(String word) {
        if (word == null || word.equals("")) {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        if (this.dictionary.containsKey(word)) {
            this.dictionary.remove(word);
            this.save();
            return new Response(ResponseCode.DELETED);
        }
        return new Response(ResponseCode.NOT_FOUND);
    }

    public synchronized Response index() {
        return new Response(ResponseCode.INDEX, this.dictionary);
    }


    public synchronized Boolean save() {
        if (this.connector.writeAll(this.dictionary)) {
            return true;
        } else {
            System.out.println("Failed to save to file");
            return false;
        }
    }

    public void finalize() {
        this.save();
    }
}