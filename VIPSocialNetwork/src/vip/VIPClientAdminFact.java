/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip;

import vip.admin.VIPClientAdmin;
import vip.msgs.VIPClientMsgHandler;
import ooclient.OOClientAdminFact;
import vip.interfaces.VIPIPrintable;

/**
 *
 * @author alexander
 */
public class VIPClientAdminFact extends OOClientAdminFact {
    public static VIPClientAdmin createAuctionsClientAdmin(String serverName, int port, VIPIPrintable printer) {
        return new VIPClientAdmin(serverName, port, printer);
    }
    
    public static VIPClientAdmin createAuctionsClientAdmin(String serverName, int port, VIPIPrintable printer, VIPClientMsgHandler msgHandler) {
        return new VIPClientAdmin(serverName, port, printer, msgHandler);
    }
}
