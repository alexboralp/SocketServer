/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.client;

import observerserver.administrator.ObserverClientAdministrator;
import observerclient.ObserverClientAdministratorFactory;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import vista.client.Client;

/**
 *
 * @author alexander
 */
public class ClientController implements IPrintable {

    Client client;
    private ObserverClientAdministrator clientAdministrator;
            
    public ClientController(Client client, String server, int port) {
        
        this.client = client;
        
        print("Connecting server " + server + " on port " + port + ".");
        
        clientAdministrator = ObserverClientAdministratorFactory.createObserverClientAdministrator(server, port, this);
        
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ObserverClientAdministrator getObserverClientAdministrator() {
        return clientAdministrator;
    }

    public void setObserverClientAdministrator(ObserverClientAdministrator clientAdministrator) {
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
