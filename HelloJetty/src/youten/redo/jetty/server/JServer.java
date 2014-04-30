
package youten.redo.jetty.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

/**
 * Jetty Server
 */
public class JServer {
    private static final String TAG = "JServer";
    private static final String ASSETS_DIR = "jetty";
    private int mPort;
    private Server mServer;

    /**
     * コンストラクタ
     * 
     * @param port Jettyを起動するport
     */
    public JServer(int port) {
        mPort = port;
    }

    /**
     * Serverを開始する。
     */
    public synchronized void start(Context applicationContext) {
        if ((mServer != null) && (mServer.isStarted())) {
            return;
        }
        if (mServer == null) {
            // setup Servlet Handler
            ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            // HelloServletクラスを "/hello"に割り当て
            servletHandler.addServlet(new ServletHolder(new HelloServlet()), "/hello");

            // setup Resource Handler
            // assets配下を展開
            extractAssets(applicationContext.getResources().getAssets(), ASSETS_DIR);
            String resourceBase = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/" + ASSETS_DIR;
            // 静的リソースルート"（外部ストレージ）/jetty"に設定
            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase(resourceBase);
            
            // setup Server and Handler List
            HandlerList handlerList = new HandlerList();
            handlerList.addHandler(servletHandler);
            handlerList.addHandler(resourceHandler);
            mServer = new Server(mPort);
            mServer.setHandler(handlerList);
        }

        try {
            mServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 参考：http://d.hatena.ne.jp/corrupt/20110203/1296695472
    private static void extractAssets(final AssetManager am, final String assetsDir) {
        // 配下のファイルリストを取得
        String[] files = null;
        try {
            if (am != null) {
                files = am.list(assetsDir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if ((files == null) || (files.length == 0)) {
            return;
        }

        // extract
        File extractDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + assetsDir);
        if (extractDir.isFile()) {
            extractDir.delete(); // Fileとしてゴミが残っていたら消してみる。
            return;
        }
        if (!extractDir.exists()) {
            extractDir.mkdirs();
        }

        for (String file : files) {
            InputStream in = null;
            BufferedOutputStream out = null;
            try {
                in = am.open(assetsDir + "/" + file);
                out = new BufferedOutputStream(new FileOutputStream(new File(
                        extractDir, file)));
                byte[] buffer = new byte[4 * 1024]; // 4k
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.close();
                in.close();
            } catch (FileNotFoundException e) {
                // FileNotFoundの際にはdirectoryと見なす
                extractAssets(am, assetsDir + "/" + file);
                continue;
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    // ignore
                }
            }

        }

    }

    /**
     * Serverを停止する。
     */
    public synchronized void stop() {
        if ((mServer == null) || (mServer.isStopped())) {
            return;
        }
        try {
            mServer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Serverが起動しているかどうか
     * 
     * @return 起動していたらtrueを、それ以外はfalseを返す。
     */
    public synchronized boolean isStarted() {
        if (mServer == null) {
            return false;
        }
        return mServer.isStarted();
    }
}
