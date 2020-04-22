//Chuanyuan Liu (884140)
package service;

import protocol.Response;
import protocol.ResponseCode;
import service.database.Data;
import service.database.DatabaseCode;

import java.util.HashMap;

public class DictionaryService implements Service {
    private HashMap<String, String> dictionary;
    private Connector connector;

    private String clean(String string) {
        return string.trim().toLowerCase();
    }

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
        if (word == null) {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        String cleanWord = clean(word);
        if (cleanWord.equals("")) {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        if (this.dictionary.containsKey(cleanWord)) {
            String meaning = this.dictionary.get(cleanWord);
            return new Response(ResponseCode.FOUND, cleanWord, meaning);
        }
        return new Response(ResponseCode.NOT_FOUND);
    }

    public synchronized Response add(String word, String meaning) {
        if ((word == null) || (meaning == null) ) {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        String cleanWord = clean(word);
        String cleanMeaning = clean(meaning);
        if ((cleanWord.equals("")) || (cleanMeaning.equals(""))) {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        if (this.dictionary.containsKey(cleanWord)) {
            String existing_meaning = this.dictionary.get(cleanWord);
            return new Response(ResponseCode.ALREADY_EXIST, cleanWord, existing_meaning);
        }
        this.dictionary.put(cleanWord, cleanMeaning);
        this.save();
        return new Response(ResponseCode.ADDED, cleanWord, cleanMeaning);
    }

    public synchronized Response remove(String word) {
        if (word == null) {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        String cleanWord = clean(word);
        if (cleanWord.equals("")) {
            return new Response(ResponseCode.MISSING_ARGUMENT);
        }
        if (this.dictionary.containsKey(cleanWord)) {
            this.dictionary.remove(cleanWord);
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