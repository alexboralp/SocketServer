/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.client;

import java.io.IOException;
import java.net.Socket;
import ssserver.msg.SSIMsg;

/**
 * Clase donde se maneja la conexión con el cliente
 * @author aborbon
 */
public class SSClient extends SSAbsClient<SSIMsg> {

    /**
     * Crea un cliente dada una conexión por medio de un socket
     * @param socket Socket de conexión con el cliente
     * @throws IOException Se lanza si hubo un problema al crear el input y el outout stream.
     */
    public SSClient(Socket socket) throws IOException {
        super(socket);
    }
    
}
