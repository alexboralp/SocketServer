/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.actions;

import vip.admin.VIPClientAdmin;
import vip.objects.VIPFamous;
import controller.Controller;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import vista.ClientGUI;
import vip.interfaces.VIPIPrintable;

/**
 *
 * @author alexander
 */
public class ActionLstAvailableFamous implements ListSelectionListener, MouseListener {

    private final VIPClientAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final VIPIPrintable printer;

    public ActionLstAvailableFamous(VIPClientAdmin admin, ClientGUI clientGUI, Controller controller, VIPIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }
    
    private void process() {
        if (clientGUI.lstAvailableFamous.getSelectedIndex() != -1) {
            clientGUI.btnFollowFamous.setEnabled(true);
        } else {
            clientGUI.btnFollowFamous.setEnabled(false);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        process();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        process();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
