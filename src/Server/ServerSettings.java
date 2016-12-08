package Server;


public class ServerSettings {
    private static boolean SHOW_NOTIFICATIONS = true;
    private static boolean BACKGROUND_PERSISTENT = true;
    private static int REFRESH_RATE = 12000;
    private static int MAX_CONNECTIONS = Integer.MAX_VALUE;
    private static boolean P2P_CONNECTIONS = true;
    private static boolean BEACON_STATUS = false;
    private static int PORT = 22122;

    public static boolean isBeaconStatus() {
        return BEACON_STATUS;
    }

    public static void setBeaconStatus(boolean beaconStatus) {
        BEACON_STATUS = beaconStatus;
    }

    public static int getPORT() {
        return PORT;
    }

    public static void setPORT(int PORT) {
        ServerSettings.PORT = PORT;
    }

    public static boolean isShowNotifications() {
        return SHOW_NOTIFICATIONS;
    }

    public static void setShowNotifications(boolean showNotifications) {
        SHOW_NOTIFICATIONS = showNotifications;
    }

    public static boolean isBackgroundPersistent() {
        return BACKGROUND_PERSISTENT;
    }

    public static void setBackgroundPersistent(boolean backgroundPersistent) {
        BACKGROUND_PERSISTENT = backgroundPersistent;
    }

    public static int getRefreshRate() {
        return REFRESH_RATE;
    }

    public static void setRefreshRate(int refreshRate) {
        REFRESH_RATE = refreshRate;
    }

    public static int getMaxConnections() {
        return MAX_CONNECTIONS;
    }

    public static void setMaxConnections(int maxConnections) {
        MAX_CONNECTIONS = maxConnections;
    }

    public static boolean isP2pConnections() {
        return P2P_CONNECTIONS;
    }

    public static void setP2pConnections(boolean p2pConnections) {
        P2P_CONNECTIONS = p2pConnections;
    }

}
