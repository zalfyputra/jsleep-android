package ZalfyPutraRezkyJSleepRJ.jsleep_android.model;

import java.util.Date;
import java.util.ArrayList;
/**
 * Store room details
 * @author Zalfy Putra Rezky
 */
public class Room extends Serializable{
    public int size;
    public int accountId;
    public String name;
    public ArrayList<Facility> facility;
    public Price price;
    public BedType bedType;
    public City city;
    public String address;
    public ArrayList <Date> booked = new ArrayList <Date>();

    public Room(int accountId, String name, int size, Price price, ArrayList<Facility> facility, City city, String address){
        this.accountId = accountId;
        this.name = name;
        this.size = size;
        this.price = price;
        this.facility = facility;
        this.city = city;
        this.address = address;
        this.bedType = BedType.SINGLE;
    }

    public String toString(){
        return "\nName: " + this.name + "\nbedType = " + this.bedType + "\nSize: " + this.size + this.price +
                "\nFacility: " + this.facility + "\nCity: " + this.city + "\nAddress: " + this.address;
    }
}
