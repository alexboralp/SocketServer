/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.client;

import socketserver.commoninterfaces.ICleanable;
import socketserver.message.IMessage;

/**
 *
 * @author alexander
 */
public interface IClients extends ICleanable {
    public void sendMessageToAllClients(IMessage message);
}
