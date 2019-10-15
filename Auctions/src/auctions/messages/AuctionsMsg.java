/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.messages;

import java.io.Serializable;
import ooserver.msg.OOMsg;
import ssserver.msg.SSIMsg;

/**
 *
 * @author alexander
 */
public class AuctionsMsg extends OOMsg {
    
    public AuctionsMsg(SSIMsg message) {
        super(message);
    }

    public AuctionsMsg() {
    }

    public AuctionsMsg(int type, Serializable mensaje) {
        super(type, mensaje);
    }

    public AuctionsMsg(String id, int type, Serializable mensaje) {
        super(id, type, mensaje);
    }
    
    
    
}
