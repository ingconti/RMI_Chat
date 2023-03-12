package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.MalformedURLException;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ChatReceiverImpl extends UnicastRemoteObject implements ChatReceiver
{
    public static void main (String[] args)
    {
        try {
            new ChatReceiverImpl ().go ();
        } catch (Exception e) {
            System.err.println (e);
        }
    }


    private void go ()
            throws IOException, MalformedURLException,
            NotBoundException, RemoteException
    {
        // Getting the registry
        Registry registry = null;

        //was: ChatServer cs = (ChatServer) Naming.lookup ("rmi://127.0.0.1/ChatService");

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


    public ChatReceiverImpl ()
            throws RemoteException
    {
    }


    public void receive (String message)
            throws RemoteException
    {
        System.out.println (message);
    }
}
