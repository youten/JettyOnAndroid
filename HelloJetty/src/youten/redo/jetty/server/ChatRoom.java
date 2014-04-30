
package youten.redo.jetty.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.util.Log;

public class ChatRoom {
    private static final String TAG = "ChatRoom";
    private int mIdCounter = 0;
    private Map<ChatWebSocket, Integer> mUsers = new ConcurrentHashMap<ChatWebSocket, Integer>();

    // WebSocketコネクションを登録する
    // ついでに適当なIDを割り当てる
    public void join(ChatWebSocket ws) {
        if (mUsers.containsKey(ws)) {
            return;
        }
        mIdCounter++;
        mUsers.put(ws, mIdCounter);
        sendAll("login " + mIdCounter);
        Log.d(TAG, "login " + mIdCounter);
    }

    // 登録されていたコネクションを取り除く
    public void leave(ChatWebSocket ws) {
        int id = mUsers.get(ws);
        mUsers.remove(ws);
        sendAll("logoff " + id);
        Log.d(TAG, "logoff " + id);
    }

    // WebSocketからメッセージが届いたら付加情報を付けて全コネクションに流す
    public void onMessage(ChatWebSocket ws, String msg) {
        int id = mUsers.get(ws);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        msg = id + ": " + msg + " " + fmt.format(new Date());
        sendAll(msg);
        Log.d(TAG, msg);
    }

    // 抱えてる全コネクションに流す
    public void sendAll(String msg) {
        for (ChatWebSocket socket : mUsers.keySet()) {
            try {
                socket.send(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
