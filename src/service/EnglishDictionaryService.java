package service;

import javafx.util.Pair;
import protocol.Response;
import protocol.ResponseCode;

import java.util.HashMap;

public class EnglishDictionaryService implements DictionaryService {
    private HashMap<String, String> dictionary;
    private Parser parser;

    public EnglishDictionaryService(Parser parser) {
        this.parser = parser;
        this.load();
    }

    public Boolean load() {
        Pair<Boolean, HashMap<String, String>> response = this.parser.readAll();
        if (response.getKey()) {
            this.dictionary = response.getValue();
            return true;
        } else {
            System.out.println("Could not load file");
            return false;
        }
    }

    public Response query(String word) {
        if (word == null) {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        if (this.dictionary.containsKey(word)) {
            String meaning = this.dictionary.get(word);
            return new Response(ResponseCode.FOUND, word, meaning);
        }
        return new Response(ResponseCode.NOT_FOUND);
    }

    public Response add(String word, String meaning) {
        if ((word == null) || (meaning == null)) {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        if (this.dictionary.containsKey(word)) {
            String existing_meaning = this.dictionary.get(word);
            return new Response(ResponseCode.ALREADY_EXIST, word, existing_meaning);
        }
        this.dictionary.put(word, meaning);
        this.save();
        return new Response(ResponseCode.ADDED, word, meaning);
    }

    public Response remove(String word) {
        if (word == null) {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        this.dictionary.remove(word);
        this.save();
        return new Response(ResponseCode.DELETED);
    }

    public Response index() {
        return new Response(ResponseCode.INDEX, this.dictionary);
    }

    public Boolean save() {
        if (this.parser.writeAll(this.dictionary)) {
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