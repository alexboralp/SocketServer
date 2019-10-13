/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.server;

import ooserver.admin.OOServerAdmin;
import ooserver.OOServerAdminFact;
import ooserver.commoninterfaces.OOIPrintable;
import vista.server.ServerGUI;

/**
 *
 * @author alexander
 */
public class ServerController implements OOIPrintable {

    private final ServerGUI server;
    private final OOServerAdmin observerServerAdministrator;
            
    public ServerController(ServerGUI server, int port) {
        this.server = server;
        
        print("Starting server on port " + port + ".");
        
        observerServerAdministrator = OOServerAdminFact.createObserverServerAdministrator(port, this);
        
    }
    
    @Override
    public void print(String message) {
        server.print(message + "\n");
    }

    @Override
    public void printError(String message) {
        server.print("ERROR: " + message + "\n");
    }
    
}
