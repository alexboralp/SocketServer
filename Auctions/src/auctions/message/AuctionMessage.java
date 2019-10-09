/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.message;

import socketserver.message.AbsMessage;

/**
 *
 * @author aborbon
 */
public class AuctionMessage extends AbsMessage {
    
    public enum Mensaje {
        OFERTAR, ACEPTAR_OFERTA, NUEVA_SUBASTA, BORRADO
    }
    
    
}
