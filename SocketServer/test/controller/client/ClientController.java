/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.client;

import ssclient.SSClientAdminFact;
import ssserver.admin.SSClientAdmin;
import vista.client.ClientGUI;
import ssserver.commoninterfaces.SSIPrintable;
import ssserver.msg.SSIMsg;

/**
 *
 * @author alexander
 */
public class ClientController implements SSIPrintable {

    ClientGUI client;
    private SSClientAdmin clientAdministrator;
            
    public ClientController(ClientGUI client, String server, int port) {
        
        this.client = client;
        
        print("Connecting server " + server + " on port " + port + ".");
        
        clientAdministrator = SSClientAdminFact.createClientAdministrator(server, port, this);
        
    }

    public ClientGUI getClient() {
        return client;
    }

    public void setClient(ClientGUI client) {
        this.client = client;
    }

    public SSClientAdmin getClientAdministrator() {
        return clientAdministrator;
    }

    public void setClientAdministrator(SSClientAdmin clientAdministrator) {
        this.clientAdministrator = clientAdministrator;
    }
    
    public void sendMessageToServer(SSIMsg message) {
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
