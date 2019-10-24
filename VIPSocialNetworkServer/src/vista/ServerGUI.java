/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import vip.interfaces.VIPIPrintable;

/**
 *
 * @author aborbon
 */
public class ServerGUI extends javax.swing.JFrame implements VIPIPrintable {
    
    /**
     * Creates new form Server
     */
    public ServerGUI() {
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtMessages.setColumns(20);
        txtMessages.setRows(5);
        jScrollPane1.setViewportView(txtMessages);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea txtMessages;
    // End of variables declaration//GEN-END:variables

    
    
    @Override
    public void print(String message) {
        txtMessages.append(message + "\n");
    }

    @Override
    public void printError(String message) {
        txtMessages.append("ERROR: " + message + "\n");
    }

}
