//Chuanyuan Liu (884140)
package service.database;

import java.util.HashMap;

public class Data {
    public HashMap<String, String> dictionary;
    public DatabaseCode status;

    public Data(DatabaseCode status) {
        this.status = status;
        this.dictionary = null;
    }

    public Data(DatabaseCode status, HashMap<String, String> dictionary) {
        this.dictionary = dictionary;
        this.status = status;
    }
}
