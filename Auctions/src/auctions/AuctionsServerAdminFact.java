/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.admin.AuctionsServerAdmin;
import auctions.interfaces.AuctionsIPrintable;
import ooserver.OOServerAdminFact;

/**
 *
 * @author alexander
 */
public class AuctionsServerAdminFact extends OOServerAdminFact {
    public static AuctionsServerAdmin createAuctionsServerAdmin (int port, AuctionsIPrintable printer) {
        return new AuctionsServerAdmin(port, printer);
    }
}
