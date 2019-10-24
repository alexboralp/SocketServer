/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip;

import vip.admin.VIPServerAdmin;
import ooserver.OOServerAdminFact;
import vip.interfaces.VIPIPrintable;

/**
 *
 * @author alexander
 */
public class VIPServerAdminFact extends OOServerAdminFact {
    public static VIPServerAdmin createAuctionsServerAdmin (int port, VIPIPrintable printer) {
        return new VIPServerAdmin(port, printer);
    }
}
