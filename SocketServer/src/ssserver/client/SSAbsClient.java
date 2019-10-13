/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander
 * @param <T>
 */
public abstract class SSAbsClient<T extends Serializable> implements SSIClient<T>{
    // Conección con el cliente
    private Socket socket;
    
    // Canales de comunicación para la entrada y salida de mensajes
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private String id;
    
    /**
     * Crea un cliente dada una conexión por medio de un socket
     * @param socket Socket de conexión con el cliente
     * @throws IOException Se lanza si hubo un problema al crear el input y el outout stream.
     */
    public SSAbsClient(Socket socket) throws IOException {
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        id = socket.getRemoteSocketAddress().toString();
    }

    /**
     * Se devuelve un ObjectInputStream para la comunicación con el cliente.
     * @return Un ObjectInputStream para la comunicación con el cliente.
     */
    @Override
    public ObjectInputStream getIn() {
        return in;
    }

    /**
     * Se devuelve un ObjectOutputStream para la comunicación con el cliente.
     * @return Un ObjectOutputStream para la comunicación con el cliente.
     */
    @Override
    public ObjectOutputStream getOut() {
        return out;
    }

    /**
     * Se devuelve el socket de conección con el cliente.
     * @return El socket con el que se conectó el cliente.
     */
    @Override
    public Socket getSocket() {
        return socket;
    }

    /**
     * Define un nuevo socket de comunicación con el cliente.
     * @param socket El nuevo socket de comunicación.
     * @throws IOException Da una excepción si hubo un error al crear el input y el output streams.
     */
    @Override
    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        id = socket.getRemoteSocketAddress().toString();
    }
    
    /**
     * Devuelve si la conexión con el cliente está bien o se cerró,
     * @return true si el socket está abierto, false si está cerrado.
     */
    @Override
    public boolean isOk() {
        return !socket.isClosed();
    }
    
    /**
     * Envía un mensaje al cliente.
     * @param message Mensaje que se desea enviar
     * @throws IOException Excepción si se da un error al enviar el mensaje.
     */
    @Override
    public void sendMessage(T message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    @Override
    public void terminate() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SSAbsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
