package com.nobodyhub.payroll.core.service.client;

import com.google.common.collect.Maps;
import com.nobodyhub.payroll.core.exception.PayrollCoreException;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol;
import com.nobodyhub.payroll.core.service.proto.PayrollCoreServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Service provider for {@link PayrollCoreClient}
 *
 * @author yan_h
 * @since 2018-05-07.
 */
public class PayrollCoreClientService {
    /**
     * Server host
     */
    @Getter
    private final String host;
    /**
     * Server port
     */
    @Getter
    private final int port;

    /**
     * gRPC channel
     */
    private final ManagedChannel channel;
    /**
     * stub for stream process
     */
    private final PayrollCoreServiceGrpc.PayrollCoreServiceStub asyncStub;


    public PayrollCoreClientService(String host, int port) {
        this.host = host;
        this.port = port;
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(host, port).usePlaintext();
        this.channel = channelBuilder.build();
        this.asyncStub = PayrollCoreServiceGrpc.newStub(channel);
    }

    /**
     * Synchronous interface to call remote payroll calculation
     * <b>Note:</b> there is no guarrentee on the order of result returned from server
     *
     * @param requestList a list of request to server
     * @return
     */
    public Map<String, Map<String, String>> calculate(List<PayrollCoreProtocol.Request> requestList) throws InterruptedException, PayrollCoreException {
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
        requestList.stream().forEach((r) -> request.onNext(r));
        request.onCompleted();
        finishLatch.await();
        return afterVals;
    }

    /**
     * Shutdown client
     *
     * @throws Exception
     */
    public void shutdown() throws Exception {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}