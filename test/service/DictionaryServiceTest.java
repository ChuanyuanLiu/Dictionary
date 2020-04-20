package service;

import org.junit.Assert;
import org.junit.Test;
import protocol.Response;
import protocol.ResponseCode;

import java.util.HashMap;

public class DictionaryServiceTest {

    public static Service getService() {
        Connector connector = SerializeConnectorTest.getParser();
        return new DictionaryService(connector);
    }

    @Test
    public void load() {
        Service service = getService();
        Assert.assertEquals(true, service.load());
    }

    @Test
    public void query() {
        Service service = getService();
        Response response = service.query("NONE");
        Assert.assertEquals(ResponseCode.NOT_FOUND, response.status);
        response = service.query("Terminate");
        Assert.assertEquals(ResponseCode.FOUND, response.status);
        Assert.assertEquals("To bring to an end", response.dictionary.get("Terminate"));
    }

    @Test
    public void add() {
        Service service = getService();
        HashMap<String, String> dictionary = SerializeConnectorTest.getData();
        Response response = service.add("Terminate", "New Meaning");
        Assert.assertEquals(ResponseCode.ALREADY_EXIST, response.status);
        response = service.add("Nice", "Giving pleasure or satisfaction");
        Assert.assertEquals(ResponseCode.ADDED, response.status);
        dictionary.put("Nice", "Giving pleasure or satisfaction");
        Assert.assertEquals(dictionary, service.index().dictionary);
    }

    @Test
    public void remove() {
        Service service = getService();
        HashMap<String, String> dictionary = SerializeConnectorTest.getData();
        Response response = service.remove("Impossible");
        Assert.assertEquals(ResponseCode.DELETED, response.status);
        response = service.remove("Java");
        Assert.assertEquals(ResponseCode.DELETED, response.status);
        dictionary.remove("Java");
        Assert.assertEquals(dictionary, service.index().dictionary);
    }

    @Test
    public void index() {
        Service service = getService();
        HashMap<String, String> dictionary = SerializeConnectorTest.getData();
        Response response = service.index();
        Assert.assertEquals(ResponseCode.INDEX, response.status);
        Assert.assertEquals(dictionary, response.dictionary);
    }
}