/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.server;

import socketserver.ServerAdministratorFactory;
import socketserver.administrator.ServerAdministrator;
import socketserver.commoninterfaces.IPrintable;
import vista.server.Server;

/**
 *
 * @author alexander
 */
public class ServerController implements IPrintable {

    private final Server server;
    private final ServerAdministrator serverAdministrator;
            
    public ServerController(Server server, int port) {
        this.server = server;
        
        print("Starting server on port " + port + ".");
        
        serverAdministrator = ServerAdministratorFactory.createServerAdministrator(port, this);
        
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
