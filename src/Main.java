//Chuanyuan Liu (884140)
// An program to initialize serialized data
import service.DictionaryService;
import service.Connector;
import service.SerializeConnector;

public class Main {
    public static void main(String[] args) {
        Connector connector = new SerializeConnector("resources/Dictionary.ser");
        DictionaryService s = new DictionaryService(connector);
        System.out.println(s.index());
        System.out.println(s.add("Computer", "An electronic device"));
        System.out.println(s.add("Taxonomy",
                "a classification into ordered categories: a proposed taxonomy of educational objectives."));
        System.out.println(s.index());
    }
}