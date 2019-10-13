/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver;

import ssserver.admin.SSServerAdmin;
import ssserver.commoninterfaces.SSIPrintable;

/**
 *
 * @author alexander
 */
public class SSServerAdminFact {
    
    
    public static SSServerAdmin createServerAdministrator(int port, SSIPrintable printer) {
        return new SSServerAdmin(port, printer);
    }
}
