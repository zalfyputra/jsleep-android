package ZalfyPutraRezkyJSleepRJ.jsleep_android.model;
/**
 * Store price details
 * @author Zalfy Putra Rezky
 */
public class Price {
    public double discount;
    public double price;

    public Price(double price){
        this.price = price;
        this.discount = 0;
    }

    public Price(double price, double discount){
        this.price = price;
        this.discount = discount;
    }

    public String toString(){
        return "\nPrice: " + this.price + "\nDiscount: " + this.discount;
    }
}
