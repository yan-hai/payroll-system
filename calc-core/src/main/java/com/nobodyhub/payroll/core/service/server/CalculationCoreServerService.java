package com.nobodyhub.payroll.core.service.server;

import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol;
import com.nobodyhub.payroll.core.service.proto.CalculationCoreServiceGrpc;
import com.nobodyhub.payroll.core.task.Task;
import com.nobodyhub.payroll.core.task.TaskFactory;
import com.nobodyhub.payroll.core.task.callback.ExecutionCallback;
import io.grpc.stub.StreamObserver;

/**
 * @author Ryan
 */
public class CalculationCoreServerService extends CalculationCoreServiceGrpc.CalculationCoreServiceImplBase {
    protected final TaskFactory taskFactory;

    public CalculationCoreServerService(TaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    @Override
    public StreamObserver<CalculationCoreProtocol.Request> doCalc(StreamObserver<CalculationCoreProtocol.Response> responseObserver) {
        final ExecutionCallback callback = new ExecutionCallback(responseObserver);
        return new StreamObserver<CalculationCoreProtocol.Request>() {
            Task task = null;

            @Override
            public void onNext(CalculationCoreProtocol.Request value) {
                if (task == null) {
                    Task task = taskFactory.get(value.getTaskId());
                    task.setCallback(callback);
                }
                try {
                    task.execute(value.getDataId(), value.getValuesMap());
                } catch (PayrollCoreException e) {
                    onError(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                //TODO: client send error
            }

            @Override
            public void onCompleted() {
                callback.await();
                responseObserver.onCompleted();
            }
        };
    }


}
