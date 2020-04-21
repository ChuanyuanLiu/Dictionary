package client;

import protocol.Request;
import protocol.Response;

import java.io.IOException;
import java.net.Socket;

// IMPORTANT always use the connect method before executing
public class Client {

    private String hostName;
    private int hostPort;
    private Socket socket;
    private ClientConnection connection;
    private InputParser inputParser = new InputParser();
    private Display display;


    // Send a request and receive a response
    // Remember to connect to host first
    private void execute(Request request) {

        display.displayIn("Attempt to message host");

        // Setup
        Response response;

        // Check if we have an initial connection.
        if (this.connection == null) {
            connect();
            return;
        }

        try {
            if (request == null) {
                // Close connection
                this.connection.close();
                display.displayOut("Disconnected with host");
            }
            display.display(request);
            // Keep trying to re-connection upon timeout
            while (true) {
                try {
                    // Send request
                    this.connection.send(request);
                    // Receive response
                    response = connection.receive();
                    // Display the response
                    display.display(response);
                    // Stop execution
                    break;
                } catch (IOException e) {
                    // Re-establish a connection with server
                    // if new socket fails, the error will stop the loop because IOException
                    // will be catched by the outermost catch
                    display.displayIn("Attempt to connect to host");
                    this.socket = new Socket(this.hostName, this.hostPort);
                    this.connection = new ClientConnection(this.socket);
                }
            }
        } catch (IOException e) {
            display.displayOut("Lost connection with host at " + hostName + ":" + hostPort);
        }
    }


    public Client(String address, int port) {
        this.hostName = address;
        this.hostPort = port;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void Update(String query, String word, String meaning) {
        // Create a new request
        Request request = inputParser.getRequest(query, word, meaning);
        // Execute
        execute(request);
    }

    // Connect to host
    public void connect() {
        display.displayIn("Attempt to connect to host");
        try {
            // Establish initial connection
            this.socket = new Socket(this.hostName, this.hostPort);
            this.connection = new ClientConnection(this.socket);
        } catch (IOException e) {
            display.displayOut("Could not connect with host at " + hostName + ":" + hostPort);
            return;
        }
        display.displayOut("Connected to host at " + hostName + ":" + hostPort);
    }


    // Close upon exit
    @Override
    protected void finalize() throws Throwable {
        this.connection.close();
        super.finalize();
    }

}