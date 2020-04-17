package service;

import javafx.util.Pair;

import java.io.*;
import java.util.HashMap;

// A service.Parser that converts csv file to a map of words to meaning
public class SerializeParser implements Parser {
    private String fileLocation;

    public SerializeParser(String fileName) {
        this.fileLocation = fileName;
    }

    public Pair<Boolean, HashMap<String, String>> readAll() {
        HashMap<String, String> dictionary;
        FileInputStream fileInput;
        try {
            fileInput = new FileInputStream(this.fileLocation);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            dictionary = (HashMap<String, String>) objectInput.readObject();
            objectInput.close();
        } catch (IOException e) {
            return new Pair(false, null);
        } catch (ClassNotFoundException e) {
            return new Pair(false, null);
        }
        return new Pair(true, dictionary);
    }

    public Boolean writeAll(HashMap<String, String> dictionary) {
        try {
            FileOutputStream fileOutput = new FileOutputStream(this.fileLocation);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(dictionary);
            objectOutput.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}