
package youten.redo.jetty.server;

import java.io.IOException;

import org.eclipse.jetty.websocket.WebSocket;

public class ChatWebSocket implements WebSocket.OnTextMessage {
    private Connection mConnection;
    private ChatRoom mChatRoom;

    public ChatWebSocket(ChatRoom chatroom) {
        if (chatroom == null) {
            throw new IllegalArgumentException();
        }

        mChatRoom = chatroom;
    }

    // WebSocket が繋がったら chatroom に join する
    @Override
    public void onOpen(Connection conn) {
        mConnection = conn;
        mChatRoom.join(this);
    }

    // WebSocket が切れたら chatroom から leave する
    @Override
    public void onClose(int closeCode, String msg) {
        mChatRoom.leave(this);
    }

    // メッセージが飛んできたら chatroom にお任せする
    @Override
    public void onMessage(String msg) {
        mChatRoom.onMessage(this, msg);
    }

    // この接続先にメッセージを投げる
    public void send(String msg) throws IOException {
        mConnection.sendMessage(msg);
    }
}
