package client;

import org.junit.Assert;
import org.junit.Test;
import protocol.Request;
import protocol.RequestCode;

public class inputParserTest {

    @Test
    public void cleanInput() {
        InputParser parser = new InputParser();
        Assert.assertEquals("QUERY", parser.cleanInput(" QUERY  "));
    }

    @Test
    public void getRequestCode() {
        InputParser parser = new InputParser();
        Assert.assertEquals(RequestCode.QUERY, parser.getRequestCode(" query  "));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBadRequestCode () {
        InputParser parser = new InputParser();
        parser.getRequestCode("jjk");
    }

    @Test
    public void getRequest() {
        InputParser parser = new InputParser();
        Assert.assertEquals(
                new Request(RequestCode.ADD, "Request", "Asking politely"),
                parser.getRequest("Add, Request, Asking politely")
        );
        Assert.assertEquals(
                new Request(RequestCode.QUERY),
                parser.getRequest("query")
        );
    }
}