package com.nobodyhub.payroll.core.task.callback;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.item.common.Builder;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.task.Task;
import com.nobodyhub.payroll.core.task.execution.normal.NormalExecutionContext;
import com.nobodyhub.payroll.core.task.status.ExecutionStatusCode;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.logging.Logger;

/**
 * The callback for the payroll calculation execution
 *
 * @author Ryan
 */
@RequiredArgsConstructor
public class ExecutionCallback implements Callback, Builder<ExecutionCallback> {
    private static Logger logger = Logger.getLogger(
            ExecutionCallback.class.getName());
    /**
     * stream from server to client
     */
    @Setter
    private StreamObserver<PayrollCoreProtocol.Response> responseObserver;

    @Setter
    private Task task;


    @Override
    public void onStart(NormalExecutionContext context) {
        logger.info(context + " Starts! ");
    }

    @Override
    public void onError(Exception e, NormalExecutionContext context) {
        //TODO: add logger
        //TODO: distinguish StausCode by the type of Exception
        handleError(e, context);
        onCompleted(context);
    }

    @Override
    public void onCompleted(NormalExecutionContext context) {
        logger.info(context + " Complete! ");
        PayrollCoreProtocol.Response response = null;
        try {
            response = context.toResponse();
        } catch (PayrollCoreException e) {
            handleError(e, context);
        }
        responseObserver.onNext(response);

        task.countDown();
    }

    private void handleError(Exception e, NormalExecutionContext context) {
        logger.severe(context + " Error! ");
        logger.severe(e.getMessage());
        context.getExecutionStatus().setStatusCode(ExecutionStatusCode.ERROR);
        context.getExecutionStatus().setMessage(e.getMessage());
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

    @Override
    public ExecutionCallback build() {
        return new ExecutionCallback();
    }
}
