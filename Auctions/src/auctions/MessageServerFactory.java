/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import observerserver.ObserverMessageFactory;

/**
 *
 * @author aborbon
 */
public class MessageServerFactory extends ObserverMessageFactory {
    public static final int NEW_OFFER = 8;
    public static final int MESSAGE_TO_BIDDER = TEXT_MESSAGE_TO_OBSERVER;
}
