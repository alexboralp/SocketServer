/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.client;

import socketclient.ClientAdministrator;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import vista.client.Client;

/**
 *
 * @author alexander
 */
public class ClientController implements IPrintable {

    Client client;
    private ClientAdministrator clientAdministrator;
            
    public ClientController(Client client, String server, int port) {
        
        this.client = client;
        
        print("Connecting server " + server + " on port " + port + ".");
        
        clientAdministrator = ClientAdministrator.createClientAdministrator(server, port, this);
        
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientAdministrator getClientAdministrator() {
        return clientAdministrator;
    }

    public void setClientAdministrator(ClientAdministrator clientAdministrator) {
        this.clientAdministrator = clientAdministrator;
    }
    
    public void sendMessageToServer(IMessage message) {
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
