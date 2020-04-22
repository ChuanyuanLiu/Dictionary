//Chuanyuan Liu (884140)
package service;

import protocol.*;
import service.database.DatabaseCode;

public interface Service {

    public DatabaseCode load();

    public Response query(String word);

    public Response add(String word, String meaning);

    public Response remove(String word);

    public Response index();

    public Boolean save();
}