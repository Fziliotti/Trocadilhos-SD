package trocadilhos.grpc.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class GameServers {

    private List<ServerStatus> serverStatusList;

    public GameServers(List<ServerStatus> serverStatusList) {
        this.serverStatusList = serverStatusList;
    }

    public GameServers() {
    }

    public List<ServerStatus> getServerStatusList() {
        return serverStatusList;
    }

    public void setServerStatusList(List<ServerStatus> serverStatusList) {
        this.serverStatusList = serverStatusList;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServerStatus {

        private String ip;
        private int port;
        private boolean isOnline;
        private BrazilRegion region;
    }

}