/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import vip.admin.VIPClientAdmin;
import vip.objects.VIPFamous;
import vip.msgs.VIPAtributeChanged;
import vip.msgs.VIPAtributeChangedFact;
import controller.actions.ActionBtnLike;
import controller.actions.ActionBtnDislike;
import controller.actions.ActionBtnFollowFamous;
import controller.actions.ActionBtnUnfollowFamous;
import controller.actions.ActionCmbFamous;
import controller.actions.ActionLstAvailableFamous;
import controller.actions.ActionLstFollowedFamous;
import controller.actions.ActionLstMsgs;
import controller.actions.ActionMnuSalir;
import controller.actions.ActionWindowListener;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import vista.ClientGUI;
import vip.interfaces.VIPIPrintable;
import vip.interfaces.VIPIObserver;
import vip.msgs.VIPMsgLike;
import vip.objects.VIPFamousMsg;

/**
 *
 * @author alexander
 */
public class Controller implements VIPIObserver {
    
    private ClientGUI clientGUI;
    private final VIPClientAdmin admin;
    private final VIPIPrintable printer;
    
    DefaultListModel<String> lstMsgsModel;
    DefaultListModel<String> lstFollowedFamousModel;
    DefaultListModel<String> lstAvailableFamousModel;

    public Controller(ClientGUI clientGUI, VIPClientAdmin admin, VIPIPrintable printer) {
        this.clientGUI = clientGUI;
        this.admin = admin;
        this.printer = printer;
        
        _init_();
    }
    
    private void _init_() {
        admin.addObserver(this);
    
        clientGUI.lstMsgs.removeAll();
        lstMsgsModel = new DefaultListModel<>();
        clientGUI.lstMsgs.setModel(lstMsgsModel);
        
        clientGUI.lstFollowedFamous.removeAll();
        lstFollowedFamousModel = new DefaultListModel<>();
        clientGUI.lstFollowedFamous.setModel(lstFollowedFamousModel);
        
        clientGUI.lstAvailableFamous.removeAll();
        lstAvailableFamousModel = new DefaultListModel<>();
        clientGUI.lstAvailableFamous.setModel(lstAvailableFamousModel);
        
        clientGUI.btnLike.addActionListener(new ActionBtnLike(admin, clientGUI, this, printer));
        clientGUI.btnDislike.addActionListener(new ActionBtnDislike(admin, clientGUI, this, printer));
        clientGUI.btnFollowFamous.addActionListener(new ActionBtnFollowFamous(admin, clientGUI, this, printer));
        clientGUI.btnUnfollowFamous.addActionListener(new ActionBtnUnfollowFamous(admin, clientGUI, this, printer));
        
        ActionLstAvailableFamous lstAvailableFamousListener = new ActionLstAvailableFamous(admin, clientGUI, this, printer);
        clientGUI.lstAvailableFamous.addListSelectionListener(lstAvailableFamousListener);
        clientGUI.lstAvailableFamous.addMouseListener(lstAvailableFamousListener);
        
        ActionLstFollowedFamous lstFollowedFamousListener = new ActionLstFollowedFamous(admin, clientGUI, this, printer);
        clientGUI.lstFollowedFamous.addListSelectionListener(lstFollowedFamousListener);
        clientGUI.lstFollowedFamous.addMouseListener(lstFollowedFamousListener);
        
        ActionLstMsgs lstMsgsListener = new ActionLstMsgs(admin, clientGUI, this, printer);
        clientGUI.lstMsgs.addListSelectionListener(lstMsgsListener);
        clientGUI.lstMsgs.addMouseListener(lstMsgsListener);
        
        clientGUI.cmbFamous.addActionListener(new ActionCmbFamous(admin, clientGUI, this, printer));
        
        clientGUI.mnuSalir.addActionListener(new ActionMnuSalir(admin, clientGUI, this, printer));
        clientGUI.addWindowListener(new ActionWindowListener(admin, clientGUI, this, printer));
        
        admin.setClientName(clientGUI.getClientName());
    }

    public ClientGUI getClientGUI() {
        return clientGUI;
    }

    public void setClientGUI(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }
    
    public void GUIUpdateAvailableFamousList() {
        lstAvailableFamousModel.clear();
        for (VIPFamous famous : admin.getFamous().getFamous().values()) {
            lstAvailableFamousModel.addElement(famous.getId());
        }
    }
    
    public void GUIUpdateFollowedAuctionsList() {
        lstFollowedFamousModel.clear();
        clientGUI.cmbFamous.removeAllItems();
        for (VIPFamous famous : admin.getFollowedFamous().getFamous().values()) {
            lstFollowedFamousModel.addElement(famous.getId());
            clientGUI.cmbFamous.addItem(famous.getId());
        }
    }
    
    public void GUIUpdateMsgsList() {
        lstMsgsModel.clear();
        VIPFamous famous = admin.getFamous(clientGUI.cmbFamous.getSelectedItem().toString());
        for (VIPFamousMsg msg : famous.getMsgs()) {
            lstMsgsModel.addElement(msg.getId());
        }
    }
    
