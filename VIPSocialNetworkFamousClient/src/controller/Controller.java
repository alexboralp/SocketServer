/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import vip.msgs.VIPAtributeChanged;
import vip.msgs.VIPAtributeChangedFact;
import controller.actions.ActionBtnNewMsg;
import controller.actions.ActionLstYourMsgs;
import controller.actions.ActionMnuLeftSocialNetwork;
import controller.actions.ActionMnuSalir;
import controller.actions.ActionWindowListener;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import vip.admin.VIPClientFamousAdmin;
import vip.objects.VIPFamousMsg;
import vista.ClientGUI;
import vip.interfaces.VIPIPrintable;
import vip.interfaces.VIPIObserver;

/**
 *
 * @author alexander
 */
public class Controller implements VIPIObserver {
    
    private ClientGUI clientGUI;
    private final VIPClientFamousAdmin admin;
    private final VIPIPrintable printer;
    
    DefaultListModel<String> lstYourMsgsModel;

    public Controller(ClientGUI clientGUI, VIPClientFamousAdmin admin, VIPIPrintable printer) {
        this.clientGUI = clientGUI;
        this.admin = admin;
        this.printer = printer;
        
        _init_();
    }
    
    private void _init_() {
        admin.addObserver(this);
    
        clientGUI.lstYourMsgs.removeAll();
        lstYourMsgsModel = new DefaultListModel<>();
        clientGUI.lstYourMsgs.setModel(lstYourMsgsModel);
        
        clientGUI.btnNewMsg.addActionListener(new ActionBtnNewMsg(admin, clientGUI, this, printer));
        
        ActionLstYourMsgs lstYourMsgsListener = new ActionLstYourMsgs(admin, clientGUI, this, printer);
        clientGUI.lstYourMsgs.addListSelectionListener(lstYourMsgsListener);
        clientGUI.lstYourMsgs.addMouseListener(lstYourMsgsListener);
        
        clientGUI.mnuSalir.addActionListener(new ActionMnuSalir(admin, clientGUI, this, printer));
        clientGUI.mnuLeftSocialNetwork.addActionListener(new ActionMnuLeftSocialNetwork(admin, clientGUI, this, printer));
        clientGUI.addWindowListener(new ActionWindowListener(admin, clientGUI, this, printer));
        
        admin.setClientName(clientGUI.getClientName());
    }

    public ClientGUI getClientGUI() {
        return clientGUI;
    }

    public void setClientGUI(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }
    
    public void updateGUIYourMsgsSelectedInfo (VIPFamousMsg famousMsg) {
        clientGUI.txtMsg.setText(famousMsg.getMessage());
        clientGUI.txtLikes.setText(Integer.toString(famousMsg.getLikes()));
        clientGUI.txtDislikes.setText(Integer.toString(famousMsg.getDislikes()));
    }
    
    public void GUIYourAuctionSelected() {
        String selectedValue = clientGUI.lstYourMsgs.getSelectedValue();
        
        if (selectedValue != null && !"".equals(selectedValue)) {
            if (admin.getFamous().getMsg(selectedValue) != null) {
                VIPFamousMsg famousMsg = admin.getFamous().getMsg(selectedValue);

                updateGUIYourMsgsSelectedInfo(famousMsg);
            }
        }
    }
    
    public void addMsg(VIPFamousMsg msg) {
        lstYourMsgsModel.addElement(msg.getId());
    }
    
    public void setId(String id) {
        admin.setClientId(id);
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(clientGUI, message);
    }
    
    public void msgLike(String msgId) {
        String msgSelected = clientGUI.lstYourMsgs.getSelectedValue();
        if (msgSelected != null && msgSelected.equals(msgId)) {
            clientGUI.txtLikes.setText(Integer.toString(admin.getFamous().getMsg(msgId).getLikes()));
        }
    }
    
    public void msgDislike(String msgId) {
        String msgSelected = clientGUI.lstYourMsgs.getSelectedValue();
        if (msgSelected != null && msgSelected.equals(msgId)) {
            clientGUI.txtDislikes.setText(Integer.toString(admin.getFamous().getMsg(msgId).getDislikes()));
        }
    }

    @Override
    public void update(Object message) {
        if (message instanceof VIPAtributeChanged) {
            VIPAtributeChanged msg = (VIPAtributeChanged) message;
            switch (msg.getType()) {
                case VIPAtributeChangedFact.NEW_MESSAGE:
                    addMsg((VIPFamousMsg)msg.getObject());
                    break;
                case VIPAtributeChangedFact.SHOW_MESSAGE:
                    showMessage((String)msg.getObject());
                    break;
                case VIPAtributeChangedFact.MESSAGE_LIKE:
                    msgLike((String)msg.getObject());
                    break;
                case VIPAtributeChangedFact.MESSAGE_DISLIKE:
                    msgDislike((String)msg.getObject());
                    break;
                default:
                    printer.printError("Comando desconocido.");
                    break;
            }
        }
    }
    
    
    
}
