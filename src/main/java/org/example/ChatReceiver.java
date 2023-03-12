package org.example;


import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ChatReceiver extends Remote {
    void receive (String message) throws RemoteException;
}
