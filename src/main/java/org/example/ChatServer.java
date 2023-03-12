package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote{
    public void register (ChatClient cr) throws RemoteException;
    public void send (String message) throws RemoteException;
}
