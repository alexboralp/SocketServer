/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import observerserver.ObserverServerAdministratorFactory;
import observerserver.administrator.ObserverServerAdministrator;
import socketserver.commoninterfaces.IPrintable;
import vista.ServerGUI;

/**
 *
 * @author alexander
 */
public class ServerController implements IPrintable {

    private final ServerGUI server;
    private final ObserverServerAdministrator serverAdministrator;
            
    public ServerController(ServerGUI server, int port) {
        this.server = server;
        
        print("Starting server on port " + port + ".");
        
        serverAdministrator = ObserverServerAdministratorFactory.createObserverServerAdministrator(port, this);
        
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
