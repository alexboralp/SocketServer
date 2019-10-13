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

/**
 *
 * @author alexander
 */
public abstract class SSAbsWaitForClientsMsgs implements SSIList<SSWaitForClientMsgs>, SSIWaitForClientsMsgs{
    private final HashMap<String, SSWaitForClientMsgs> waitForClientsMessages;
    private final SSIPrintable printer;

    public SSAbsWaitForClientsMsgs(SSIPrintable printer) {
        waitForClientsMessages = new HashMap();
        this.printer = printer;
    }

    @Override
    public void add(SSWaitForClientMsgs waitForClientMessages) {
        waitForClientsMessages.put(waitForClientMessages.getClient().getId(), waitForClientMessages);
    }

    @Override
    public void remove(SSWaitForClientMsgs waitForClientMessages) {
        waitForClientsMessages.remove(waitForClientMessages.getClient().getId());
    }

    public void remove(String clientId) {
        waitForClientsMessages.remove(clientId);
    }
    
    @Override
    public SSWaitForClientMsgs get(String id) {
        return waitForClientsMessages.get(id);
    }
    
    @Override
    public void clean() {
        for (SSWaitForClientMsgs waitForClientMessages: waitForClientsMessages.values()) {
            if (!waitForClientMessages.getClient().isOk()) {
                remove(waitForClientMessages);
            }
        }
    }
    
    @Override
    public void stopAll(){
        for (SSWaitForClientMsgs waitForClientMessages: waitForClientsMessages.values()) {
            try {
                waitForClientMessages.closeComunication();
            } catch (IOException ex) {
                printer.printError("AbsWaitForClientsMessages: " + "Could not close communication to client " + waitForClientMessages.getClient().getId() + ".");
            }
        }
    }
}
