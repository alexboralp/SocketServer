/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import vista.ClientGUI;

/**
 *
 * @author aborbon
 */
public class AuctionsClient {

    private static ClientGUI clientGUI;
    private static ClientController clientController;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    
        String serverName = "loalhost";
        int port = 4444;
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                clientGUI = new ClientGUI();
                clientGUI.setVisible(true);
                clientController = new ClientController(clientGUI, serverName, port);
            }
        });
    
    }
    
    
    
}
