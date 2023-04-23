package ma.enset.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ma.enset.service.BankGrpcService;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        //utilison le clase Server de GRPC
        Server server= ServerBuilder.forPort(500)
                // on publions le service
                .addService(new BankGrpcService())
                .build();
          server.start();
         /* bloque*/ server.awaitTermination();

    }
}

/*package ma.enset.Client;


*/
