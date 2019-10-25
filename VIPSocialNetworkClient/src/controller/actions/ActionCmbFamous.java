/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.actions;

import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vip.admin.VIPClientAdmin;
import vip.interfaces.VIPIPrintable;
import vista.ClientGUI;

/**
 *
 * @author aborbon
 */
public class ActionCmbFamous implements ActionListener {

    VIPClientAdmin admin;
    ClientGUI clientGUI;
    Controller controller;
    VIPIPrintable printer;

    public ActionCmbFamous(VIPClientAdmin admin, ClientGUI clientGUI, Controller controller, VIPIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        controller.GUIUpdateMsgsList();
    }
    
}
