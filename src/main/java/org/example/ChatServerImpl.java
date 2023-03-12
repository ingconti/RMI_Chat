package org.example;

import java.net.MalformedURLException;

import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


public class ChatServerImpl
        extends UnicastRemoteObject
        implements ChatServer
{
    public static void main (String[] args)
    {
        try {
            new ChatServerImpl ().go ();
        } catch (Exception e) {
            System.err.println (e);
        }
    }


    private void go () throws RemoteException {
//        Naming.rebind ("rmi://127.0.0.1/ChatService", this);

        // Bind the remote object's stub in the registry
        //DO NOT CALL Registry registry = LocateRegistry.getRegistry();
        Registry registry = LocateRegistry.createRegistry(Settings.PORT);
        try {
            registry.bind("ChatService", this);
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server ready");

    }


    private List receivers;


    public ChatServerImpl ()
            throws RemoteException
    {
        receivers = new ArrayList (10);
    }


    public void register (ChatReceiver cr)
            throws RemoteException
    {
        receivers.add (cr);
    }


    public void send (String message)
            throws RemoteException
    {
        System.out.println ("ChatServerImpl received: " + message);
        Iterator iter = receivers.iterator ();
        while (iter.hasNext ()) {
            ChatReceiver cr = (ChatReceiver) iter.next ();
            cr.receive (message);
        }
    }
}
