package ZalfyPutraRezkyJSleepRJ.jsleep_android.model;

import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.*;
/**
 * Class for storing invoice details
 * @author Zalfy Putra Rezky
 */
public class Invoice extends Serializable
{
    public int buyerId;
    public int renterId;
    public PaymentStatus status;
    public RoomRating rating;

    public enum RoomRating{
        NONE, BAD, NEUTRAL, GOOD
    }
    public enum PaymentStatus{
        FAILED, WAITING, SUCCESS
    }
    protected Invoice(int buyerId, int renterId){
        this.buyerId = buyerId;
        this.renterId = renterId;
    }
    public Invoice(Account buyer, Renter renter){
        this.buyerId = buyer.id;
        this.renterId = renter.id;
    }

    public String print(){
        return "\nID: " + this.id + "\nBuyer: " + this.buyerId + "\nRenter: " + this.renterId;
    }
}
