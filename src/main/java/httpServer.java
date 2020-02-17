import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class httpServer {

    public static void main(String[] args) throws IOException {

    int port = 8080;

    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    System.out.println("Server started at " + port);

server.createContext("/",  new RootHandler());
server.createContext("/myHead",  new HeaderHandler());
server.createContext("/myGet",  new GetHandler());
server.createContext("/myPost",  new PostHandler());


server.setExecutor(null);
server.start();

}

}






























/*
public class httpServer implements Runnable {

    static final File webRoot = new File("C:\\Users\\tugba\\labb1son\\src\\main\\resources");
    static final String defaultFile = "index.html";
    static final String fileNotFound = "404.html";
    static final String methodNotSupported = "not_supported.html";

    static final int PORT = 8080;

    static final boolean verbose = true;

    private Socket connect;

    public httpServer(Socket c) {
        connect = c;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverConnect = new ServerSocket
                    (PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");

            while (true) {
                httpServer myServer = new httpServer(serverConnect.accept());

                if (verbose) {
                    System.out.println("Connecton opened. (" + new Date() + ")");
                }

                // create dedicated thread to manage the client connection
                Thread thread = new Thread(myServer);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }


    @Override
    public void run() {
        // we manage our particular client connection
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        String fileRequested = null;

        try {
            in = new BufferedReader(new InputStreamReader((connect.getInputStream())));
            out = new PrintWriter(connect.getOutputStream());
            dataOut = new BufferedOutputStream(connect.getOutputStream());

            String input = in.readLine();
            StringTokenizer parse = new StringTokenizer(input);
            String method = parse.nextToken().toUpperCase();
            fileRequested = parse.nextToken().toLowerCase();

            if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
                if (verbose) {
                    System.out.println("501 Not Implemented: " + method + "method.");
                    File file = new File(webRoot, methodNotSupported);
                    int filelength = (int) file.length();
                    String contentMimeType = "text/html";
                    byte[] fileData = readfileData(file, filelength);

                    out.println("HTTP/1.1 501 Not Implemented");
                    out.println("Server: http java");
                    out.println("Date: " + new Date());
                    out.println("Content-type: " + contentMimeType);
                    out.println("Content-length: " + filelength);
                    out.println(); // blank line between headers and content, very important !
                    out.flush(); // flush character output stream buffer
                    // file
                    dataOut.write(fileData, 0, filelength);
                    dataOut.flush();


                } }else{
                if (fileRequested.endsWith("/"))fileRequested += defaultFile;
            }

            File file = new File(webRoot, fileRequested);
            int fileLength = (int) file.length();
            String content = getContentType(fileRequested);


            if (method.equals("GET")) {
                byte[] fileData = readfileData(file, fileLength);


                out.println("HTTP/1.1 200 OK");
                out.println("Date: " + new Date());
                out.println("Content-type: " + content);
                out.println("Content-length: " + fileLength);
                out.println(); // blank line between headers and content, very important !
                out.flush(); // flush character output stream buffer

                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();}

            if (verbose) {
                System.out.println("File " + fileRequested + " of type " + content + " returned");
            }

            if(method.equals("POST")){


            }

        }catch(FileNotFoundException fnfe){
            try {
                fileNotFound(out, dataOut, fileRequested);
            } catch (IOException ioe) {
                System.err.println("Error with file not found exception : " + ioe.getMessage());
            }

        } catch (IOException ioe) {
            System.err.println("Server error : " + ioe);
        } finally {
            try {
                in.close();
                out.close();
                dataOut.close();
                connect.close(); // we close socket connection
            } catch (Exception e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }

            if (verbose) {
                System.out.println("Connection closed.\n");
            }
        }


    }

    private void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested) throws IOException {
        File file = new File(webRoot, fileNotFound);
        int fileLength = (int) file.length();
        String content = "text/html";
        byte[] fileData = readFileData(file, fileLength);

        out.println("HTTP/1.1 404 File Not Found");
        out.println("Server: localhost");
        out.println("Date: " + new Date());
        out.println("Content-type: " + content);
        out.println("Content-length: " + fileLength);
        out.println(); // blank line between headers and content, very important !
        out.flush(); // flush character output stream buffer

        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();

        if (verbose) {
            System.out.println("File " + fileRequested + " not found");
        }
    }



    private byte[] readFileData(File file, int fileLength) throws IOException {
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



    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
            return "text/html";
        else
            return "text/plain";
    }

    private byte[] readfileData(File file, int filelength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[filelength];

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
*/






