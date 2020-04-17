import service.EnglishDictionaryService;
import service.Parser;
import service.SerializeParser;

public class Main {
    public static void main(String[] args) {
        Parser parser = new SerializeParser("resources/Dictionary.ser");
        EnglishDictionaryService s = new EnglishDictionaryService(parser);
        System.out.println(s.index());
        System.out.println(s.add("Computer", "An electronic device"));
        System.out.println(s.add("Taxonomy",
                "a classification into ordered categories: a proposed taxonomy of educational objectives."));
        System.out.println(s.index());
    }
}