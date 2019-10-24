/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import vip.admin.VIPServerAdmin;
import vista.ServerGUI;
import vip.interfaces.VIPIPrintable;

/**
 *
 * @author alexander
 */
public class Controller{
    private final ServerGUI serverGUI;
    private final VIPServerAdmin admin;
    private final VIPIPrintable printer;

    public Controller(ServerGUI serverGUI, VIPServerAdmin admin, VIPIPrintable printer) {
        this.serverGUI = serverGUI;
        this.admin = admin;
        this.printer = printer;
    }
}
