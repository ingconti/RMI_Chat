package org.example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class ChatServerApp  extends UnicastRemoteObject implements ChatServer
{
    public static void main (String[] args)
    {
        try {
            new ChatServerApp().go ();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void go () throws RemoteException {

        // Bind the remote object's stub in the registry
        //DO NOT CALL Registry registry = LocateRegistry.getRegistry();
        Registry registry = LocateRegistry.createRegistry(Settings.PORT);
        try {
            registry.bind("ChatService", this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server ready");

    }


    private final List<ChatClient> receivers;

    public ChatServerApp() throws RemoteException {
        this.receivers = new ArrayList<>();
    }


    public void register (ChatClient cr) throws RemoteException {
        this.receivers.add(cr);
    }


    public void send (String message) throws RemoteException {
        System.out.println ("server received: " + message);
        for (ChatClient receiver : receivers) {
            receiver.receive(message);
        }
    }
}
