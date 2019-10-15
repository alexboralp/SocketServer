/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import ssserver.patterns.observer.SSAbsObservable;
import ssserver.commoninterfaces.SSIPrintable;

/**
 *
 * @author aborbon
 */
public class SSWaitForClients extends SSAbsObservable implements Runnable{
    
    // Socket del server que está a la espera de clientes
    private final ServerSocket serverSocket;
    private final SSIPrintable printer;
    private Thread thread;

    /**
     * Constructor que recibe el puerto en el que se escuchará por clientes
     * @param port Número de puerto en el que s escuchará por clientes
     * @param printer
     * @throws IOException Excepción lanzada si no se pudo iniciar el socket.
     */
    public SSWaitForClients(int port, SSIPrintable printer) throws IOException {
        // Se inicializa el socket con el puerto dado
        serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);
        this.printer = printer;
    }
    
    public void startListening() {
        thread = new Thread(this);
        thread.start();
    }
    
    public void stopListening() {
        thread.interrupt();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socketClient = serverSocket.accept();
                printer.print("SSWaitForClients: " + "New client accepted: " + socketClient.getRemoteSocketAddress().toString() + ".");
                SSIClient client = new SSClient(socketClient);
                client.setId(socketClient.getRemoteSocketAddress().toString());
                updateAll(client);
            } catch (IOException ex) {
                printer.printError("SSWaitForClients: " + ex.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return "SSWaitForClients{" + "serverSocket=" + serverSocket + ", printer=" + printer + ", thread=" + thread + '}';
    }
    
    
    
}
