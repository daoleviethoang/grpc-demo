
import service.UploadService;

/**
 * Grpc Client
 */
public class GRPCClient {

    public static void main(String[] args) throws Exception {
        UploadService uploadService = new UploadService();
        uploadService.uploadFile();
    }

}

