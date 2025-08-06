package fortytwolabs;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleFileServer {
    private final int port;
    private final String rootDir;

    public SimpleFileServer(){
        this.port = 8080;
        this.rootDir = "C:\\Users\\Swaraj Bankar\\Desktop\\My-Files";
    }

    public void start(){
        try(ServerSocket socket = new ServerSocket(port)){
            System.out.println("Server Started On Port : " + port);
            while (true){
                Socket clientSocket = socket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleClient(Socket clientSocket){
    try(
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true)
            ) {

        String requestLine = in.readLine();

        if(requestLine == null || !requestLine.startsWith("GET")) return;

        String[] parts = requestLine.split(" ");
        if(parts.length < 2) return;

        String filePath = parts[1].substring(1);
        File file = new File(rootDir, filePath);

        if(!file.exists() || !file.isFile()){
            out.println("HTTP/1.1 404 FILE NOT FOUND");
            out.println("content-type : text/html");
            out.println();
            out.println("<html><body><h1> File Not Found </h1></body></html>");
            return;
        }

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type : application/octet-stream");
        out.println("Content-Disposition : attachment; filename=\"" + file.getName() + "\"");
        out.println("Content-Length: "+file.length());
        out.println();
        out.flush();

        try(FileInputStream fis = new FileInputStream(file)){
            byte[] buffer = new byte[4096];
            int bytesRead;
            while((bytesRead = fis.read(buffer)) != -1){
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        }

    } catch (IOException e) {
        throw new RuntimeException(e);
    } finally {
        try{
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    }

}
