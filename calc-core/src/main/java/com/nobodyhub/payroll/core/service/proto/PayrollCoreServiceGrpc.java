package com.nobodyhub.payroll.core.service.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.11.0)",
    comments = "Source: calc-core.proto")
public final class PayrollCoreServiceGrpc {

  private PayrollCoreServiceGrpc() {}

  public static final String SERVICE_NAME = "payroll.core.service.proto.PayrollCoreService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getDoCalcMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Request,
      com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Response> METHOD_DO_CALC = getDoCalcMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Request,
      com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Response> getDoCalcMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Request,
      com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Response> getDoCalcMethod() {
    return getDoCalcMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Request,
      com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Response> getDoCalcMethodHelper() {
    io.grpc.MethodDescriptor<com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Request, com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Response> getDoCalcMethod;
    if ((getDoCalcMethod = PayrollCoreServiceGrpc.getDoCalcMethod) == null) {
      synchronized (PayrollCoreServiceGrpc.class) {
        if ((getDoCalcMethod = PayrollCoreServiceGrpc.getDoCalcMethod) == null) {
          PayrollCoreServiceGrpc.getDoCalcMethod = getDoCalcMethod = 
              io.grpc.MethodDescriptor.<com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Request, com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "payroll.core.service.proto.PayrollCoreService", "doCalc"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new PayrollCoreServiceMethodDescriptorSupplier("doCalc"))
                  .build();
          }
        }
     }
     return getDoCalcMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PayrollCoreServiceStub newStub(io.grpc.Channel channel) {
    return new PayrollCoreServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PayrollCoreServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PayrollCoreServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PayrollCoreServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PayrollCoreServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PayrollCoreServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * do calculation on requested data
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Request> doCalc(
        io.grpc.stub.StreamObserver<com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Response> responseObserver) {
      return asyncUnimplementedStreamingCall(getDoCalcMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getDoCalcMethodHelper(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Request,
                com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Response>(
                  this, METHODID_DO_CALC)))
          .build();
    }
  }

  /**
   */
  public static final class PayrollCoreServiceStub extends io.grpc.stub.AbstractStub<PayrollCoreServiceStub> {
    private PayrollCoreServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PayrollCoreServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PayrollCoreServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PayrollCoreServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * do calculation on requested data
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Request> doCalc(
        io.grpc.stub.StreamObserver<com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Response> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getDoCalcMethodHelper(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class PayrollCoreServiceBlockingStub extends io.grpc.stub.AbstractStub<PayrollCoreServiceBlockingStub> {
    private PayrollCoreServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PayrollCoreServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PayrollCoreServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PayrollCoreServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class PayrollCoreServiceFutureStub extends io.grpc.stub.AbstractStub<PayrollCoreServiceFutureStub> {
    private PayrollCoreServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PayrollCoreServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PayrollCoreServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PayrollCoreServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_DO_CALC = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PayrollCoreServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PayrollCoreServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DO_CALC:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.doCalc(
              (io.grpc.stub.StreamObserver<com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.Response>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PayrollCoreServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PayrollCoreServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.nobodyhub.payroll.core.service.proto.PayrollCoreProtocol.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PayrollCoreService");
    }
  }

  private static final class PayrollCoreServiceFileDescriptorSupplier
      extends PayrollCoreServiceBaseDescriptorSupplier {
    PayrollCoreServiceFileDescriptorSupplier() {}
  }

  private static final class PayrollCoreServiceMethodDescriptorSupplier
      extends PayrollCoreServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PayrollCoreServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PayrollCoreServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PayrollCoreServiceFileDescriptorSupplier())
              .addMethod(getDoCalcMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
