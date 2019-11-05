// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TrocadilhosGame.proto

package trocadilhos.grpc;

public final class TrocadilhosGameOuterClass {
  private TrocadilhosGameOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_TrocadilhoRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_TrocadilhoRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_HelloResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_HelloResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_LoginToMasterRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_LoginToMasterRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_LoginToGameRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_LoginToGameRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_LoginToGameResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_LoginToGameResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_LogoutRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_LogoutRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_LogoutResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_LogoutResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_LoginToMasterResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_LoginToMasterResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_APIRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_APIRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_APIResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_APIResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_ServerDetailsRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_ServerDetailsRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trocadilhos_grpc_ServerDetailsResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trocadilhos_grpc_ServerDetailsResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\025TrocadilhosGame.proto\022\020trocadilhos.grp" +
      "c\"(\n\021TrocadilhoRequest\022\023\n\013description\030\001 " +
      "\001(\t\"!\n\rHelloResponse\022\020\n\010greeting\030\001 \001(\t\"(" +
      "\n\024LoginToMasterRequest\022\020\n\010nickname\030\001 \001(\t" +
      "\";\n\022LoginToGameRequest\022\020\n\010nickname\030\001 \001(\t" +
      "\022\023\n\013reconnected\030\002 \001(\t\"&\n\023LoginToGameResp" +
      "onse\022\017\n\007message\030\001 \001(\t\"!\n\rLogoutRequest\022\020" +
      "\n\010nickname\030\001 \001(\t\"\"\n\016LogoutResponse\022\020\n\010ni" +
      "ckname\030\001 \001(\t\"1\n\025LoginToMasterResponse\022\n\n" +
      "\002ip\030\001 \001(\t\022\014\n\004port\030\002 \001(\t\"+\n\nAPIRequest\022\017\n",
      "\007message\030\001 \001(\t\022\014\n\004from\030\002 \001(\t\":\n\013APIRespo" +
      "nse\022\017\n\007message\030\001 \001(\t\022\014\n\004from\030\002 \001(\t\022\014\n\004ty" +
      "pe\030\003 \001(\t\"\026\n\024ServerDetailsRequest\"E\n\025Serv" +
      "erDetailsResponse\022\023\n\013playersList\030\001 \001(\t\022\027" +
      "\n\017amountOfPlayers\030\002 \001(\t2\216\004\n\017TrocadilhosG" +
      "ame\022T\n\016sendTrocadilho\022#.trocadilhos.grpc" +
      ".TrocadilhoRequest\032\035.trocadilhos.grpc.AP" +
      "IResponse\022`\n\rloginToMaster\022&.trocadilhos" +
      ".grpc.LoginToMasterRequest\032\'.trocadilhos" +
      ".grpc.LoginToMasterResponse\022Z\n\013loginToGa",
      "me\022$.trocadilhos.grpc.LoginToGameRequest" +
      "\032%.trocadilhos.grpc.LoginToGameResponse\022" +
      "S\n\016logoutToMaster\022\037.trocadilhos.grpc.Log" +
      "outRequest\032 .trocadilhos.grpc.LogoutResp" +
      "onse\022H\n\006logout\022\037.trocadilhos.grpc.Logout" +
      "Request\032\035.trocadilhos.grpc.APIResponse\022H" +
      "\n\005start\022\034.trocadilhos.grpc.APIRequest\032\035." +
      "trocadilhos.grpc.APIResponse(\0010\001B\002P\001b\006pr" +
      "oto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_trocadilhos_grpc_TrocadilhoRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_trocadilhos_grpc_TrocadilhoRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_TrocadilhoRequest_descriptor,
        new java.lang.String[] { "Description", });
    internal_static_trocadilhos_grpc_HelloResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_trocadilhos_grpc_HelloResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_HelloResponse_descriptor,
        new java.lang.String[] { "Greeting", });
    internal_static_trocadilhos_grpc_LoginToMasterRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_trocadilhos_grpc_LoginToMasterRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_LoginToMasterRequest_descriptor,
        new java.lang.String[] { "Nickname", });
    internal_static_trocadilhos_grpc_LoginToGameRequest_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_trocadilhos_grpc_LoginToGameRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_LoginToGameRequest_descriptor,
        new java.lang.String[] { "Nickname", "Reconnected", });
    internal_static_trocadilhos_grpc_LoginToGameResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_trocadilhos_grpc_LoginToGameResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_LoginToGameResponse_descriptor,
        new java.lang.String[] { "Message", });
    internal_static_trocadilhos_grpc_LogoutRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_trocadilhos_grpc_LogoutRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_LogoutRequest_descriptor,
        new java.lang.String[] { "Nickname", });
    internal_static_trocadilhos_grpc_LogoutResponse_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_trocadilhos_grpc_LogoutResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_LogoutResponse_descriptor,
        new java.lang.String[] { "Nickname", });
    internal_static_trocadilhos_grpc_LoginToMasterResponse_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_trocadilhos_grpc_LoginToMasterResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_LoginToMasterResponse_descriptor,
        new java.lang.String[] { "Ip", "Port", });
    internal_static_trocadilhos_grpc_APIRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_trocadilhos_grpc_APIRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_APIRequest_descriptor,
        new java.lang.String[] { "Message", "From", });
    internal_static_trocadilhos_grpc_APIResponse_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_trocadilhos_grpc_APIResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_APIResponse_descriptor,
        new java.lang.String[] { "Message", "From", "Type", });
    internal_static_trocadilhos_grpc_ServerDetailsRequest_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_trocadilhos_grpc_ServerDetailsRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_ServerDetailsRequest_descriptor,
        new java.lang.String[] { });
    internal_static_trocadilhos_grpc_ServerDetailsResponse_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_trocadilhos_grpc_ServerDetailsResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trocadilhos_grpc_ServerDetailsResponse_descriptor,
        new java.lang.String[] { "PlayersList", "AmountOfPlayers", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
