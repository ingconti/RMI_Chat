package org.example;

import org.example.ChatReceiver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote{
    public void register (ChatReceiver cr) throws RemoteException;
    public void send (String message) throws RemoteException;
}
