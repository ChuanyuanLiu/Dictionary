package client;

public class Main {

    // An object that represents all the commandline arguments
    private static class CmdArgs {
        private int port;
        private String address;
        CmdArgs(String address, int port) {
            this.port = port;
            this.address = address;
        }
    }

    // Parse commandline arguments
    private static CmdArgs parseArgs (String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar DictionaryClient.jar <server-address> <server-port>");
            System.out.println("Using default localhost:4444");
            return new CmdArgs("localhost", 4444);
        }
        try {
            String address = args[0];
            int port = Integer.valueOf(args[1]);
            return new CmdArgs(address, port);
        } catch (NumberFormatException e) {
            System.out.println("Usage: java -jar DictionaryClient.jar <server-address> <server-port>");
            System.out.println("Where the <port> must be a number");
            System.exit(1);
        }
        return null;
    }

    public static void main(String[] args) {
        // Parse command line arguments
        CmdArgs cmdArgs = parseArgs(args);
        // Display GUI by adding client as a listener
        // and giving client the control of GUI
        Client client = new Client(cmdArgs.address, cmdArgs.port);
        GUI gui = new GUI(client);
        Display display = new Display(gui);
        client.setDisplay(display);

        // Connect to server
        client.connect();
    }
}
