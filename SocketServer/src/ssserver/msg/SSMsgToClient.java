/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.msg;

import java.io.Serializable;
import ssserver.commoninterfaces.SSIIdable;
import ssserver.commoninterfaces.SSIMessageable;

/**
 *
 * @author alexander
 */
public class SSMsgToClient implements Serializable, SSIIdable, SSIMessageable{
    private String idClient;
    private String message;

    public SSMsgToClient(String idClient, String message) {
        this.idClient = idClient;
        this.message = message;
    }

    @Override
    public String getId() {
        return idClient;
    }

    @Override
    public void setId(String id) {
        this.idClient = id;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(Serializable message) {
        this.message = (String)message;
    }
    
    
}
