/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.msgs;

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

    @Override
    public void handleMsg(Object message) {
        this.printer.print("AuctionsClientMsgHandler: " + "New message received.");
        super.handleMsg(message);
        AuctionsIMsg msg;
        if (message instanceof OOIMsg) {
            msg = AuctionsMsgFactForServer.createMsg((OOIMsg)message);
        }
        if (message instanceof AuctionsIMsg) {
            msg = (AuctionsIMsg)message;
            printer.print("ClientController: Nuevo mensaje recibido: " + msg.toString());
            switch(msg.getType()) {
                /*case AuctionsMsgFactForClients.NEW_OFFER:
                    AuctionsMsgNewOffer newOffer = (AuctionsMsgNewOffer)msg.getMessage();
                    printer.print("ClientController: Recibida una nueva oferta para la subasta " + newOffer.getIdAuction());
                    aAdmin.newOffer(newOffer.getIdAuction(), msg.getId(), newOffer.getNewOffer());
                    break;
                case AuctionsMsgFactForClients.ACCEPT_OFFER:
                    AuctionsMsgAcceptOffer acceptOffer = (AuctionsMsgAcceptOffer)msg.getMessage();
                    printer.print("ClientController: Se aceptó tu oferta en la subasta " + acceptOffer.getIdAuction());
                    aAdmin.offerAccepted(acceptOffer.getIdAuction(), acceptOffer.getIdBidder(), acceptOffer.getNewPrice());
                    break;
                case AuctionsMsgFactForClients.AUCTION_FINISHED:
                    AuctionsMsgAuctionFinished auctionFinished = (AuctionsMsgAuctionFinished)msg.getMessage();
                    printer.print("ClientController: Se terminó la subasta" + auctionFinished.getIdAuction() + ", el ganador es " + auctionFinished.getIdWinnerBidder() + ".");
                    aAdmin.auctionFinished(auctionFinished.getIdAuction());
                    break;
                case AuctionsMsgFactForClients.MESSAGE_TO_BIDDER:
                    AuctionsMsgMessageToBidder messageToBidder = (AuctionsMsgMessageToBidder)msg.getMessage();
                    printer.print("ClientController: Llegó un mensaje de la subasta " + messageToBidder.getIdAuction() + ", el mensaje es: " + messageToBidder.getMessage() + ".");
                    break;
                case AuctionsMsgFactForServer.MESSAGE_TO_BIDDER:
                case AuctionsMsgFactForServer.INFO:
                    printer.print("ClientController: Se recibió el mensaje: " + (String)msg.getMessage());
                    break;
                case AuctionsMsgFactForServer.SENDING_ALL_AUCTIONS:
                    printer.print("ClientController: Se van a recibir todas las subastas.");
                    aAdmin.deleteAllAuctions();
                    break;
                case AuctionsMsgFactForServer.SENDING_ALL_BIDDERS:
                    printer.print("ClientController: Se van a recibir todos los postores.");
                    break;*/
                case AuctionsMsgFactForServer.SENDING_AUCTION:
                    printer.print("ClientController: Se recibe una subasta.");
                    aAdmin.addAuction((Auction)msg.getMessage());
                    break;
                /*case AuctionsMsgFactForServer.SENDING_BIDDER:
                    printer.print("ClientController: Se recibe un postor.");
                    break;*/
                case AuctionsMsgFactForServer.DONE:
                    printer.print("ClientController: Todo realizado de parte del servidor.");
                    break;
                case AuctionsMsgFactForServer.SENDING_BIDDER_ID:
                    aAdmin.setClientId((String)msg.getMessage());
                    printer.print("ClientController: El servidor nos envía nuestro id: " + aAdmin.getClient().getId() + ".");
                    break;
                case AuctionsMsgFactForServer.CLOSE_CONNECTION:
                    printer.print("ClientController: El servidor solicita cerrar la conexión.");
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
