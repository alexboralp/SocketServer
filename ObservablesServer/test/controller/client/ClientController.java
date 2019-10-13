/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.client;

import ooserver.admin.OOClientAdmin;
import ooclient.OOClientAdminFact;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.commoninterfaces.OOIPrintable;
import vista.client.ClientGUI;

/**
 *
 * @author alexander
 */
public class ClientController implements OOIPrintable {

    ClientGUI client;
    private OOClientAdmin clientAdministrator;
            
    public ClientController(ClientGUI client, String server, int port) {
        
        this.client = client;
        
        print("Connecting server " + server + " on port " + port + ".");
        
        clientAdministrator = OOClientAdminFact.createObserverClientAdministrator(server, port, this);
        
    }

    public ClientGUI getClient() {
        return client;
    }

    public void setClient(ClientGUI client) {
        this.client = client;
    }

    public OOClientAdmin getObserverClientAdministrator() {
        return clientAdministrator;
    }

    public void setObserverClientAdministrator(OOClientAdmin clientAdministrator) {
        this.clientAdministrator = clientAdministrator;
    }
    
    public void sendMessageToServer(OOIMsg message) {
        clientAdministrator.sendMessage(message);
    }
    
    @Override
    public void print(String message) {
        client.print(message + "\n");
    }

    @Override
    public void printError(String message) {
        client.print("ERROR: " + message + "\n");
    }
    
}
