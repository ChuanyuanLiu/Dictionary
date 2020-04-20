package client;

import protocol.Request;
import protocol.RequestCode;

// Purpose is to parse text input from commandline
public class InputParser {

    // Remove leading and trailing spaces
    public String cleanInput (String input) {
        return input.trim();
    }

    // Returns null if no request quest can be found
    public RequestCode getRequestCode(String string) throws IllegalArgumentException {
        String input = this.cleanInput(string).toUpperCase();
        switch (input) {
            case "QUERY":
                return RequestCode.QUERY;
            case "ADD":
                return RequestCode.ADD;
            case "DELETE":
                return RequestCode.DELETE;
            case "INDEX":
                return RequestCode.INDEX;
            default:
                throw new IllegalArgumentException("Invalid Request Code. Must be either 'QUERY', 'ADD', 'DELETE' or 'INDEX'");
        }
    }

    // split input string and return a request
    // assumes input is in the format "RequestCode, Word, Meaning"
    // return null if not possible
    public Request getRequest(String string) throws IllegalArgumentException {
        String[] split = string.split(",");
        String word = null;
        String meaning = null;
        RequestCode code = getRequestCode(split[0]);
        if (split.length > 1) {
            word = cleanInput(split[1]);
        }
        if (split.length > 2) {
            meaning = cleanInput(split[2]);
        }
        switch (code) {
            case ADD:
                return new Request(code, word, meaning);
            case QUERY:
                return new Request(code, word);
            case DELETE:
                return new Request(code, word);
            default:
                return new Request(code);
        }
    }
}
