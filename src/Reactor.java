import java.io.IOException;
import java.net.ServerSocket;

public class Reactor {
    private ServerSocket serverSocket;
    private HandleMap handleMap;
    public Reactor(int port) {
        handleMap = new HandleMap();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startServer() {
        // Reactor에서 어느 Dispatcher를 쓰는가로 ThreadPer과 ThreadPool를 선택할 수 있다
//        Dispatcher dispatcher = new ThreadPerDispatcher();
        Dispatcher dispatcher = new ThreadPoolDispatcher();
        dispatcher.dispatch(serverSocket, handleMap);
    }
    // 오버로딩
    public void registerHandler(String header, EventHandler handler) {
        handleMap.put(handler.getHandler(), handler); // (핸들러 헤더, 헤더 등록)
    }
    public void registerHandler(EventHandler handler) {
        handleMap.put(handler.getHandler(), handler); // (핸들러 헤더, 헤더 등록)
    }
    public void removeHandler(EventHandler handler) {
        handleMap.remove(handler.getHandler());
    }
}
