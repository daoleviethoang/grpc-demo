package service;


import com.wnc.io.*;

import com.google.protobuf.ByteString;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;


public class UploadService {

    private ManagedChannel channel;
    private FileServiceGrpc.FileServiceStub fileServiceStub;

    public void uploadFile() throws IOException, InterruptedException {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 6000)
                .usePlaintext()
                .build();
        this.fileServiceStub = FileServiceGrpc.newStub(channel);

        StreamObserver<FileUploadRequest> streamObserver = this.fileServiceStub.upload(new FileUploadObserver());

        // input file for testing
        //Path path = Paths.get("src/main/resources/input/java_input.pdf");

        // build metadata
//        FileUploadRequest metadata = FileUploadRequest.newBuilder()
//                .setMetadata(MetaData.newBuilder()
//                        .setName("output")
//                        .setType("pdf").build())
//                .build();
//        streamObserver.onNext(metadata);

        Path path = Paths.get("src/main/resources/input/demo_image.jpg");

        // build metadata
        FileUploadRequest metadata = FileUploadRequest.newBuilder()
                .setMetadata(MetaData.newBuilder()
                        .setName("output_image")
                        .setType("jpg").build())
                .build();
        streamObserver.onNext(metadata);

        // upload bytes
        InputStream inputStream = Files.newInputStream(path);
        byte[] bytes = new byte[4096];
        int size;
        while ((size = inputStream.read(bytes)     ) > 0){
            FileUploadRequest uploadRequest = FileUploadRequest.newBuilder()
                    .setFile(File.newBuilder().setContent(ByteString.copyFrom(bytes, 0 , size)).build())
                    .build();
            streamObserver.onNext(uploadRequest);
        }
        // close the stream
        inputStream.close();
        streamObserver.onCompleted();

        Thread.sleep(5000);
    }

    private static class FileUploadObserver implements StreamObserver<FileUploadResponse> {
        @Override
        public void onNext(FileUploadResponse fileUploadResponse) {
            System.out.println(
                    "File upload status :: " + fileUploadResponse.getStatus()
            );
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onCompleted() {

        }
    }

}
