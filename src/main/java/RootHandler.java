import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class RootHandler implements HttpHandler {

    static final File webRoot = new File("C:\\Users\\husam\\Desktop\\labb1\\src\\main\\resources");
    static final String defaultFile = "index.html";



    @Override
    public void handle (HttpExchange he) throws IOException {

        String response = "<h1>Connected</h1>" ;
        File file = new File(webRoot, defaultFile);
        int filelength = (int) file.length();
        byte[] fileData = GetHandler.readFileData(file, filelength);
        he.sendResponseHeaders(200, filelength);
        OutputStream os = he.getResponseBody();
        os.write(fileData, 0, filelength);
        os.close();

    }



}
