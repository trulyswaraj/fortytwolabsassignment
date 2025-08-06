public class MainServer {
    private static final int port = 8080;

    public static void main(String[] args){

        SimpleFileServer server = new SimpleFileServer();
        server.start();
    }

}
