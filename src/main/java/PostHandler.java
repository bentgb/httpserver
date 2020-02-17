import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PostHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange he) throws IOException {
        // parse request
        Map<String, Object> parameters = new HashMap<String, Object>();
        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        GetHandler.parseQuery(query, parameters);


              // send response
        String response = "";
                for (String key : parameters.keySet())
            //response += key + " = " + parameters.get(key) + "\n";

        he.sendResponseHeaders(200, response.length());
                OutputStream os = he.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();





    }
}
