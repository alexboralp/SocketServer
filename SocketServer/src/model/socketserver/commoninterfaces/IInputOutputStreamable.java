/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.socketserver.commoninterfaces;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author alexander
 */
public interface IInputOutputStreamable {
    public ObjectInputStream getIn();
    public ObjectOutputStream getOut();
}
