package org.zjs.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.zjs.grpc.helloworld.GreeterGrpc;
import org.zjs.grpc.helloworld.HelloReply;
import org.zjs.grpc.helloworld.HelloRequest;

/**
 * Created by IntelliJ IDEA
 * User: jinshi.zjs
 * Date: 2018/4/4
 * Time: 16:08
 */
@Component
public class GrpcServer implements InitializingBean {

    private Server server;

    @Override
    public void afterPropertiesSet() throws Exception {
        // ***start server
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        //LOGGER.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            this.stop();
            System.err.println("*** server shut down");
        }));

        // ***block until shutdown
        if (server != null) {
            //server.awaitTermination();
        }
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello, your name is  " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
