
import com.jobinesh.example.grpc.hr.service.Department;
import com.wnc.example.grpc.note.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;

/**
 * Grpc Client
 */
public class GrpcClient {

    private ManagedChannel channel;
    private NoteServiceGrpc.NoteServiceBlockingStub noteServiceBlockingStub;
    private NoteServiceGrpc.NoteServiceStub noteServiceStub;

    public GrpcClient() {
        initializeStub();
    }

    public static void main(String[] args) throws Exception {

        new GrpcClient().exercieAllAPIs();
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private void exercieAllAPIs() throws InterruptedException {
        GrpcClient client = new GrpcClient();

        try {
            log("gRPC Client");
            NoteData noteResponse = client.findNoteById(NoteRequestID.newBuilder().setNoteId(2).build());
            log(noteResponse.toString());

//            NoteListResponse noteListResponse = client.findAll();
//            log(noteListResponse.toString());
            /*
             CountDownLatch finishLatch = new CountDownLatch(1);
            client.updateDepartmentsUsingStream(finishLatch);
            client.fetchAllDepartmentsUsingStream();
            client.updateDepartment(1000L);
            Department dept = client.findDepartmentById(1000L);
            DepartmentFilter filter = DepartmentFilter.newBuilder().build();
            List<Department> depts = client.findDepartmentByFilter(filter);
            client.deleteDepartment(1000L);
            dept = client.findDepartmentById(1000L);

            if (!finishLatch.await(10, TimeUnit.SECONDS)) {
                log("gRPC API call can not finish within 10 seconds");
            } */

        } catch (StatusRuntimeException e) {
            // Do not use Status.equals(...) - it's not well defined. Compare Code directly.
            if (e.getStatus().getCode() == Status.Code.DEADLINE_EXCEEDED) {
                log(e.getMessage());
            }
        } finally {

            client.shutdown();
        }
    }


    private void initializeStub() {
        channel = ManagedChannelBuilder.forAddress("127.0.0.1", 3001).usePlaintext().build();
        noteServiceBlockingStub = NoteServiceGrpc.newBlockingStub(channel);
        noteServiceStub = NoteServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private NoteData findNoteById(NoteRequestID noteRequestID) {
        return noteServiceBlockingStub.getNoteById(noteRequestID);
    }

    private NoteListResponse findAll(){
        return noteServiceBlockingStub.getNotes(Empty.newBuilder().build());
    }
}

