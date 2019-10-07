/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import socketserver.patterns.observer.AbsObservable;
import socketserver.commoninterfaces.IPrintable;

/**
 *
 * @author aborbon
 */
public class WaitClients extends AbsObservable implements Runnable{
    
    // Socket del server que está a la espera de clientes
    private final ServerSocket serverSocket;
    IPrintable printer;

    /**
     * Constructor que recibe el puerto en el que se escuchará por clientes
     * @param port Número de puerto en el que s escuchará por clientes
     * @param printer
     * @throws IOException Excepción lanzada si no se pudo iniciar el socket.
     */
    public WaitClients(int port, IPrintable printer) throws IOException {
        // Se inicializa el socket con el puerto dado
        serverSocket = new ServerSocket(port);
        this.printer = printer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socketClient = serverSocket.accept();
                updateAll(new Client(socketClient));
                printer.print("New client accepted: " + socketClient.getInetAddress().toString() + ".");
            } catch (IOException ex) {
                Logger.getLogger(WaitClients.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
