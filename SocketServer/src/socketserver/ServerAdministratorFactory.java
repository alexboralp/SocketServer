/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver;

import socketserver.administrator.ServerAdministrator;
import socketserver.commoninterfaces.IPrintable;

/**
 *
 * @author alexander
 */
public class ServerAdministratorFactory {
    
    
    public static ServerAdministrator createServerAdministrator(int port, IPrintable printer) {
        return new ServerAdministrator(port, printer);
    }
}
