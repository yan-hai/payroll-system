package com.nobodyhub.payroll.core.service.server;

import com.nobodyhub.payroll.core.task.TaskFactory;
import com.nobodyhub.payroll.core.util.PayrollCoreConst;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Payroll Core sever
 *
 * @author yan_h
 * @since 2018-05-07.
 */
public class PayrollCoreServer {
    /**
     * server port
     */
    private final int port;
    /**
     * gRPC server
     */
    private final Server server;
    /**
     * Service provider
     */
    private final PayrollCoreServerService service;

    public PayrollCoreServer(TaskFactory taskFactory) {
        this(PayrollCoreConst.DEFAULT_PORT, taskFactory);
    }

    public PayrollCoreServer(int port, TaskFactory taskFactory) {
        this.port = port;
        this.service = new PayrollCoreServerService(taskFactory);
        this.server = ServerBuilder.forPort(port).addService(service).build();
    }

    /**
     * Start server in a new thread
     *
     * @throws IOException
     */
    public void start() throws IOException {
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> PayrollCoreServer.this.stop()));
    }

    /**
     * Stop the server
     */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * block the main thread from exiting
     *
     * @throws InterruptedException
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
