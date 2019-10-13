/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.server;

import ssserver.SSServerAdminFact;
import ssserver.admin.SSServerAdmin;
import vista.server.ServerGUI;
import ssserver.commoninterfaces.SSIPrintable;

/**
 *
 * @author alexander
 */
public class ServerController implements SSIPrintable {

    private final ServerGUI server;
    private final SSServerAdmin serverAdministrator;
            
    public ServerController(ServerGUI server, int port) {
        this.server = server;
        
        print("Starting server on port " + port + ".");
        
        serverAdministrator = SSServerAdminFact.createServerAdministrator(port, this);
        
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
