package com.wnc.grpc;

import com.wnc.grpc.io.FileUploadService;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GRPCServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        // build gRPC server
        Server server = ServerBuilder
                .forPort(6000)
                .addService((BindableService) new FileUploadService())
                .build();
        // start
        server.start();
        System.out.println("Upload File Server started " + server.getPort());
        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("gRPC server is shutting down!");
            server.shutdown();
        }));
        Thread.sleep(5000);

        // wait for 1 hr
        server.awaitTermination(1, TimeUnit.HOURS);

    }

}
