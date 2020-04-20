package protocol;

import java.io.Serializable;

public class Request implements Serializable {
    public String word;
    public String meaning;
    public RequestCode status;

    public Request(RequestCode status, String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
        this.status = status;
    }

    public Request(RequestCode status, String word) {
        this.word = word;
        this.meaning = null;
        this.status = status;
    }

    public Request(RequestCode status) {
        this.word = null;
        this.meaning = null;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request: " + status + "\nword: " + word + "\nmeaning: " + meaning + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        Request other = (Request) obj;
        if ((other.word == this.word || other.word.equals(this.word)) &&
            (other.meaning == this.meaning || other.meaning.equals(this.meaning)) &&
            other.status == this.status) {
            return true;
        }
        return false;
    }
}