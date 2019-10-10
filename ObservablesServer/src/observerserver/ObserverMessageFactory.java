/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver;

import socketserver.MessageFactory;

/**
 *
 * @author alexander
 */
public class ObserverMessageFactory extends MessageFactory {
    
    public static final int OBSERVABLES_LIST = 3;
    public static final int OBSERVERS_LIST = 4;
    public static final int TEXT_MESSAGE = 5;
    public static final int SENDING_OBSERVABLE = 6;
    public static final int SENDING_OBSERVER = 7;
    public static final int TEXT_MESSAGE_TO_OBSERVER = 8;
    public static final int DONE = 9;
}
