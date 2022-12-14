import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.List;

public class ServerInitializer {
    public static void main(String[] args) {
        int port = 5001;
        System.out.println("Server On: " + port);

        Reactor reactor = new Reactor(port);

        try { // SimpleFramework로 xml파일을 객체로 만듬
            Serializer serializer = new Persister();
            File source = new File("HandlerList.xml");
            ServerListData serverList = serializer.read(ServerListData.class, source);
            // 서버 정보 가져오기
            for (HandlerListData handlerListData : serverList.getServer()) {
                if ("server1".equals(handlerListData.getName())) {
                    List<HandlerData> handlerList = handlerListData.getHandler();
                    for (HandlerData handler : handlerList) {
                        try {
                            // Class.forName()으로 HandleMap에 클래스를 등록한다.
                            reactor.registerHandler(handler.getHandler(), (EventHandler) Class.forName(handler.getHandler()).newInstance());
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        reactor.startServer();
    }
}
