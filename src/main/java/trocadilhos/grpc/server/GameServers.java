package trocadilhos.grpc.server;


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

    public static class ServerStatus {

        private String ip;
        private int port;
        private boolean isOnline;
        private BrazilRegion region;

        public ServerStatus() {
        }

        public ServerStatus(String ip, int port, boolean isOnline, BrazilRegion region) {
            this.ip = ip;
            this.port = port;
            this.isOnline = isOnline;
            this.region = region;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public boolean getOnline() {
            return isOnline;
        }

        public void setOnline(boolean online) {
            isOnline = online;
        }

        public BrazilRegion getRegion() {
            return region;
        }

        public void setRegion(BrazilRegion region) {
            this.region = region;
        }
    }

}