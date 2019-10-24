/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.actions;

import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vip.admin.VIPClientFamousAdmin;
import vista.ClientGUI;
import vip.interfaces.VIPIPrintable;

/**
 *
 * @author alexander
 */
public class ActionBtnNewMsg implements ActionListener {

    private final VIPClientFamousAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final VIPIPrintable printer;

    public ActionBtnNewMsg(VIPClientFamousAdmin admin, ClientGUI clientGUI, Controller controller, VIPIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = clientGUI.txtMsg.getText();
        if (!"".equals(msg)) {
            admin.addMsg(msg);
        }
    }
    
}
