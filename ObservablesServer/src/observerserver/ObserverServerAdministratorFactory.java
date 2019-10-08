/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver;

import observerserver.administrator.ObserverServerAdministrator;
import socketserver.commoninterfaces.IPrintable;

/**
 *
 * @author alexander
 */
public class ObserverServerAdministratorFactory {
    public static ObserverServerAdministrator createObserverServerAdministrator(int port, IPrintable printer) {
        return new ObserverServerAdministrator(port, printer);
    }
}
