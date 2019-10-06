/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.socketserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Clase donde se maneja la conexión con el cliente
 * @author aborbon
 */
public class Client {
    // Conección con el cliente
    private Socket socket;
    
    // Canales de comunicación para la entrada y salida de mensajes
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * Crea un cliente dada una conexión por medio de un socket
     * @param socket Socket de conexión con el cliente
     * @throws IOException Se lanza si hubo un problema al crear el input y el outout stream.
     */
    public Client(Socket socket) throws IOException {
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * Se devuelve un ObjectInputStream para la comunicación con el cliente.
     * @return Un ObjectInputStream para la comunicación con el cliente.
     */
    public ObjectInputStream getIn() {
        return in;
    }

    /**
     * Se devuelve un ObjectOutputStream para la comunicación con el cliente.
     * @return Un ObjectOutputStream para la comunicación con el cliente.
     */
    public ObjectOutputStream getOut() {
        return out;
    }

    /**
     * Se devuelve el socket de conección con el cliente.
     * @return El socket con el que se conectó el cliente.
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Define un nuevo socket de comunicación con el cliente.
     * @param socket El nuevo socket de comunicación.
     * @throws IOException Da una excepción si hubo un error al crear el input y el output streams.
     */
    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }
    
    /**
     * Devuelve si la conexión con el cliente está bien o se cerró,
     * @return true si el socket está abierto, false si está cerrado.
     */
    public boolean isOk() {
        return !socket.isClosed();
    }
    
    /**
     * Envía un mensaje al cliente.
     * @param message Mensaje que se desea enviar
     * @throws IOException Excepción si se da un error al enviar el mensaje.
     */
    public void sendMessage(Object message) throws IOException {
        out.writeObject(message);
    }
}
