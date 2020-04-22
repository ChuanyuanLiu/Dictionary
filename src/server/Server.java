//Chuanyuan Liu (884140)
package server;

import service.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

    private int port;
    private int timeOut;
    private Service service;

    public Server (int port, int timeOut, Service service) {
        this.port = port;
        this.timeOut = timeOut;
        this.service = service;
    }

    public void execute() {
        // Accept requests
        ServerSocket welcomeSocket = null;
        try {
            welcomeSocket = new ServerSocket(this.port);
            while (true) {
                // Receive a new connection
                Socket socket = welcomeSocket.accept();
                System.out.println("Received a new socket");
                socket.setSoTimeout(this.timeOut);
                // Create and start a new thread for that connection
                ServerConnectionThread connection = new ServerConnectionThread(this.service, socket);
                new Thread(connection).start();
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