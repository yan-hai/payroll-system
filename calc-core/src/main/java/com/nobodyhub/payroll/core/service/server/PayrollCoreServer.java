package com.nobodyhub.payroll.core.service.server;

import com.nobodyhub.payroll.core.task.TaskFactory;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author yan_h
 * @since 2018-05-07.
 */
public class PayrollCoreServer {
    private final int port;
    private final Server server;
    private final PayrollCoreServerService service;

    public PayrollCoreServer(int port, TaskFactory taskFactory) {
        this.port = port;
        this.service = new PayrollCoreServerService(taskFactory);
        this.server = ServerBuilder.forPort(port).addService(service).build();
    }

    public void start() throws IOException {
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                PayrollCoreServer.this.stop();
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
