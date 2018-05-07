package com.nobodyhub.payroll.core.service.server;

import com.google.common.collect.Sets;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol;
import com.nobodyhub.payroll.core.service.proto.CalculationCoreServiceGrpc;
import com.nobodyhub.payroll.core.task.Task;
import com.nobodyhub.payroll.core.task.TaskManager;
import com.nobodyhub.payroll.core.task.callback.ExecutionCallback;
import io.grpc.stub.StreamObserver;

import java.util.Set;

/**
 * @author Ryan
 */
public class CalculationCoreServerService extends CalculationCoreServiceGrpc.CalculationCoreServiceImplBase {
    protected TaskManager taskManager;

    public CalculationCoreServerService() {
        taskManager = new TaskManager();
    }

    @Override
    public StreamObserver<CalculationCoreProtocol.Request> doCalc(StreamObserver<CalculationCoreProtocol.Response> responseObserver) {
        final ExecutionCallback callback = new ExecutionCallback(responseObserver);
        return new StreamObserver<CalculationCoreProtocol.Request>() {
            Set<String> taskIds = Sets.newHashSet();
            @Override
            public void onNext(CalculationCoreProtocol.Request value) {
                taskIds.add(value.getTaskId());
                Task task = taskManager.get(value.getTaskId());
                task.setCallback(callback);
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
                taskManager.clear(taskIds);
            }
        };
    }


}
