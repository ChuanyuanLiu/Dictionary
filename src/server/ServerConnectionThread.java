//Chuanyuan Liu (884140)
package server;

import service.Service;
import java.net.Socket;

public class ServerConnectionThread extends ServerConnection implements Runnable {

    ServerConnectionThread(Service service, Socket socket) {
        super(service, socket);
    }

    @Override
    public void run() {
        super.execute();
    }
}
