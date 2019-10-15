/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.interfaces;

import auctions.messages.AuctionsMsg;

/**
 *
 * @author alexander
 */
public interface AuctionsIMsgHandler {
    public void handleMessage(AuctionsMsg message);
}
