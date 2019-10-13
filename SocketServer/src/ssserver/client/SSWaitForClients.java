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
    SSIPrintable printer;
    Thread thread;

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
                updateAll(new SSClient(socketClient));
                printer.print("WaitClients: " + "New client accepted: " + socketClient.getRemoteSocketAddress().toString() + ".");
            } catch (IOException ex) {
                printer.printError("WaitClients: " + ex.getMessage());
            }
        }
    }
    
}
