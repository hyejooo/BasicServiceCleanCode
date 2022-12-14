import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Dispatcher {
    private final int HEADER_SIZE = 6; // 헤더 사이즈 6크기로 고정
    public void dispatch(ServerSocket serverSocket) {
        try {
            Socket socket = serverSocket.accept();
            demultiplex(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 받은 데이터를 분배
    public void demultiplex(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();

            byte[] buffer = new byte[HEADER_SIZE];
            inputStream.read(buffer);
            String header = new String(buffer);

            switch (header) {
                case "0x5001":  // 헤더가 0x5001이면 SayHelloProtocol 실행
                    StreamSayHelloProtocol sayHelloProtocol = new StreamSayHelloProtocol();
                    sayHelloProtocol.handleEvent(inputStream);
                    break;
                case "0x6001":
                    StreamUpdateProfileProtocol updateProfileProtocol = new StreamUpdateProfileProtocol();
                    updateProfileProtocol.handleEvent(inputStream);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
