/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.socketserver.message;

import java.io.Serializable;
import model.socketserver.commoninterfaces.IIdable;
import model.socketserver.commoninterfaces.IMessageable;
import model.socketserver.commoninterfaces.ISecondaryTypeable;
import model.socketserver.commoninterfaces.ITypeable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface IMessage<T extends Serializable> extends IIdable, ITypeable, ISecondaryTypeable, IMessageable<T>, Serializable {
    
}
