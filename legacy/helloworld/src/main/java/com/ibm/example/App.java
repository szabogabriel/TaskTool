package com.ibm.example;

public final class App {

    Server server;
    Database database;

    private App() {
        database = new Database(Config.DB_FILE_PATH.value());
        logMessage("Starting server ...");
        server = new Server(database);
    }

    public static void main(String[] args) {
        new App();
    }

    private void logMessage(String message) {
        long id = database.log(message);
        System.out.println("Logged message with ID: " + id);
    }
}
