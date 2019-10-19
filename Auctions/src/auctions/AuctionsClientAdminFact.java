/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.admin.AuctionsClientAdmin;
import auctions.interfaces.AuctionsIPrintable;
import auctions.msgs.AuctionsClientMsgHandler;
import ooclient.OOClientAdminFact;

/**
 *
 * @author alexander
 */
public class AuctionsClientAdminFact extends OOClientAdminFact {
    public static AuctionsClientAdmin createAuctionsClientAdmin(String serverName, int port, AuctionsIPrintable printer) {
        return new AuctionsClientAdmin(serverName, port, printer);
    }
    
    public static AuctionsClientAdmin createAuctionsClientAdmin(String serverName, int port, AuctionsIPrintable printer, AuctionsClientMsgHandler msgHandler) {
        return new AuctionsClientAdmin(serverName, port, printer, msgHandler);
    }
}
