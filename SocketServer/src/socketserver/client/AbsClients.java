/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.client;

import java.io.IOException;
import java.util.HashMap;
import socketserver.commoninterfaces.IList;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;

/**
 *
 * @author alexander
 */
public abstract class AbsClients implements IList<IClient>, IClients{
    private HashMap<String, IClient> clients;
    IPrintable printer;

    public AbsClients(IPrintable printer) {
        clients = new HashMap();
        this.printer = printer;
    }

    @Override
    public void add(IClient client) {
        clients.put(client.getId(), client);
    }

    @Override
    public void remove(IClient client) {
        clients.remove(client.getId());
    }
    
    @Override
    public void clean() {
        for (IClient client: clients.values()) {
            if (!client.isOk()) {
                clients.remove(client.getId());
            }
        }
    }

    @Override
    public void sendMessageToAllClients(IMessage message) {
        for (IClient client: clients.values()) {
            if (client.isOk()) {
                try {
                    client.sendMessage(message);
                } catch (IOException ex) {
                    printer.printError("AbsClient: " + "Error al enviar el mensaje al cliente " + client.getId() + ".");
                }
            }
        }
    }
}