    public void GUIUpdateMsgInfo (VIPFamousMsg msg) {
        clientGUI.txtMsg.setText(msg.getMessage());
        clientGUI.txtLikes.setText(Integer.toString(msg.getLikes()));
        clientGUI.txtDislikes.setText(Integer.toString(msg.getDislikes()));
    }
    
    public void GUIMsgSelected() {
        VIPFamous famous = admin.getFamous(clientGUI.cmbFamous.getSelectedItem().toString());
        VIPFamousMsg message = famous.getMsg(clientGUI.lstMsgs.getSelectedValue());
        GUIUpdateMsgInfo(message);
    }
    
    public void GUIDeleteAllFamous() {
        lstAvailableFamousModel.clear();
        lstMsgsModel.clear();
    }
    
    public void GUIDeleteFamous(String famousId) {
        if (lstFollowedFamousModel.contains(famousId)) {
            lstFollowedFamousModel.removeElement(famousId);
        }
        if (lstAvailableFamousModel.contains(famousId)) {
            lstAvailableFamousModel.removeElement(famousId);
        }
    }
    
    public void GUIAddFamous(VIPFamous famous) {
        if (!lstAvailableFamousModel.contains(famous.getId())) {
            lstAvailableFamousModel.addElement(famous.getId());
        }
    }
    
    public void GUIAddMsg(VIPFamousMsg msg) {
        if (clientGUI.cmbFamous.getSelectedItem() != null && clientGUI.cmbFamous.getSelectedItem().toString().equals(msg.getOwnerId())) {
            lstMsgsModel.addElement(msg.getId());
        }
    }
    
    public void GUILikeMsg(VIPMsgLike msgLike) {
        if (clientGUI.cmbFamous.getSelectedItem().toString().equals(msgLike.getFamousId())) {
            if (clientGUI.lstMsgs.getSelectedValue().equals(msgLike.getMsgId())) {
                VIPFamous famous = admin.getFamous(msgLike.getFamousId());
                VIPFamousMsg msg = famous.getMsg(msgLike.getMsgId());
                clientGUI.txtLikes.setText(Integer.toString(msg.getLikes()));
            }
        }
    }
    
    public void GUIDislikeMsg(VIPMsgLike msgLike) {
        if (clientGUI.cmbFamous.getSelectedItem().toString().equals(msgLike.getFamousId())) {
            if (clientGUI.lstMsgs.getSelectedValue().equals(msgLike.getMsgId())) {
                VIPFamous famous = admin.getFamous(msgLike.getFamousId());
                VIPFamousMsg msg = famous.getMsg(msgLike.getMsgId());
                clientGUI.txtDislikes.setText(Integer.toString(msg.getDislikes()));
            }
        }
    }
    
    public void GUIFollowFamous(String famousId) {
        if (lstAvailableFamousModel.contains(famousId) && !lstFollowedFamousModel.contains(famousId)) {
            lstFollowedFamousModel.addElement(famousId);
            clientGUI.cmbFamous.addItem(famousId);
        }
    }
    
    public void GUIUnfollowFamous(String famousId) {
        if (lstFollowedFamousModel.contains(famousId)) {
            lstFollowedFamousModel.removeElement(famousId);
            clientGUI.cmbFamous.removeItem(famousId);
        }
    }
    
    public void GUIShowMessage(String message) {
        JOptionPane.showMessageDialog(clientGUI, message);
    }

    public void setId(String id) {
        admin.setClientId(id);
    }

    @Override
    public void update(Object message) {
        if (message instanceof VIPAtributeChanged) {
            VIPAtributeChanged msg = (VIPAtributeChanged) message;
            switch (msg.getType()) {
                case VIPAtributeChangedFact.DELETE_ALL_FAMOUS:
                    GUIDeleteAllFamous();
                    break;
                case VIPAtributeChangedFact.DELETE_FAMOUS:
                    GUIDeleteFamous((String)msg.getObject());
                    break;
                case VIPAtributeChangedFact.NEW_FAMOUS:
                    GUIAddFamous((VIPFamous)msg.getObject());
                    break;
                case VIPAtributeChangedFact.NEW_MESSAGE:
                    GUIAddMsg((VIPFamousMsg)msg.getObject());
                    break;
                case VIPAtributeChangedFact.FOLLOW_FAMOUS:
                    GUIFollowFamous((String)msg.getObject());
                    break;
                case VIPAtributeChangedFact.UNFOLLOW_FAMOUS:
                    GUIUnfollowFamous((String)msg.getObject());
                    break;
                case VIPAtributeChangedFact.MESSAGE_LIKE:
                    GUILikeMsg((VIPMsgLike)msg.getObject());
                    break;
                case VIPAtributeChangedFact.MESSAGE_DISLIKE:
                    GUIDislikeMsg((VIPMsgLike)msg.getObject());
                    break;
                case VIPAtributeChangedFact.SHOW_MESSAGE:
                    GUIShowMessage((String)msg.getObject());
                    break;
                default:
                    printer.printError("Comando desconocido.");
                    break;
            }
        }
    }
    
    
    
}
