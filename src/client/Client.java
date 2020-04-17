package client;

import protocol.Request;
import protocol.RequestCode;
import protocol.Response;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    // Display data accordingly
    private static void display(ClientConnection connection, Request request) {
        Response response = connection.send(request);
        System.out.print(request);
        System.out.print(response);
    }

    public static void main(String[] args) {
        String hostName = "localhost";
        int hostPort = 4444;
        ClientConnection c;
        Socket socket;
        try {
            Request r = new Request(RequestCode.INDEX);
            Request r1 = new Request(RequestCode.ADD, "Lamp", "A device that gives off light");
            Request r2 = new Request(RequestCode.QUERY, "Lamp");
            socket = new Socket(hostName, hostPort);
            c = new ClientConnection(socket);
            display(c, r);
            socket = new Socket(hostName, hostPort);
            c = new ClientConnection(socket);
            display(c, r1);
            socket = new Socket(hostName, hostPort);
            c = new ClientConnection(socket);
            display(c, r2);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}