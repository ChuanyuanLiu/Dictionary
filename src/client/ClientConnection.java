package client;

import protocol.Request;
import protocol.Response;
import protocol.ResponseCode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection {
    private Socket socket;

    // Offers service on a connection defined by the socket
    ClientConnection(Socket socket) {
        this.socket = socket;
    }

    // Send the request
    public void send(Request request) throws IOException {
        ObjectOutputStream objectOut = new ObjectOutputStream(this.socket.getOutputStream());
        objectOut.writeObject(request);
        objectOut.flush();
    }

    // Wait to receive the response
    public Response receive() throws IOException {
        Response response = new Response(ResponseCode.CONNECTION_LOST);
        try {
            ObjectInputStream objectInput = new ObjectInputStream(this.socket.getInputStream());
            response = (Response) objectInput.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("database.Request is not correctly formated");
            e.printStackTrace();
        }
        return response;
    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            System.out.println("Could not close socket");
            e.printStackTrace();
        }
    }
}