package com.nobodyhub.payroll.core.task.callback;

import com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol;
import com.nobodyhub.payroll.core.task.ExecutionContext;
import com.nobodyhub.payroll.core.task.ExecutionStatusCode;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

/**
 * @author Ryan
 */
@RequiredArgsConstructor
public class ExecutionCallback implements Callback {
    private final StreamObserver<CalculationCoreProtocol.Response> responseObserver;

    @Override
    public void onStart() {
        //TODO: add logger
    }

    @Override
    public void onError(Exception e, ExecutionContext context) {
        //TODO: add logger
        //TODO: distinguish SttausCode by the type of Exception
        context.getExecutionStatus().setStatusCode(ExecutionStatusCode.ERROR);
        context.getExecutionStatus().setMessage(e.getMessage());
        responseObserver.onNext(context.toResponse());
    }

    @Override
    public void onComplete(ExecutionContext context) {
        //TODO: add logger
        responseObserver.onNext(context.toResponse());
    }
}
