import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInitializer {
    public static void main(String[] args) {
        int port = 5001;
        System.out.println("Server On: " + port);

        Reactor reactor = new Reactor(port);

        reactor.registerHandler(new StreamSayHelloEventHandler());
        reactor.registerHandler(new StreamUpdateProfileEventHandler());

        reactor.startServer();
    }
}
