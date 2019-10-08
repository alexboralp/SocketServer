/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.client;

import controller.client.ClientController;
import socketclient.MessageFactory;

/**
 *
 * @author aborbon
 */
public class Client extends javax.swing.JFrame {

    ClientController controller;
    
    /**
     * Creates new form Server
     */
    public Client() {
        initComponents();
        
        this.setSize(800, 600);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessages = new javax.swing.JTextArea();
        btnSendMessage = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtMessages.setColumns(20);
        txtMessages.setRows(5);
        jScrollPane1.setViewportView(txtMessages);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        btnSendMessage.setText("Send Message");
        btnSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendMessageActionPerformed(evt);
            }
        });
        getContentPane().add(btnSendMessage, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendMessageActionPerformed
        // TODO add your handling code here:
        
        controller.sendMessageToServer(MessageFactory.createMessage(MessageFactory.INFO, "¡Todo bien!"));
    }//GEN-LAST:event_btnSendMessageActionPerformed

    
    /**
     * Imprime un mensaje en la pantalla
     * @param message Mensaje que se desea imprimir
     */
    public void print(String message) {
        txtMessages.append(message + '\n');
    }

    public ClientController getController() {
        return controller;
    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSendMessage;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtMessages;
    // End of variables declaration//GEN-END:variables
}
