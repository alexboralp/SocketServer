/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketclient.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import socketserver.commoninterfaces.IOk;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.AbsObservable;

/**
 *
 * @author aborbon
 */
public class WaitMessagesFromServer extends AbsObservable implements Runnable, IOk{
    private String serverName;
    private int port;
    
    private Socket socket;
    private boolean ok;
    private IMessage message;
    
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    
    IPrintable printer;
    private Thread thread;

    public WaitMessagesFromServer(String serverName, int port, IPrintable printer) {
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

    public boolean isOk() {
        return ok;
    }

    public IMessage getResponse() {
        return message;
    }

    public void setResponse(IMessage message) {
        this.message = message;
    }
    
    public void sendMessage(IMessage message) {
        try {
            message.setId(socket.getLocalSocketAddress().toString());
            printer.print("Sending the message: " + message.toString());
            out.writeObject(message);
            out.flush();
        } catch (IOException ex) {
            printer.printError("WaitMessagesFromServer: " + ex.getMessage());
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
            printer.printError("WaitMessagesFromServer: " + ex.getMessage());
        }
    }
    
    private boolean startSocket(String serverName, int port) {
        try {
            socket = new Socket(serverName, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            printer.printError("WaitMessagesFromServer: Could not listen on server " + serverName + ", port: " + port + ".");
            ok = false;
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        try {
            while (ok && (message = (IMessage) in.readObject()) != null) {
                printer.print("New message from server: " + message.toString());
                updateAll(message);
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(WaitMessagesFromServer.class.getName()).log(Level.SEVERE, null, ex);
            printer.printError("WaitMessagesFromServer: " + ex.getMessage());
        }
    }
}
