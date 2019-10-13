/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.client;

import ssserver.commoninterfaces.SSICleanable;
import ssserver.msg.SSIMsg;

/**
 *
 * @author alexander
 */
public interface SSIClients extends SSICleanable {
    public void sendMessageToAllClients(SSIMsg message);
}
