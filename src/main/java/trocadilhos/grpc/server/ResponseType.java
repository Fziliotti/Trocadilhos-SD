package trocadilhos.grpc.server;

public enum ResponseType {
    INVALID_PUN,
    INVALID_POLL,
    NORMAL_MESSAGE,
    PUN_TIME,
    POLL_TIME,
    WAIT_NEXT_STEP
}
