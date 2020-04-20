package service;

import service.database.Data;
import service.database.DatabaseCode;

import java.io.*;
import java.util.HashMap;

// A service.Connector that converts csv file to a map of words to meaning
public class CSVConnector implements Connector {
    private String token;
    private String fileLocation;

    public CSVConnector(String fileName, String token) {
        this.token = token;
        this.fileLocation = fileName;
    }

    public Data readAll() {
        HashMap<String, String> data = new HashMap<String, String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.fileLocation));
            while (true) {
                String row = reader.readLine();
                if (row == null)
                    break;
                String[] item = row.split(token);
                data.put(item[0], item[1]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return new Data(DatabaseCode.DATABASE_UNAVAILABLE, data);
        }
        return new Data(DatabaseCode.DATABASE_READ, data);
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