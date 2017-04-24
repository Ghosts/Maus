package Server;


public class ServerSettings {
    private static String CONNECTION_IP = "localhost";
    private static boolean SHOW_NOTIFICATIONS = true;
    private static boolean BACKGROUND_PERSISTENT = false;
    private static int REFRESH_RATE = 12000;
    private static int MAX_CONNECTIONS = 999;
    private static int PORT = 22122;
    private static boolean SOUND = true;

    public static boolean getSOUND() {
        return SOUND;
    }

    public static void setSOUND(boolean SOUND) {
        ServerSettings.SOUND = SOUND;
    }

    public static String getConnectionIp() {
        return CONNECTION_IP;
    }

    public static void setConnectionIp(String connectionIp) {
        CONNECTION_IP = connectionIp;
    }

    public static int getPORT() {
        return PORT;
    }

    public static void setPORT(int PORT) {
        ServerSettings.PORT = PORT;
    }

    public static boolean getShowNotifications() {
        return SHOW_NOTIFICATIONS;
    }

    public static void setShowNotifications(boolean showNotifications) {
        SHOW_NOTIFICATIONS = showNotifications;
    }

    public static boolean getBackgroundPersistent() {
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

}
