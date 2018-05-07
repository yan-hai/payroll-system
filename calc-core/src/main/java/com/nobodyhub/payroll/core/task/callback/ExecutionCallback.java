package com.nobodyhub.payroll.core.task.callback;

import com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol;
import com.nobodyhub.payroll.core.task.ExecutionContext;
import com.nobodyhub.payroll.core.task.status.ExecutionStatusCode;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Phaser;

/**
 * @author Ryan
 */
@RequiredArgsConstructor
public class ExecutionCallback implements Callback {
    private final StreamObserver<CalculationCoreProtocol.Response> responseObserver;
    private final Phaser phaser = new Phaser(1);

    @Override
    public void onStart() {
        //TODO: add logger
        countUp();
    }

    @Override
    public void onError(Exception e, ExecutionContext context) {
        //TODO: add logger
        //TODO: distinguish SttausCode by the type of Exception
        context.getExecutionStatus().setStatusCode(ExecutionStatusCode.ERROR);
        context.getExecutionStatus().setMessage(e.getMessage());
        onCompleted(context);
    }

    @Override
    public void onCompleted(ExecutionContext context) {
        //TODO: add logger
        responseObserver.onNext(context.toResponse());
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
}
