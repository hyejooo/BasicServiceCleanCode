import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Dispatcher {
    private final int HEADER_SIZE = 6; // 헤더 사이즈 6크기로 고정
    public void dispatch(ServerSocket serverSocket, HandleMap handleMap) {
        try {
            Socket socket = serverSocket.accept();
            demultiplex(socket, handleMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 받은 데이터를 분배
    public void demultiplex(Socket socket, HandleMap handleMap) {
        try {
            InputStream inputStream = socket.getInputStream();

            byte[] buffer = new byte[HEADER_SIZE];
            inputStream.read(buffer);
            String header = new String(buffer);

            handleMap.get(header).handleEvent(inputStream);

            socket.close(); // socket close 추가
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
