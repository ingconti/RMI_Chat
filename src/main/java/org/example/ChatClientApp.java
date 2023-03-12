package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ChatClientApp extends UnicastRemoteObject implements ChatClient {
    public static void main (String[] args) {
        try {
            new ChatClientApp().go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void go () throws Exception {
        // Getting the registry
        Registry registry;

        registry = LocateRegistry.getRegistry("127.0.0.1", Settings.PORT);

        ChatServer cs;

        // Looking up the registry for the remote object
        cs = (ChatServer) registry.lookup("ChatService");

        cs.register (this);

        BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
        String message;

        while ( (message = br.readLine ()) != null) {
            cs.send(message);
        }
    }


    public ChatClientApp() throws RemoteException {

    }


    public void receive (String message) throws RemoteException {
        System.out.println (message);
    }
}
