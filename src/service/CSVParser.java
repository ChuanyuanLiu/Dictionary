package service;

import javafx.util.Pair;

import java.io.*;
import java.util.HashMap;

// A service.Parser that converts csv file to a map of words to meaning
public class CSVParser implements Parser {
    private String token;
    private String fileLocation;

    public CSVParser(String fileName, String token) {
        this.token = token;
        this.fileLocation = fileName;
    }

    public Pair<Boolean, HashMap<String, String>> readAll() {
        HashMap<String, String> data = new HashMap<String, String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.fileLocation));
            while (true) {
                String[] row = reader.readLine().split(token);
                if (row == null)
                    break;
                data.put(row[0], row[1]);
            }
            reader.close();
        } catch (IOException e) {
            return new Pair(false, null);
        }
        return new Pair(true, data);
    }

    public Boolean writeAll(HashMap<String, String> dictionary) {
        try {
            FileWriter fileWriter = new FileWriter(this.fileLocation);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (String key : dictionary.keySet()) {
                writer.write(key + dictionary.get(key));
                writer.newLine();
            }
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}