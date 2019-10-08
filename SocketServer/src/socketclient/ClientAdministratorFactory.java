/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketclient;

import socketserver.administrator.ClientAdministrator;
import socketserver.commoninterfaces.IPrintable;

/**
 *
 * @author alexander
 */
public class ClientAdministratorFactory {
    
    
    public static ClientAdministrator createClientAdministrator(String serverName, int port, IPrintable printer) {
        return new ClientAdministrator(serverName, port, printer);
    }
}
