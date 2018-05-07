package com.nobodyhub.payroll.core.service.client;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author yan_h
 * @since 2018-05-07.
 */
public class PayrollCoreClientService {
    @Getter
    private final String host;
    @Getter
    private final int port;

    private final ManagedChannel channel;
    private final PayrollCoreServiceGrpc.PayrollCoreServiceStub asyncStub;


    public PayrollCoreClientService(String host, int port) {
        this.host = host;
        this.port = port;
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(host, port).usePlaintext();
        this.channel = channelBuilder.build();
        this.asyncStub = PayrollCoreServiceGrpc.newStub(channel);
    }

    /**
     * TODO: input/output should include all employee to be calculated
     *
     * @param beforeVals
     * @return
     */
    public Map<String, Map<String, String>> calculate(String taskId, Map<String, Map<String, String>> beforeVals) throws InterruptedException {
        final CountDownLatch finishLatch = new CountDownLatch(1);
        Map<String, Map<String, String>> afterVals = Maps.newHashMap();
        StreamObserver<PayrollCoreProtocol.Response> response = new StreamObserver<PayrollCoreProtocol.Response>() {
            @Override
            public void onNext(PayrollCoreProtocol.Response value) {
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

        StreamObserver<PayrollCoreProtocol.Request> request = asyncStub.doCalc(response);
        for (Map.Entry<String, Map<String, String>> entry : beforeVals.entrySet()) {
            PayrollCoreProtocol.Request reqData = PayrollCoreProtocol.Request.newBuilder()
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

    public void close() throws Exception {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}