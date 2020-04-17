package service;

import protocol.*;

public interface DictionaryService {

    public Boolean load();

    public Response query(String word);

    public Response add(String word, String meaning);

    public Response remove(String word);

    public Response index();
}