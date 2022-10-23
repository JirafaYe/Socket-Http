

import handler.HttpHandler;


import server.Server;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Server server = new Server(new HttpHandler());
        server.listen(8080);
    }

}
