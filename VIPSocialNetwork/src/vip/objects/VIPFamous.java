/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.objects;

import java.util.LinkedList;
import ooserver.observermsg.OOAbsSendableObj;
import vip.interfaces.VIPILevelable;

/**
 *
 * @author alexander
 */
public class VIPFamous extends OOAbsSendableObj implements VIPILevelable {
    
    private String name;
    private int level;
    private final LinkedList<VIPFamousMsg> msgs;
    
    public VIPFamous() {
        super("");
        msgs = new LinkedList();
    }

    public VIPFamous(String id, String name) {
        super(id);
        this.name = name;
        this.level = 0;
        msgs = new LinkedList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getMsgsNumber() {
        return msgs.size();
    }

    @Override
    public void addLevel() {
        level++;
    }

    @Override
    public void substractLevel() {
        level--;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }
    
    public void addMsg(VIPFamousMsg msg) {
        msg.setOwnerId(id);
        msgs.add(msg);
    }
    
    public VIPFamousMsg getMsg(String msgId) {
        for (VIPFamousMsg msg : msgs) {
            if (msgId.equals(msg.getId())) {
                return msg;
            }
        }
        return null;
    }
    
    public LinkedList<VIPFamousMsg> getMsgs() {
        return msgs;
    }

    @Override
    public String toString() {
        return "VIPFamous{" + "name=" + name + ", level=" + level + ", msgs=" + msgs + '}';
    }
    
    
}
