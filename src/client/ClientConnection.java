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

    // send the request and receive the response
    public Response send(Request request) {
        Response response = new Response(ResponseCode.CONNECTION_LOST);
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(this.socket.getOutputStream());
            objectOut.writeObject(request);
            objectOut.flush();
            ObjectInputStream objectInput = new ObjectInputStream(this.socket.getInputStream());
            response = (Response) objectInput.readObject();
        } catch (IOException e) {
            System.out.println("Connection Lost");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("protocol.Request is not correctly formated");
            e.printStackTrace();
        }
        return response;
    }
}