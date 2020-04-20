package service;

import org.junit.Assert;
import service.database.Data;
import service.database.DatabaseCode;

import java.util.HashMap;

public class SerializeConnectorTest {
    public static final String testLocation = "resources/Test.ser";

    public static HashMap<String, String> getData() {
        HashMap<String, String> dictionary = new HashMap<>();
        dictionary.put("Java", "A software programming language");
        dictionary.put("Terminate", "To bring to an end");
        return dictionary;
    }

    public static Connector getParser() {
        HashMap<String, String> dictionary = new HashMap<>();
        dictionary.put("Java", "A software programming language");
        dictionary.put("Terminate", "To bring to an end");
        Connector connector = new SerializeConnector(testLocation);
        connector.writeAll(dictionary);
        return connector;
    }

    @org.junit.Test
    public void writeAndRead() {
        Connector connector = new SerializeConnector(testLocation);
        if (!connector.writeAll(getData())) {
            Assert.fail("Could not write");
        }
        Data data = connector.readAll();
        Assert.assertEquals(DatabaseCode.DATABASE_READ, data.status);
        Assert.assertEquals(this.getData(), data.dictionary);
    }
}