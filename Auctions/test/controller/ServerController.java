/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ooserver.OOServerAdminFact;
import ooserver.admin.OOServerAdmin;
import ooserver.commoninterfaces.OOIPrintable;
import vista.Server;

/**
 *
 * @author alexander
 */
public class ServerController implements OOIPrintable {

    private final Server server;
    private final OOServerAdmin serverAdministrator;
            
    public ServerController(Server server, int port) {
        this.server = server;
        
        print("Starting server on port " + port + ".");
        
        serverAdministrator = OOServerAdminFact.createObserverServerAdministrator(port, this);
        
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
