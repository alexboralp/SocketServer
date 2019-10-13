/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.commoninterfaces;

import ssserver.client.SSIClient;

/**
 *
 * @author alexander
 */
public interface SSIClientable {
    public SSIClient getClient();

    public void setClient(SSIClient client);
}
