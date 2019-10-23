/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.msgs;

import auctions.AuctionsMsgFactForClients;
import auctions.AuctionsMsgFactForServer;
import auctions.admin.AuctionsClientAdmin;
import auctions.interfaces.AuctionsIMsg;
import auctions.interfaces.AuctionsIMsgHandler;
import auctions.interfaces.AuctionsIPrintable;
import auctions.objects.Auction;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.msg.OOClientMsgHandler;

/**
 *
 * @author alexander
 */
public class AuctionsClientMsgHandler extends OOClientMsgHandler implements AuctionsIMsgHandler {
    
    AuctionsClientAdmin aAdmin;
    
    public AuctionsClientMsgHandler(AuctionsIPrintable printer, AuctionsClientAdmin admin) {
        super(printer, admin);
        aAdmin = admin;
    }
    
    private void newOffer(AuctionsMsgNewOffer newOffer) {
        printer.print("AuctionsClientMsgHandler: Recibida una nueva oferta para la subasta " + newOffer.getIdAuction());
        Auction auction = aAdmin.getAuctions().get(newOffer.getIdAuction());
        auction.newOffer(newOffer.getIdBidder(), newOffer.getNewOffer());
        aAdmin.GUINewMessage("Recibida una nueva oferta para la subasta " + newOffer.getIdAuction() + ".");
    }
    
    private void acceptOffer(AuctionsMsgAcceptOffer acceptOffer) {
        printer.print("AuctionsClientMsgHandler: Se aceptó tu oferta en la subasta " + acceptOffer.getIdAuction());
        Auction auction = aAdmin.getAuctions().get(acceptOffer.getIdAuction());
        //auction.acceptNewOffer();
        auction.setActualPrice(auction.getNextPrice());
        auction.setNextPrice(acceptOffer.getNewPrice());
        auction.setBidderId(acceptOffer.getIdBidder());
        auction.setNewBidderId("");
        aAdmin.GUINewMessage("Se aceptó la oferta para la subasta " + acceptOffer.getIdAuction() + ".");
    }
    
    private void auctionFinished(AuctionsMsgAuctionFinished auctionFinished) {
        printer.print("AuctionsClientMsgHandler: Se terminó la subasta" + auctionFinished.getIdAuction() + ", el ganador es " + auctionFinished.getIdWinnerBidder() + ".");
        Auction auction = aAdmin.getAuction(auctionFinished.getIdAuction());
        auction.setState(Auction.STATE.FINISHED);
        aAdmin.GUINewMessage("Se terminó la subasta" + auctionFinished.getIdAuction() + ", el ganador es " + auctionFinished.getIdWinnerBidder() + ".");
    }

    @Override
    public void handleMsg(Object message) {
        this.printer.print("AuctionsClientMsgHandler: " + "New message received.");
        super.handleMsg(message);
        Object msgObj;
        if (message instanceof OOIMsg) {
            msgObj = AuctionsMsgFactForServer.createMsg((OOIMsg)message);
        } else {
            msgObj = message;
        }
        if (msgObj instanceof AuctionsIMsg) {
            AuctionsIMsg msg = (AuctionsIMsg)msgObj;
            printer.print("AuctionsClientMsgHandler: Nuevo mensaje recibido: " + msg.toString());
            switch(msg.getType()) {
                case AuctionsMsgFactForClients.NEW_OFFER:
                    newOffer((AuctionsMsgNewOffer)msg.getMessage());
                    break;
                case AuctionsMsgFactForClients.ACCEPT_OFFER:
                    acceptOffer((AuctionsMsgAcceptOffer)msg.getMessage());
                    break;
                case AuctionsMsgFactForClients.AUCTION_FINISHED:
                    auctionFinished((AuctionsMsgAuctionFinished)msg.getMessage());
                    break;
                case AuctionsMsgFactForClients.MESSAGE_TO_BIDDER:
                    AuctionsMsgMessageToBidder messageToBidder = (AuctionsMsgMessageToBidder)msg.getMessage();
                    printer.print("AuctionsClientMsgHandler: Llegó un mensaje de la subasta " + messageToBidder.getIdAuction() + ", el mensaje es: " + messageToBidder.getMessage() + ".");
                    aAdmin.GUINewMessage("Llegó un mensaje de la subasta " + messageToBidder.getIdAuction() + ": " + messageToBidder.getMessage() + ".");
                    break;
                case AuctionsMsgFactForClients.FOLLOW_AUCTION:
                    aAdmin.GUIFollowAuction((String)msg.getMessage());
                    break;
                case AuctionsMsgFactForClients.UNFOLLOW_AUCTION:
                    aAdmin.GUIUnfollowAuction((String)msg.getMessage());
                    break;
                case AuctionsMsgFactForServer.INFO:
                    printer.print("AuctionsClientMsgHandler: Se recibió el mensaje: " + (String)msg.getMessage());
                    break;
                case AuctionsMsgFactForServer.AUCTION_ID_REPEATED:
                    printer.print("AuctionsClientMsgHandler: El id de la subasta ya fue utilizado.");
                    aAdmin.GUINewMessage("El id de la subasta ya fue utilizado.");
                    break;
                case AuctionsMsgFactForServer.SENDING_ALL_AUCTIONS:
                    printer.print("AuctionsClientMsgHandler: Se van a recibir todas las subastas.");
                    aAdmin.GUIDeleteAllAuctions();
                    break;
                case AuctionsMsgFactForServer.SENDING_ALL_BIDDERS:
                    printer.print("AuctionsClientMsgHandler: Se van a recibir todos los postores.");
                    break;
                case AuctionsMsgFactForServer.SENDING_AUCTION:
                    printer.print("AuctionsClientMsgHandler: Se recibe una subasta.");
                    aAdmin.GUIAddAuction((Auction)msg.getMessage());
                    break;
                /*case AuctionsMsgFactForServer.SENDING_BIDDER:
                    printer.print("ClientController: Se recibe un postor.");
                    break;*/
                case AuctionsMsgFactForServer.NEW_BID_ALLREADY_MADE:
                    aAdmin.GUINewBidAllreadyMade((String)msg.getMessage());
                    break;
                case AuctionsMsgFactForServer.TEXT_MESSAGE:
                    aAdmin.GUINewBidAllreadyMade((String)msg.getMessage());
                    break;
                case AuctionsMsgFactForServer.TEXT_MESSAGE_TO_OBSERVER:
                    aAdmin.GUINewBidAllreadyMade((String)msg.getMessage());
                    break;
                case AuctionsMsgFactForServer.DONE:
                    printer.print("AuctionsClientMsgHandler: Todo realizado de parte del servidor.");
                    break;
                case AuctionsMsgFactForServer.SENDING_BIDDER_ID:
                    aAdmin.setClientId((String)msg.getMessage());
                    printer.print("AuctionsClientMsgHandler: El servidor nos envía nuestro id: " + aAdmin.getClient().getId() + ".");
                    break;
                case AuctionsMsgFactForServer.CLOSE_CONNECTION:
                    printer.print("AuctionsClientMsgHandler: El servidor solicita cerrar la conexión.");
                    break;
                case AuctionsMsgFactForServer.CHECKING_CONNECTION:
                    printer.print("El servidor solicitó checkar la conexión, se mandó un mensaje de confirmación.");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void update(Object message) {
            this.printer.print("AuctionsClientMsgHandler: " + "Sending message to message handler.");
            this.handleMsg(message);
    }
    
}
