/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssclient.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ssserver.patterns.observer.SSAbsObservable;
import ssserver.commoninterfaces.SSIPrintable;
import ssserver.commoninterfaces.SSIOkable;
import ssserver.msg.SSIMsg;

/**
 *
 * @author aborbon
 */
public class SSWaitMsgsFromServer extends SSAbsObservable implements Runnable, SSIOkable{
    private String serverName;
    private int port;
    
    private Socket socket;
    private boolean ok;
    private SSIMsg message;
    
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    
    private final SSIPrintable printer;
    private Thread thread;

    public SSWaitMsgsFromServer(String serverName, int port, SSIPrintable printer) {
        this.printer = printer;
        ok = startSocket(serverName, port);
    }
    
    public void startListening() {
        if (ok){
            thread = new Thread(this);
            thread.start();
        }
    }
    
    public void stop() {
        thread.interrupt();
        this.closeConnection();
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        closeConnection();
        this.port = port;
        ok = startSocket(serverName, port);
    }
    
    public void setServerName(String serverName) {
        closeConnection();
        this.serverName = serverName;
        ok = startSocket(serverName, port);
    }
    
    public void setServerNameAndPort(String serverName, int port) {
        closeConnection();
        this.serverName = serverName;
        this.port = port;
        ok = startSocket(serverName, port);
    }

    @Override
    public boolean isOk() {
        return ok;
    }

    public SSIMsg getResponse() {
        return message;
    }

    public void setResponse(SSIMsg message) {
        this.message = message;
    }
    
    public void sendMessage(SSIMsg message) {
        try {
            message.setId(socket.getLocalSocketAddress().toString());
            printer.print("SSWaitMsgsFromServer: " + "Sending the message: " + message.toString());
            out.writeObject(message);
            out.flush();
        } catch (IOException ex) {
            printer.printError("SSWaitMsgsFromServer: " + ex.getMessage());
        }
    }
    
    public void closeConnection() {
        try {
            if (socket.isConnected()) {
                out.writeObject(null);
                in.close();
                out.close();
                socket.close();
            }
            ok = false;
        } catch (IOException ex) {
            printer.printError("SSWaitMsgsFromServer: " + ex.getMessage());
        }
    }
    
    private boolean startSocket(String serverName, int port) {
        try {
            socket = new Socket(serverName, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            printer.printError("SSWaitMsgsFromServer: Could not listen on server " + serverName + ", port: " + port + ".");
            ok = false;
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        try {
            while (ok && (message = (SSIMsg) in.readObject()) != null) {
                printer.print("SSWaitMsgsFromServer: " + "New message from server: " + message.toString());
                updateAll(message);
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SSWaitMsgsFromServer.class.getName()).log(Level.SEVERE, null, ex);
            printer.printError("SSWaitMsgsFromServer: " + ex.getMessage());
        }
    }
}
