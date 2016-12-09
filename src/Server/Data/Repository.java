package Server.Data;

import Server.ClientObject;

import java.util.HashMap;

public interface Repository {
    HashMap<String, ClientObject> CONNECTIONS = new HashMap<>();
}

