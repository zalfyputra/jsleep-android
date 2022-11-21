package ZalfyPutraRezkyJSleepRJ.jsleep_android.model;

public class Account extends Serializable{
    public double balance;
    public String email;
    public String name;
    public String password;
    public Renter renter;

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", renter='" + renter + '\'' +
                '}';
    }
}
