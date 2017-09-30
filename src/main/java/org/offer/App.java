package org.offer;

public class App {

    private static final int PORT = 2223;

    public static void main(String[] args) throws Exception {
        AppServer server = new AppServer(PORT);
        server.runServer();
    }
}
