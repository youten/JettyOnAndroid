
package youten.redo.jetty.server;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class WSChatServlet extends WebSocketServlet {
    private static final long serialVersionUID = 1L;
    ChatRoom mChatRoom = new ChatRoom();

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest req, String protocol) {
        return new ChatWebSocket(mChatRoom);
    }
}
