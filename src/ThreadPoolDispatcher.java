import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPoolDispatcher implements Dispatcher {
    // 생성자와 스레드 수
    static final String NUMBERTHREADS = "8";
    static final String THREADPROP = "Threads";

    private int numThreads;

    public ThreadPoolDispatcher() {
        // 시스템 정보의 스레드 수를 가져오거나 기본 값을 가져옴
        numThreads = Integer.parseInt(System.getProperty(THREADPROP, NUMBERTHREADS));
    }
    public void dispatch(final ServerSocket serverSocket, final HandleMap handleMap) {
        for (int i = 0; i <(numThreads - 1); i++) {
            Thread thread = new Thread() {
                public void run() {
                    dispatchLoop(serverSocket, handleMap);
                }
            };
            thread.start();
            System.out.println("Created and started Thread = " + thread.getName());
        }
        System.out.println("Iterative server starting in main thread " +
                Thread.currentThread().getName());
        dispatchLoop(serverSocket, handleMap);
    }
    private void dispatchLoop(ServerSocket serverSocket, HandleMap handleMap) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Runnable demuliplexer = new Demultiplexer(socket, handleMap);
                demuliplexer.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
