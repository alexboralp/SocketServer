/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.socketserver;

import java.io.Serializable;
import model.socketserver.commoninterfaces.IInputOutputStreamable;
import model.socketserver.commoninterfaces.IOk;
import model.socketserver.commoninterfaces.ISendMessageable;
import model.socketserver.commoninterfaces.ISocketable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface IClient<T extends Serializable> extends ISendMessageable<T>, ISocketable, IInputOutputStreamable, IOk {
    
}
