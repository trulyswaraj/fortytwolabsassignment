package fortytwolabs;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {

    SimpleFileServer server = new SimpleFileServer();
    server.start();


    }
}