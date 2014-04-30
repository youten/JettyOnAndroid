
package youten.redo.jetty.server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;

import youten.redo.jetty.http.ContentType;

public class ServletUtil {
    public static final String LS = System.getProperty("line.separator");
    
    /**
     * 単純なHtmlを返す。
     * @param resp
     * @param title
     * @param message
     * @throws IOException
     */
    public static void responseHtml(HttpServletResponse resp, String title, String message) throws IOException {
        resp.setCharacterEncoding(HTTP.UTF_8);
        resp.setContentType(ContentType.HTML);
        StringBuilder sb = new StringBuilder();
        sb.append("<html>").append(LS);
        sb.append("<head><title>").append(title).append("</title></head>").append(LS);
        sb.append("<body><h1>").append(title).append("</h1>").append(LS);
        sb.append("<p>").append(message).append("</p>").append(LS);
        sb.append("</body></html>").append(LS);

        resp.getWriter().println(sb.toString());
    }

    private ServletUtil() {
        // Util
    }
}
