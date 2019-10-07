/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.commoninterfaces;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author alexander
 */
public interface ISocketable {
    public Socket getSocket();
    public void setSocket(Socket socket) throws IOException;
}
