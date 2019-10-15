/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssclient;

import ssserver.admin.SSClientAdmin;
import ssserver.commoninterfaces.SSIPrintable;

/**
 *
 * @author alexander
 */
public class SSClientAdminFact {
    
    
    public static SSClientAdmin createClientAdministrator(String serverName, int port, SSIPrintable printer) {
        return new SSClientAdmin(serverName, port, printer);
    }
}
