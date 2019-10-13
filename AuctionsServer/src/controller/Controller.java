/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import admin.Admin;
import ooserver.commoninterfaces.OOIPrintable;
import vista.ServerGUI;

/**
 *
 * @author alexander
 */
public class Controller{
    private final ServerGUI serverGUI;
    Admin admin;
    OOIPrintable printer;

    public Controller(ServerGUI serverGUI, Admin admin, OOIPrintable printer) {
        this.serverGUI = serverGUI;
        this.admin = admin;
        this.printer = printer;
    }
}
