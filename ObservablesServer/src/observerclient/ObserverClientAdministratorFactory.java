/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerclient;

import observerserver.administrator.ObserverClientAdministrator;
import socketserver.commoninterfaces.IPrintable;

/**
 *
 * @author alexander
 */
public class ObserverClientAdministratorFactory {
    
    
    public static ObserverClientAdministrator createObserverClientAdministrator(String serverName, int port, IPrintable printer) {
        return new ObserverClientAdministrator(serverName, port, printer);
    }
}
