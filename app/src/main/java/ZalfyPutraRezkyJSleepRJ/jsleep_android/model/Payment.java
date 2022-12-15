package ZalfyPutraRezkyJSleepRJ.jsleep_android.model;

import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Class for storing payments done by the account
 * @author Zalfy Putra Rezky
 */
public class Payment extends Invoice {

    public Date to;
    public Date from;
    private int roomId;
    SimpleDateFormat SDFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Payment(int buyerId, int renterId, int roomId, Date from, Date to) {
        super(buyerId, renterId);
        this.buyerId = buyerId;
        this.renterId = renterId;
        this.roomId = roomId;
        this.from = from;
        this.to = to;
    }

    public Payment(Account buyer, Renter renter, int roomId, Date from, Date to) {
        super(buyer.id, renter.id);
        this.roomId = roomId;
        this.from = from;
        this.to = to;
    }

    public String print() {
        return "\nId: " + this.id + "\nBuyer: " + this.buyerId + "\nRenter: " + this.renterId +
                "\nRoom ID: " + this.roomId + "\nFrom: " + this.from + "\nTo: " + to;
    }

    public int getRoomId() {
        return roomId;
    }

    public static boolean makeBooking(Date from, Date to, Room room) {
        if (availability(from, to, room)) {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            return true;
        }
        return false;
    }

    public static boolean availability(Date from, Date to, Room room) {
        Calendar start = Calendar.getInstance();
        start.setTime(from);
        Calendar end = Calendar.getInstance();
        end.setTime(to);
        if (start.after(end) || start.equals(end)) {
            return false;
        }
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            if (room.booked.contains(date)) {
                return false;
            }
        }
        return true;
    }
}
