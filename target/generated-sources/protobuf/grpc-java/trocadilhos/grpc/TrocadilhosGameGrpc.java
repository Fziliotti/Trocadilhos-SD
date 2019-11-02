package trocadilhos.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: TrocadilhosGame.proto")
public final class TrocadilhosGameGrpc {

  private TrocadilhosGameGrpc() {}

  public static final String SERVICE_NAME = "trocadilhos.grpc.TrocadilhosGame";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<trocadilhos.grpc.TrocadilhoRequest,
      trocadilhos.grpc.APIResponse> METHOD_TROCADILHO =
      io.grpc.MethodDescriptor.<trocadilhos.grpc.TrocadilhoRequest, trocadilhos.grpc.APIResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "trocadilhos.grpc.TrocadilhosGame", "Trocadilho"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              trocadilhos.grpc.TrocadilhoRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              trocadilhos.grpc.APIResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TrocadilhosGameStub newStub(io.grpc.Channel channel) {
    return new TrocadilhosGameStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TrocadilhosGameBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new TrocadilhosGameBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TrocadilhosGameFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new TrocadilhosGameFutureStub(channel);
  }

  /**
   */
  public static abstract class TrocadilhosGameImplBase implements io.grpc.BindableService {

    /**
     */
    public void trocadilho(trocadilhos.grpc.TrocadilhoRequest request,
        io.grpc.stub.StreamObserver<trocadilhos.grpc.APIResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_TROCADILHO, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_TROCADILHO,
            asyncUnaryCall(
              new MethodHandlers<
                trocadilhos.grpc.TrocadilhoRequest,
                trocadilhos.grpc.APIResponse>(
                  this, METHODID_TROCADILHO)))
          .build();
    }
  }

  /**
   */
  public static final class TrocadilhosGameStub extends io.grpc.stub.AbstractStub<TrocadilhosGameStub> {
    private TrocadilhosGameStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TrocadilhosGameStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrocadilhosGameStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TrocadilhosGameStub(channel, callOptions);
    }

    /**
     */
    public void trocadilho(trocadilhos.grpc.TrocadilhoRequest request,
        io.grpc.stub.StreamObserver<trocadilhos.grpc.APIResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_TROCADILHO, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TrocadilhosGameBlockingStub extends io.grpc.stub.AbstractStub<TrocadilhosGameBlockingStub> {
    private TrocadilhosGameBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TrocadilhosGameBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrocadilhosGameBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TrocadilhosGameBlockingStub(channel, callOptions);
    }

    /**
     */
    public trocadilhos.grpc.APIResponse trocadilho(trocadilhos.grpc.TrocadilhoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_TROCADILHO, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TrocadilhosGameFutureStub extends io.grpc.stub.AbstractStub<TrocadilhosGameFutureStub> {
    private TrocadilhosGameFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TrocadilhosGameFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrocadilhosGameFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TrocadilhosGameFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<trocadilhos.grpc.APIResponse> trocadilho(
        trocadilhos.grpc.TrocadilhoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_TROCADILHO, getCallOptions()), request);
    }
  }

  private static final int METHODID_TROCADILHO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TrocadilhosGameImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TrocadilhosGameImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TROCADILHO:
          serviceImpl.trocadilho((trocadilhos.grpc.TrocadilhoRequest) request,
              (io.grpc.stub.StreamObserver<trocadilhos.grpc.APIResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class TrocadilhosGameDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return trocadilhos.grpc.TrocadilhosGameOuterClass.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TrocadilhosGameGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TrocadilhosGameDescriptorSupplier())
              .addMethod(METHOD_TROCADILHO)
              .build();
        }
      }
    }
    return result;
  }
}