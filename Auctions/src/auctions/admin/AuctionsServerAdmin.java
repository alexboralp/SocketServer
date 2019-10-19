/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.admin;

import auctions.interfaces.AuctionsIMsgHandler;
import auctions.interfaces.AuctionsIPrintable;
import auctions.msgs.AuctionsServerMsgHandler;
import ooserver.admin.OOServerAdmin;

/**
 *
 * @author alexander
 */
public class AuctionsServerAdmin extends OOServerAdmin {

    public AuctionsServerAdmin(int port, AuctionsIPrintable printer) {
        super(port, printer);
        msgHandler = new AuctionsServerMsgHandler(printer, this);
    }

    public AuctionsServerAdmin(int port, AuctionsIPrintable printer, AuctionsIMsgHandler msgHandler) {
        super(port, printer, msgHandler);
    }
    
    
    
}
