/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.patterns.observer.AbsObservable;
import model.patterns.observer.IObserver;

/**
 *
 * @author aborbon
 */
public class WaitClients extends AbsObservable implements Runnable{
    
    // Socket del server que está a la espera de clientes
    private final ServerSocket serverSocket;

    /**
     * Constructor que recibe el puerto en el que se escuchará por clientes
     * @param port Número de puerto en el que s escuchará por clientes
     * @throws IOException Excepción lanzada si no se pudo iniciar el socket.
     */
    public WaitClients(int port) throws IOException {
        // Se inicializa el socket con el puerto dado
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                updateAll(client);
            } catch (IOException ex) {
                Logger.getLogger(WaitClients.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
