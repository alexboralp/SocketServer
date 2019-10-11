/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import auctions.MessageClientFactory;
import observerserver.ObserverServerAdministratorFactory;
import observerserver.administrator.ObserverServerAdministrator;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.IObserver;
import vista.ServerGUI;

/**
 *
 * @author alexander
 */
public class ServerController implements IPrintable, IObserver {

    private final ServerGUI serverGUI;
    private final ObserverServerAdministrator serverAdministrator;
            
    public ServerController(ServerGUI serverGUI, int port) {
        this.serverGUI = serverGUI;
        serverGUI.setServerController(this);
        
        print("Starting serverGUI on port " + port + ".");
        
        serverAdministrator = ObserverServerAdministratorFactory.createObserverServerAdministrator(port, this);
        serverAdministrator.addObserver(this);
    }
    
    @Override
    public void print(String message) {
        serverGUI.print(message + "\n");
    }

    @Override
    public void printError(String message) {
        serverGUI.print("ERROR: " + message + "\n");
    }

    @Override
    public void update(Object message) {
        if (message instanceof IMessage) {
            IMessage mes = (IMessage)message;
            switch(mes.getType()) {
                case MessageClientFactory.NEW_OFFER:
                    break;
                case MessageClientFactory.ACCEPT_OFFER:
                    break;
                case MessageClientFactory.ADD_AUCTION:
                    break;
                case MessageClientFactory.FOLLOW_AUCTION:
                    break;
                case MessageClientFactory.UNFOLLOW_AUCTION:
                    break;
                case MessageClientFactory.REMOVE_AUCTION:
                    break;
                case MessageClientFactory.ADD_BIDDER:
                    break;
                case MessageClientFactory.MESSAGE_TO_BIDDER:
                    break;
                case MessageClientFactory.AUCTION_FINISHED:
                    break;
                case MessageClientFactory.SEND_ALL_AUCTIONS:
                    break;
                case MessageClientFactory.SEND_ALL_BIDDERS:
                    break;
                case MessageClientFactory.CLOSE_CONNECTION:
                    break;
                default:
                    break;
            }
        }
    }
    
}
