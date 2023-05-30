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
        fixIt(); // for NON-localhost
        try {
            new ChatServerApp().startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startServer() throws RemoteException {

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


    private final List<ChatClient> chatClients;

    public ChatServerApp() throws RemoteException {
        this.chatClients = new ArrayList<>();
    }


    public void login(ChatClient cc) throws RemoteException {
        System.out.println("arrived " + cc.toString());
        this.chatClients.add(cc);
    }


    public void send (String message) throws RemoteException {
        System.out.println ("server received: " + message);
        for (ChatClient cc : chatClients) {
            cc.receive(message.toUpperCase());
        }
    }

    static void fixIt(){
        System.setProperty("java.rmi.server.hostname","192.168.1.12");
    }

}
