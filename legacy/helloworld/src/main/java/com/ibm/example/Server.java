package com.ibm.example;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Database database;

    public Server(Database database) {
        this(database, Integer.parseInt(Config.SERVER_PORT.value()));
    }

    public Server(Database database, int port) {
        // database = new H2Database("./my.db"); -> do this in the beginning.
        this.database = database;
        logMessage("Starting server ...");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted a connection");
                handleSocket(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleSocket(Socket socket) {
        new Thread(() -> {
            try {
                System.out.println("Handling socket: " + socket);
                InputStream inStream = socket.getInputStream();
                OutputStream outStream = socket.getOutputStream();

                // Read the incoming request
                byte[] buffer = new byte[4096];
                int bytesRead;

                do {
                    bytesRead = inStream.read(buffer);
                    String request = bytesRead > 0 ? new String(buffer, 0, bytesRead) : "";
                    System.out.println("Received request:\n" + request);
                    logMessage("Received request:\n" + request);
                } while (bytesRead == 4096);


                // Write the response
                String response = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "Content-Length: 13\r\n" +
                        "\r\n" +
                        "Hello, World";
                outStream.write(response.getBytes());
                outStream.flush();
                socket.close();
                logMessage("Sent response:\n" + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void logMessage(String message) {
        long id = database.log(message);
        System.out.println("Logged message with ID: " + id);
    }
}

