package ZalfyPutraRezkyJSleepRJ.jsleep_android.model;

import java.util.Date;
import java.util.ArrayList;

public class Room extends Serializable{
    public int accountId;
    public String name;
    public ArrayList<Date> booked;
    public String address;
    public Price price;
    public City city;
    public int size;
    public BedType bedType;
    public Facility facility;
}
