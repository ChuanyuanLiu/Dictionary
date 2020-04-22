//Chuanyuan Liu (884140)
package client;

import protocol.Request;
import protocol.Response;
import protocol.ResponseCode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Display {
    private GUI gui;
    private DateFormat dateFormat = new SimpleDateFormat("mm:ss");

    public Display(GUI gui) {
        this.gui = gui;
    }

    private String getOutputPrefix () {
        return String.format("Out [%s]> ", dateFormat.format(new Date()));
    }

    private  String getInputPrefix() {
        return String.format("In  [%s]> ", dateFormat.format(new Date()));
    }

    private String getOutputPadding() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getOutputPrefix().length(); i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    private String getInputPadding() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getInputPrefix().length(); i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    // Show to GUI
    private void show(String string) {
        gui.appendDialogue(string + "\n");
    }

    // Display dictionary
    public void display(HashMap<String,String> dictionary) {
        show(getOutputPadding() + "word: meaning");
        show(getOutputPadding() + "-------------");
        for (String key :dictionary.keySet()) {
            show(String.format("%s%s:%s",getOutputPadding(), capitalize(key), capitalize(dictionary.get(key))));
        }
    }

    private String capitalize(String string) {
        if (string.length() < 1) {
            return string.toUpperCase();
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    // Display response accordingly
    public void display(Response response) {
        final ResponseCode status = response.status;
        String output = getOutputPrefix();

        // Check for error
        switch (status) {
            case NOT_FOUND:
                show( output + "The word could not be found");
                break;
            case ALREADY_EXIST:
                show( output + "The word already exist");
                break;
            case INVALID_REQUEST:
                show( output + "The request is invalid, use INDEX, ADD, DELETE, QUERY");
                break;
            case DELETED:
                show( output + "The word was deleted");
                break;
            case INDEX:
                show( output + "The whole dictionary ");
                display(response.dictionary);
                break;
            case FOUND:
                show( output + "Found the word");
                display(response.dictionary);
                break;
            case ADDED:
                show( output + "Added the word");
                break;
            case CONNECTION_LOST:
                show( output + "Lost connection");
                break;
            case MISSING_ARGUMENT:
                show( output + "Missing arguments");
                break;
            default:
                show( output + "Unknown error");
                break;
        }
    }

    public void displayIn(String string) {
        show(getInputPrefix() + string);
    }

    public void displayOut(String string) {
        show(getOutputPrefix() + string);
    }

    public void display (Request request) {
        show(getInputPrefix() + "Request: "+ request.status);
        if (request.word != null) {
            show(getInputPadding() + "Word: "+ request.word);
        }
        if (request.meaning != null) {
            show(getInputPadding() + "Meaning: " + request.meaning);
        }
    }

}
