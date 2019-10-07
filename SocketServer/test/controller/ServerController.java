/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import socketserver.ServerAdministrator;
import socketserver.commoninterfaces.IPrintable;
import vista.Server;

/**
 *
 * @author alexander
 */
public class ServerController implements IPrintable {

    private Server server;
    private ServerAdministrator serverAdministrator;
            
    public ServerController(Server server, int port) {
        this.server = server;
        
        print("Starting server on port " + port + ".");
        
        serverAdministrator = new ServerAdministrator(port, this);
        
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
