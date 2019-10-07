/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.socketserver;

import java.util.LinkedList;
import model.socketserver.commoninterfaces.IList;

/**
 *
 * @author alexander
 */
public class Clients implements IList<IClient>{
    private LinkedList<IClient> clients;

    @Override
    public void add(IClient element) {
        clients.add(element);
    }

    @Override
    public void remove(IClient element) {
        clients.remove(element);
    }
    
}
