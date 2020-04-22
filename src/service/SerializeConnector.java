//Chuanyuan Liu (884140)
package service;

import service.database.Data;
import service.database.DatabaseCode;

import java.io.*;
import java.util.HashMap;

// A service.Connector that converts csv file to a map of words to meaning
public class SerializeConnector implements Connector {
    private String fileLocation;

    public SerializeConnector(String fileName) {
        this.fileLocation = fileName;
    }

    // Read and closes the file
    public Data readAll() {
        HashMap<String, String> dictionary;
        FileInputStream fileInput;
        try {
            fileInput = new FileInputStream(this.fileLocation);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            dictionary = (HashMap<String, String>) objectInput.readObject();
            objectInput.close();
        } catch (IOException e) {
            return new Data(DatabaseCode.DATABASE_UNAVAILABLE);
        } catch (ClassNotFoundException e) {
            return new Data(DatabaseCode.DATABASE_ERROR);
        }
        return new Data(DatabaseCode.DATABASE_READ, dictionary);
    }

    // Overwrite then close the file
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