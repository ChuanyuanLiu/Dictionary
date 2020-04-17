package server;

import service.DictionaryService;
import service.EnglishDictionaryService;
import service.Parser;
import service.SerializeParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        int hostPort = 4444;
        String fileLocation = "resources/Dictionary.ser";
        Parser parser = new SerializeParser(fileLocation);
        DictionaryService service = new EnglishDictionaryService(parser);
        ServerSocket welcomeSocket = null;

        try {
            welcomeSocket = new ServerSocket(hostPort);
            while (true) {
                Socket socket = welcomeSocket.accept();
                ServerConnection connection = new ServerConnection(service, socket);
                if (connection.execute()) {
                    System.out.println("Success");
                } else {
                    System.out.println("Failure");
                }
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (welcomeSocket != null) {
                try {
                    welcomeSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}