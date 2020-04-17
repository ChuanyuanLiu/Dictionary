package server;

import protocol.Request;
import protocol.Response;
import protocol.ResponseCode;
import service.DictionaryService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection {
    private DictionaryService service;
    private Socket socket;

    // Offers service on a connection defined by the socket
    ServerConnection(DictionaryService service, Socket socket) {
        this.service = service;
        this.socket = socket;
    }

    // Generate the correct response based on the request
    private Response respond(Request request) {
        String word = request.word;
        String meaning = request.meaning;
        switch (request.status) {
            case QUERY:
                return this.service.query(word);
            case ADD:
                return this.service.add(word, meaning);
            case DELETE:
                return this.service.remove(word);
            case INDEX:
                return this.service.index();
            default:
                return new Response(ResponseCode.INVALID_REQUEST);
        }
    }

    public Boolean execute() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(this.socket.getInputStream());
            Request request = (Request) objectInput.readObject();
            Response response = this.respond(request);
            System.out.print(request);
            System.out.print(response);
            ObjectOutputStream objectOut = new ObjectOutputStream(this.socket.getOutputStream());
            objectOut.writeObject(response);
            objectOut.flush();
        } catch (IOException e) {
            System.out.println("Connection Lost");
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("protocol.Request is not correctly formatted");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}