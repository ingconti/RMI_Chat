package org.example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


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
       // eventually..... this.timer = startTimer();

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


    Timer timer;

     private Timer startTimer(){
         timer = new Timer("Timer");

         TimerTask task = new TimerTask() {
             public void run() {
                 String s = new Date().toString();
                 try {
                    send(s);
                 } catch (Exception e) {
                     throw new RuntimeException(e);
                 }
             }
         };

        long delay = 0;
        long period = 1000L;
        timer.schedule(task, delay, period);
        return timer;
    } // startTimer
}
