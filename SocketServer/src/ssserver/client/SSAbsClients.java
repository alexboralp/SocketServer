/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.client;

import java.io.IOException;
import java.util.HashMap;
import ssserver.commoninterfaces.SSIPrintable;
import ssserver.commoninterfaces.SSIList;
import ssserver.msg.SSIMsg;

/**
 *
 * @author alexander
 */
public abstract class SSAbsClients implements SSIList<SSIClient>, SSIClients{
    private final HashMap<String, SSIClient> clients;
    private final SSIPrintable printer;

    public SSAbsClients(SSIPrintable printer) {
        clients = new HashMap();
        this.printer = printer;
    }

    @Override
    public void add(SSIClient client) {
        clients.put(client.getId(), client);
    }

    @Override
    public void remove(SSIClient client) {
        clients.remove(client.getId());
    }
    
    @Override
    public SSIClient get(String id) {
        return clients.get(id);
    }
    
    public boolean containsKey(String key) {
        return clients.containsKey(key);
    }
    
    @Override
    public void clean() {
        for (SSIClient client: clients.values()) {
            if (!client.isOk()) {
                clients.remove(client.getId());
            }
        }
    }

    @Override
    public void sendMessageToAllClients(SSIMsg message) {
        for (SSIClient client: clients.values()) {
            if (client.isOk()) {
                try {
                    client.sendMessage(message);
                } catch (IOException ex) {
                    printer.printError("SSAbsClients: " + "Error al enviar el mensaje al cliente " + client.getId() + ".");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "SSAbsClients{" + "clients=" + clients + ", printer=" + printer + '}';
    }
    
    
}
