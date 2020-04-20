package server;

import service.Connector;
import service.DictionaryService;
import service.SerializeConnector;
import service.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

    public static void main(String[] args) {
        int hostPort = 4444;
        String fileLocation = "resources/Dictionary.ser";
        Connector connector = new SerializeConnector(fileLocation);
        Service service = new DictionaryService(connector);
        ServerSocket welcomeSocket = null;
        try {
            welcomeSocket = new ServerSocket(hostPort);
            while (true) {
                Socket socket = welcomeSocket.accept();
                System.out.println("received a new socket");
                socket.setSoTimeout(600000);
                ServerConnection connection = new ServerConnection(service, socket);
                connection.execute();
            }
        } catch(SocketTimeoutException e) {
            System.out.println("Socket timed out");
        } catch(IOException e) {
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