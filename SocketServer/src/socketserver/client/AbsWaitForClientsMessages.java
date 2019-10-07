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

/**
 *
 * @author alexander
 */
public abstract class AbsWaitForClientsMessages implements IList<WaitForClientMessages>, IWaitForClientsMessages{
    private final HashMap<String, WaitForClientMessages> waitForClientsMessages;
    private final IPrintable printer;

    public AbsWaitForClientsMessages(IPrintable printer) {
        waitForClientsMessages = new HashMap();
        this.printer = printer;
    }

    @Override
    public void add(WaitForClientMessages waitForClientMessages) {
        waitForClientsMessages.put(waitForClientMessages.getClient().getId(), waitForClientMessages);
    }

    @Override
    public void remove(WaitForClientMessages waitForClientMessages) {
        waitForClientsMessages.remove(waitForClientMessages.getClient().getId());
    }
    
    @Override
    public void clean() {
        for (WaitForClientMessages waitForClientMessages: waitForClientsMessages.values()) {
            if (!waitForClientMessages.getClient().isOk()) {
                remove(waitForClientMessages);
            }
        }
    }
    
    @Override
    public void stopAll(){
        for (WaitForClientMessages waitForClientMessages: waitForClientsMessages.values()) {
            try {
                waitForClientMessages.closeComunication();
            } catch (IOException ex) {
                printer.printError("AbsWaitForClientsMessages: " + "Could not close communication to client " + waitForClientMessages.getClient().getId() + ".");
            }
        }
    }
}
