package com.wnc.example.grpc.note.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {

    public static void main(String[] args) throws Exception {
        new GrpcServer().startServer();
    }

    private void startServer() throws Exception {
        // Create a new server to listen on port 8080
        Server server = ServerBuilder.forPort(3001)
                .addService(new NoteServiceGrpcImpl())
                .intercept(new GrpcExceptionHandler())
                .build();

        // Start the server
        server.start();

        // Server threads are running in the background.
        System.out.println("Server started " + server.getPort());
        // Don't exit the main thread. Wait until server is terminated.
        server.awaitTermination();
    }

}
