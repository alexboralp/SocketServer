/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import admin.Admin;
import auctions.interfaces.AuctionsIPrintable;
import vista.ServerGUI;

/**
 *
 * @author alexander
 */
public class Controller{
    private final ServerGUI serverGUI;
    private final Admin admin;
    private final AuctionsIPrintable printer;

    public Controller(ServerGUI serverGUI, Admin admin, AuctionsIPrintable printer) {
        this.serverGUI = serverGUI;
        this.admin = admin;
        this.printer = printer;
    }
}
