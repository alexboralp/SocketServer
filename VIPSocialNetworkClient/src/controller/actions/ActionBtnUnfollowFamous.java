/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.actions;

import vip.admin.VIPClientAdmin;
import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import vista.ClientGUI;
import vip.interfaces.VIPIPrintable;

/**
 *
 * @author alexander
 */
public class ActionBtnUnfollowFamous implements ActionListener {

    private final VIPClientAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final VIPIPrintable printer;

    public ActionBtnUnfollowFamous(VIPClientAdmin admin, ClientGUI clientGUI, Controller controller, VIPIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pos = clientGUI.lstAvailableFamous.getSelectedIndex();
        if (pos != -1) {
            String famousId = clientGUI.lstAvailableFamous.getSelectedValue();

            printer.print("Solicitando seguir al famoso " + famousId + ".");
            admin.unfollowFamous(famousId);
        } else {
            controller.GUIShowMessage("Se debe escoger un famoso.");
        }
    }
    
}
