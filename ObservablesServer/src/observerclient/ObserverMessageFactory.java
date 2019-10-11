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
    
    public static final int ADD_OBSERVABLE = 100;
    public static final int REMOVE_OBSERVABLE = 101;
    public static final int FOLLOW_OBSERVABLE = 102;
    public static final int UNFOLLOW_OBSERVABLE = 103;
    public static final int REMOVE_ME_FROM_OBSERVERS = 104;
    public static final int MESSAGE_RECEIVED = 105;
    public static final int ADD_OBSERVER = 106;
    public static final int SEND_ALL_OBSERVERS = 107;
    public static final int SEND_ALL_OBSERVABLES = 108;
    public static final int SEND_MY_ID = 109;
}
