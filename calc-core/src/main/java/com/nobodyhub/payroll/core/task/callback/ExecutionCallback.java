package com.nobodyhub.payroll.core.task.callback;

import com.nobodyhub.payroll.core.common.Identifiable;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;
import com.nobodyhub.payroll.core.task.status.ExecutionStatusCode;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.Phaser;

/**
 * The callback for the payroll calculation execution
 *
 * @author Ryan
 */
@RequiredArgsConstructor
public class ExecutionCallback implements Callback, Identifiable {
    /**
     * stream from server to client
     */
    @Setter
    private StreamObserver<PayrollCoreProtocol.Response> responseObserver;
    /**
     * Phaser to await all calculation to finish before reply complete the stream
     */
    private final Phaser phaser = new Phaser(1);

    @Override
    public void onStart() {
        //TODO: add logger
        countUp();
    }

    @Override
    public void onError(Exception e, NormalExecutionContext context) {
        //TODO: add logger
        //TODO: distinguish StausCode by the type of Exception
        context.getExecutionStatus().setStatusCode(ExecutionStatusCode.ERROR);
        context.getExecutionStatus().setMessage(e.getMessage());
        onCompleted(context);
    }

    @Override
    public void onCompleted(NormalExecutionContext context) {
        //TODO: add logger
        try {
            responseObserver.onNext(context.toResponse(context));
        } catch (PayrollCoreException e) {
            onError(e, context);
        }
        countDown();
    }

    public void countUp() {
        phaser.register();
    }

    public void countDown() {
        phaser.arriveAndDeregister();
    }

    public void await() {
        phaser.arriveAndAwaitAdvance();
    }

    /**
     * get the unique identifier
     *
     * @return unique id
     */
    @Override
    public String getId() {
        //Execution do not need Id, just to satisfy the Factory interface
        return "";
    }
}
