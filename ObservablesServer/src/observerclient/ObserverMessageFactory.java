/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerclient;

import socketclient.MessageFactory;

/**
 *
 * @author alexander
 */
public class ObserverMessageFactory extends MessageFactory {
    
    public static final int ADD_OBSERVABLE = 2;
    public static final int REMOVE_OBSERVABLE = 3;
    public static final int FOLLOW_OBSERVABLE = 4;
    public static final int UNFOLLOW_OBSERVABLE = 5;
    public static final int REMOVE_ME_FROM_OBSERVERS = 6;
    public static final int MESSAGE_RECEIVED = 7;
    public static final int ADD_OBSERVER = 8;
}
