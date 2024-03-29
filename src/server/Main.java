//Chuanyuan Liu (884140)
package server;

import service.Connector;
import service.DictionaryService;
import service.SerializeConnector;
import service.Service;
import service.database.DatabaseCode;

public class Main {


    // An object that represents all the commandline arguments
    private static class CmdArgs {
        private int port;
        private String fileLocation;
        private int timeOut;
        CmdArgs(int port , String fileLocation, int timeOut) {
            this.port = port;
            this.fileLocation = fileLocation;
            this.timeOut = timeOut;
        }
    }

    // Parse commandline arguments
    private static CmdArgs parseArgs (String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java -jar DictionaryServer.jar <port> <dictionary-file> <timeout(ms)>");
            System.out.println("Using the default port at 4444, file at resources/Dictionary.ser, and 5mins timeout");
            return new CmdArgs(4444, "resources/Dictionary.ser", 30000);
        }
        try {
            int port = Integer.valueOf(args[0]);
            String fileLocation = args[1];
            int timeOut = Integer.valueOf(args[2]);
            return new CmdArgs(port, fileLocation, timeOut);
        } catch (NumberFormatException e) {
            System.out.println("Usage: java -jar DictionaryServer.jar <port> <dictionary-file>");
            System.out.println("Where the <port> and <timeout(ms)> must be a number");
            System.exit(1);
        }
        return null;
    }


    public static void main(String[] args) {
        // Parse commandline inputs
        CmdArgs cmdArgs = parseArgs(args);
        // Connect to dictionary file
        Connector connector = new SerializeConnector(cmdArgs.fileLocation);
        // Start the service
        Service service = new DictionaryService(connector);

        // Read from disk
        DatabaseCode status = service.load();
        switch (status) {
            case DATABASE_UNAVAILABLE:
                System.out.println("Dictionary file at location <" + cmdArgs.fileLocation + "> can't be found.");
                System.exit(1);
            case DATABASE_ERROR:
                System.out.println("Dictionary file at location <" + cmdArgs.fileLocation + "> isn't serializable.");
                System.exit(1);
        }

        // Start the server
        Server server = new Server(cmdArgs.port, cmdArgs.timeOut, service);
        server.execute();
    }
}
