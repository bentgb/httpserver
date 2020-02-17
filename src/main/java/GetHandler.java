import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

public class GetHandler implements HttpHandler {

    static final File webRoot = new File("C:\\Users\\tugba\\labb1son\\src\\main\\resources\\");
    static final String samplePdf = "sample.pdf";
    static final String samplePng = "sample.png";
    static final String sampleJson = "sample.json";

    @Override
    public void handle(HttpExchange he) throws IOException {

        // parse request
        Map<String, Object> parameters = new HashMap<String, Object>();
        URI requestedUri = he.getRequestURI();
        String query = requestedUri.getRawQuery();
        parseQuery(query, parameters);

       // send response
//        String response = "Requested content-type:";
      String requestedType="";
       String filePath="";
      for (String key : parameters.keySet())
//         response += key + " = " + parameters.get(key) + "\n";

          requestedType=key ;


      if(requestedType.equals("pdf"))
          filePath = samplePdf;
        if(requestedType.equals("png"))
            filePath = samplePng;
        if(requestedType.equals("json"))
            filePath = sampleJson;
        File file = new File(webRoot, filePath);
        int fileLength = (int) file.length();
        String content = getContentType(filePath);
        byte[] fileData = readFileData(file, fileLength);
        he.sendResponseHeaders(200, file.length());
         OutputStream os = he.getResponseBody();
        //os.write(response.toString().getBytes());
        //dataOut = new BufferedOutputStream(os);
//        PrintWriter out = new PrintWriter(os2);
//        out.println("HTTP/1.1 200 OK");
//        out.println("Server: Java HTTP Server from H&T");
//        out.println("Date: " + new Date());
//        out.println("Content-type: " + content);
//        out.println("Content-length: " + fileLength);
//        out.println(); // blank line between headers and content, very important !
//        out.flush(); // flush character output stream buffer





            os.write(fileData, 0, fileLength);
            //os.flush();
            //os.write(content.toString().getBytes());
            os.close();



    }



    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
            return "text/html";
        else if (fileRequested.endsWith(".css"))
            return "text/css";
        else if (fileRequested.endsWith(".jpeg"))
            return "image/jpeg";
        else if (fileRequested.endsWith(".jpg"))
            return "image/jpg";
        else if (fileRequested.endsWith(".png"))
            return "image/png";
        else if (fileRequested.endsWith(".js"))
            return "application/javascript";
        else if (fileRequested.endsWith(".pdf"))
            return "application/pdf";

        else if (fileRequested.endsWith(".json"))
            return "application/json";
        else
            return "text/plain";

    }


    public static void parseQuery(String query, Map<String,
            Object> parameters) throws UnsupportedEncodingException {

        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                String key = null;
                String value = null;
                if (param.length > 0) {
                    key = URLDecoder.decode(param[0],
                            System.getProperty("file.encoding"));
                }
                if (param.length > 1) {
                    value = URLDecoder.decode(param[1],
                           System.getProperty("file.encoding"));
                }
                if (parameters.containsKey(key)) {
                    Object obj = parameters.get(key);
                    if (obj instanceof List<?>) {
                        List<String> values = (List<String>) obj;
                        values.add(value);

                    } else if (obj instanceof String) {
                        List<String> values = new ArrayList<String>();
                        values.add((String) obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else {
                    parameters.put(key, value);
                }
            }
        }
    }

    public static byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }
}