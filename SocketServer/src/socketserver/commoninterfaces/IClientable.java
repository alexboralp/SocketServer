/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.commoninterfaces;

import socketserver.client.IClient;

/**
 *
 * @author alexander
 */
public interface IClientable {
    public IClient getClient();

    public void setClient(IClient client);
}
