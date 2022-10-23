package server;

import config.UrlConfig;
import handler.HttpHandler;
import request.Request;
import response.Response;
import utils.HttpStatusCode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private HttpHandler handler;

    public Server(HttpHandler handler) {
        this.handler = handler;
    }

    public void listen(Integer port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            this.accept();
        }
    }

    private void accept() throws IOException {
        Socket socket = serverSocket.accept();
            new Thread(() -> {
                System.out.println("new Thread");
                Request request;
                System.out.println(socket);
                Response resp = new Response();;
                BufferedWriter writer = null;
                try {
                    System.out.println("GetRequest");
                    request = new Request(socket);
                    System.out.println(request);
                    writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    UrlConfig urlConfig = handler.getUrlConfig(request.getUrl());
                    handler.doHandle(request,resp,urlConfig);
                    handler.sendResponse(resp,writer);
                    socket.close();
                } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException | RuntimeException e) {
                    e.printStackTrace();
                    resp.setBody(e.getMessage());
                    resp.setHttpStatusCode(HttpStatusCode.BAD_REQUEST);
                    try {
                        handler.sendResponse(resp,writer);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
    }

    private void handle(Socket socket) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Request request = new Request(socket);
        Response response = new Response();
        UrlConfig urlConfig = handler.getUrlConfig(request.getUrl());
        handler.doHandle(request,response,urlConfig);
        handler.sendResponse(response,new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
    }
}
