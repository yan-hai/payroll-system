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
public final class CalculationCoreServiceGrpc {

  private CalculationCoreServiceGrpc() {}

  public static final String SERVICE_NAME = "payroll.core.service.proto.CalculationCoreService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getDoCalcMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Request,
      com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Response> METHOD_DO_CALC = getDoCalcMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Request,
      com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Response> getDoCalcMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Request,
      com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Response> getDoCalcMethod() {
    return getDoCalcMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Request,
      com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Response> getDoCalcMethodHelper() {
    io.grpc.MethodDescriptor<com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Request, com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Response> getDoCalcMethod;
    if ((getDoCalcMethod = CalculationCoreServiceGrpc.getDoCalcMethod) == null) {
      synchronized (CalculationCoreServiceGrpc.class) {
        if ((getDoCalcMethod = CalculationCoreServiceGrpc.getDoCalcMethod) == null) {
          CalculationCoreServiceGrpc.getDoCalcMethod = getDoCalcMethod = 
              io.grpc.MethodDescriptor.<com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Request, com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "payroll.core.service.proto.CalculationCoreService", "doCalc"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new CalculationCoreServiceMethodDescriptorSupplier("doCalc"))
                  .build();
          }
        }
     }
     return getDoCalcMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CalculationCoreServiceStub newStub(io.grpc.Channel channel) {
    return new CalculationCoreServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CalculationCoreServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CalculationCoreServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CalculationCoreServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CalculationCoreServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class CalculationCoreServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Request> doCalc(
        io.grpc.stub.StreamObserver<com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Response> responseObserver) {
      return asyncUnimplementedStreamingCall(getDoCalcMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getDoCalcMethodHelper(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Request,
                com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Response>(
                  this, METHODID_DO_CALC)))
          .build();
    }
  }

  /**
   */
  public static final class CalculationCoreServiceStub extends io.grpc.stub.AbstractStub<CalculationCoreServiceStub> {
    private CalculationCoreServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CalculationCoreServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalculationCoreServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CalculationCoreServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Request> doCalc(
        io.grpc.stub.StreamObserver<com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Response> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getDoCalcMethodHelper(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class CalculationCoreServiceBlockingStub extends io.grpc.stub.AbstractStub<CalculationCoreServiceBlockingStub> {
    private CalculationCoreServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CalculationCoreServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalculationCoreServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CalculationCoreServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class CalculationCoreServiceFutureStub extends io.grpc.stub.AbstractStub<CalculationCoreServiceFutureStub> {
    private CalculationCoreServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CalculationCoreServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalculationCoreServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CalculationCoreServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_DO_CALC = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CalculationCoreServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CalculationCoreServiceImplBase serviceImpl, int methodId) {
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
              (io.grpc.stub.StreamObserver<com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.Response>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class CalculationCoreServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CalculationCoreServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.nobodyhub.payroll.core.service.proto.CalculationCoreProtocol.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CalculationCoreService");
    }
  }

  private static final class CalculationCoreServiceFileDescriptorSupplier
      extends CalculationCoreServiceBaseDescriptorSupplier {
    CalculationCoreServiceFileDescriptorSupplier() {}
  }

  private static final class CalculationCoreServiceMethodDescriptorSupplier
      extends CalculationCoreServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CalculationCoreServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (CalculationCoreServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CalculationCoreServiceFileDescriptorSupplier())
              .addMethod(getDoCalcMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
