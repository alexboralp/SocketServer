/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.objects;

import ooserver.commoninterfaces.OOIMessageable;
import ooserver.commoninterfaces.OOIOwnerable;
import ooserver.commoninterfaces.OOISerializableIdable;

/**
 *
 * @author alexander
 */
public class VIPFamousMsg implements OOISerializableIdable, OOIMessageable<String>, OOIOwnerable {

    private String id;
    private String message;
    private String ownerId;
    private int likes;
    private int dislikes;

    public VIPFamousMsg(String id, String message, String ownerId) {
        this.id = id;
        this.message = message;
        this.ownerId = ownerId;
        this.likes = 0;
        this.dislikes = 0;
    }
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    @Override
    public String toString() {
        return "VIPFamousMsg{" + "id=" + id + ", message=" + message + ", ownerId=" + ownerId + ", likes=" + likes + ", dislikes=" + dislikes + '}';
    }
    
}
