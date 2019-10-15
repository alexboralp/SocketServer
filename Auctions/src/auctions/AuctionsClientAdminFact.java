/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.admin.AuctionsClientAdmin;
import ooclient.OOClientAdminFact;
import ooserver.commoninterfaces.OOIPrintable;

/**
 *
 * @author alexander
 */
public class AuctionsClientAdminFact extends OOClientAdminFact {
    public static AuctionsClientAdmin createAuctionsClientAdmin(String serverName, int port, OOIPrintable printer) {
        return new AuctionsClientAdmin(serverName, port, printer);
    }
}
