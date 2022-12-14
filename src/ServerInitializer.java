import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInitializer {
    public static void main(String[] args) {
        int port = 5001;
        System.out.println("Server On: " + port);

        // Dispatcher에게 ServerSocket을 전달하도록 수정
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            Dispatcher dispatcher = new Dispatcher();
            while (true) {
                dispatcher.dispatch(serverSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
