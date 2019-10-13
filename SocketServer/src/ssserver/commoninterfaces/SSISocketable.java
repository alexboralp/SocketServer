/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.commoninterfaces;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author alexander
 */
public interface SSISocketable {
    public Socket getSocket();
    public void setSocket(Socket socket) throws IOException;
}
