/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.client;

import java.io.Serializable;
import ssserver.commoninterfaces.SSICleanable;

/**
 *
 * @author alexander
 */
public interface SSIClients extends SSICleanable {
    public void sendMessageToAllClients(Serializable message);
}
