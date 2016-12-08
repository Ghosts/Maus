package Server.Data;

import Client.ClientObject;

import java.util.HashMap;

public interface Repository {
    HashMap<String, ClientObject> CONNECTIONS = new HashMap<>();
}

