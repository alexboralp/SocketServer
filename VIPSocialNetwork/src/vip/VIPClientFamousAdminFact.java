/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip;

import vip.msgs.VIPClientMsgHandler;
import ooclient.OOClientAdminFact;
import vip.admin.VIPClientFamousAdmin;
import vip.interfaces.VIPIPrintable;

/**
 *
 * @author alexander
 */
public class VIPClientFamousAdminFact extends OOClientAdminFact {
    public static VIPClientFamousAdmin createAuctionsClientFamousAdmin(String serverName, int port, VIPIPrintable printer) {
        return new VIPClientFamousAdmin(serverName, port, printer);
    }
    
    public static VIPClientFamousAdmin createAuctionsClientFamousAdmin(String serverName, int port, VIPIPrintable printer, VIPClientMsgHandler msgHandler) {
        return new VIPClientFamousAdmin(serverName, port, printer, msgHandler);
    }
}
