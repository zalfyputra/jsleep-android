package ZalfyPutraRezkyJSleepRJ.jsleep_android.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Store renter details
 * @author Zalfy Putra Rezky
 */
public class Renter extends Serializable{
    public String phoneNumber;
    public String address = "";
    public String name ;
    public static final String REGEX_NAME = "^[A-Z][A-Za-z0-9_]{4,20}$";
    public static final String REGEX_PHONE = "^[0-9]{9,12}$";

    public Renter(String username, String phoneNumber, String address){
        this.name = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    public boolean validate(){
        Pattern patternPhone = Pattern.compile(REGEX_PHONE);
        Matcher matcherPhone = patternPhone.matcher(phoneNumber);
        boolean matchPhone = matcherPhone.find();
        Pattern patternName = Pattern.compile(REGEX_NAME);
        Matcher matcherName = patternName.matcher(name);
        boolean matchName = matcherName.find();
        return matchName && matchPhone;
    }
}
