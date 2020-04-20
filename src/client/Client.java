package client;

import protocol.Request;
import protocol.Response;
import protocol.ResponseCode;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Client {
    private String outputPrefix = "out> ";

    // Display dictionary
    private String displayDictionary(HashMap<String,String> dictionary) {
        StringBuilder outputs = new StringBuilder();
        outputs.append("\n     word: meaning");
        outputs.append("\n     -------------");
        for (String key :
                dictionary.keySet()) {
            outputs.append("\n     ");
            outputs.append(key + ": " + dictionary.get(key));
        }
        return outputs.toString();
    }

    // Display response accordingly
    private String display(Response response) {
        final ResponseCode status = response.status;
        String output = outputPrefix;

        // Check for error
        switch (status) {
            case NOT_FOUND:
                return output + "The word could not be found";
            case ALREADY_EXIST:
                return output + "The word already exist";
            case INVALID_REQUEST:
                return output + "The request is invalid, use INDEX, ADD, DELETE, QUERY";
            case DELETED:
                return output + "The word was deleted";
            case INDEX:
                return output + "The whole dictionary " + this.displayDictionary(response.dictionary);
            case FOUND:
                return output + "Found the word" + this.displayDictionary(response.dictionary);
            case ADDED:
                return output + "Added the word";
            case CONNECTION_LOST:
                return output + "Lost connection";
            case MISSING_ARGUMENT:
                return output + "Missing arguments";
            default:
                return output + "Unknown error";
        }
    }

    private String display(String string) {
        return outputPrefix + string;
    }

    // null means end of input
    private Request getInput (Scanner reader) throws IllegalArgumentException {
        System.out.print("in> ");
        String line = null;
        line = reader.nextLine();
        if (line == null) return null;
        if (line.trim().toUpperCase().equals("EXIT")) {
            return null;
        }
        InputParser parser = new InputParser();
        Request request = parser.getRequest(line);
        return request;
    }

    public static void main(String[] args) {
        // Get the host connection
        String hostName = "localhost";
        int hostPort = 4444;

        // Setup
        Client client = new Client();
        Scanner reader = new Scanner(System.in);
        ClientConnection connection;
        Socket socket;
        Request request;
        Response response;

        try {
            // Establish initial connection
            socket = new Socket(hostName, hostPort);
            connection = new ClientConnection(socket);
            // Keep parsing input
            while (true) {
                try {
                    // Get input
                    request = client.getInput(reader);
                    if (request == null) {
                        // Close connection
                        connection.close();
                        break;
                    }
                    // Keep trying to re-connection upon timeout
                    while (true) {
                        try {
                            // Send request
                            connection.send(request);
                            // Receive response
                            response = connection.receive();
                            // Display the response
                            System.out.println(client.display(response));
                            // Exit if successful
                            break;
                        } catch (IOException e) {
                            // Re-establish a connection with server
                            // if new socket fails, the error will stop the loop because IOException
                            // will be catched by the outermost catch
                            socket = new Socket(hostName, hostPort);
                            connection = new ClientConnection(socket);
                            System.out.println("Created new connection");
                            e.printStackTrace();
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(client.display(e.getMessage()));
                }
            }
        } catch (IOException e) {
            System.out.println(client.display("Could not connect with Host"));
            e.printStackTrace();
        }
    }
}