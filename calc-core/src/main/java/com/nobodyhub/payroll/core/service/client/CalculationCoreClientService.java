package com.nobodyhub.payroll.core.service.client;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol;
import com.nobodyhub.payroll.core.service.proto.CalculationCoreServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author yan_h
 * @since 2018-05-07.
 */
public class CalculationCoreClientService {
    @Getter
    private final String host;
    @Getter
    private final int port;

    private final ManagedChannel channel;
    private final CalculationCoreServiceGrpc.CalculationCoreServiceStub asyncStub;


    public CalculationCoreClientService(String host, int port) {
        this.host = host;
        this.port = port;
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(host, port).usePlaintext();
        this.channel = channelBuilder.build();
        this.asyncStub = CalculationCoreServiceGrpc.newStub(channel);
    }

    /**
     * TODO: input/output should include all employee to be calculated
     *
     * @param beforeVals
     * @return
     */
    public Map<String, Map<String, String>> doCalc(String taskId, Map<String, Map<String, String>> beforeVals) throws InterruptedException {
        final CountDownLatch finishLatch = new CountDownLatch(1);
        Map<String, Map<String, String>> afterVals = Maps.newHashMap();
        StreamObserver<CalculationCoreProtocol.Response> response = new StreamObserver<CalculationCoreProtocol.Response>() {
            @Override
            public void onNext(CalculationCoreProtocol.Response value) {
                afterVals.put(value.getDataId(), value.getValuesMap());
            }

            @Override
            public void onError(Throwable t) {
                //TODO: add logger and pass error
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        };

        StreamObserver<CalculationCoreProtocol.Request> request = asyncStub.doCalc(response);
        for (Map.Entry<String, Map<String, String>> entry : beforeVals.entrySet()) {
            CalculationCoreProtocol.Request reqData = CalculationCoreProtocol.Request.newBuilder()
                    .setTaskId(taskId)
                    .setDataId(entry.getKey())
                    .putAllValues(entry.getValue())
                    .build();
            request.onNext(reqData);
        }
        request.onCompleted();
        finishLatch.await();
        return afterVals;
    }
}