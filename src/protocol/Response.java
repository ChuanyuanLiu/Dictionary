package protocol;

import java.io.Serializable;
import java.util.HashMap;

public class Response implements Serializable {
    public HashMap<String, String> dictionary;
    public ResponseCode status;

    public Response(ResponseCode status) {
        this.status = status;
        this.dictionary = null;
    }

    public Response(ResponseCode status, String word, String meaning) {
        this.status = status;
        this.dictionary = new HashMap<String, String>();
        this.dictionary.put(word, meaning);
    }

    public Response(ResponseCode status, HashMap<String, String> dictionary) {
        this.dictionary = dictionary;
        this.status = status;
    }

    @Override
    public String toString() {
        return "protocol.Response: " + status + "\ndictionary: " + dictionary + "\n";
    }
}