/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import auctions.interfaces.AuctionsIPrintable;

/**
 *
 * @author alexander
 */
public class Printer implements AuctionsIPrintable {
    
    AuctionsIPrintable clientGUI;

    public Printer(AuctionsIPrintable clientGUI) {
        this.clientGUI = clientGUI;
    }

    @Override
    public void print(String message) {
        clientGUI.print(message);
    }

    @Override
    public void printError(String message) {
        clientGUI.printError(message);
    }
    
}
