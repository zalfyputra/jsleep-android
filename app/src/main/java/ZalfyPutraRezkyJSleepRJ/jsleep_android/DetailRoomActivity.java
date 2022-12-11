package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.util.Calendar;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.*;
/**
 * Show room details and make room bookings
 * @author Zalfy Putra Rezky
 */
public class DetailRoomActivity extends AppCompatActivity {
    TextView name, bed, size, price, city, address, makeBooking;
    CheckBox ac, balcony, bathtub, wifi, refrigerator, restaurant, fitnessCenter, swimmpomgPool;
    private DatePickerDialog datePickerDialog;
    private Button fromDateButton, toDateButton, makeBookingButton, cancelButton;
    public static String fromDate, toDate;
    public static Room selectedRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        // Hide shadow, remove title, and set back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);
        if(getSupportActionBar() != null){
            setTitle("");
        }

        // Room Details
        name = findViewById(R.id.nameBox);
        bed = findViewById(R.id.bedBox);
        size = findViewById(R.id.sizeBox);
        price = findViewById(R.id.priceBox);
        city = findViewById(R.id.cityBox);
        address = findViewById(R.id.addressBox);

        // Facilities Checkboxes
        ac = findViewById(R.id.ac);
        balcony = findViewById(R.id.balcony);
        bathtub = findViewById(R.id.bathtub);
        wifi =  findViewById(R.id.wifi);
        refrigerator = findViewById(R.id.refrigerator);
        restaurant = findViewById(R.id.restaurant);
        fitnessCenter = findViewById(R.id.fitnessCenter);
        swimmpomgPool = findViewById(R.id.swimmingPool);

        // Set Text
        Room selectedRoom = MainActivity.roomList.get(MainActivity.roomSelected);
        name.setText("Name: " + selectedRoom.name);
        bed.setText("Bed Type: " + selectedRoom.bedType.toString());
        size.setText("Size: " + String.valueOf(selectedRoom.size) + "m^2");
        price.setText("Price: Rp. " + Double.toString(selectedRoom.price.price));
        city.setText("City: " + selectedRoom.city.toString());
        address.setText("Address: " + selectedRoom.address);

        // Set Facilities Checkboxes
        for(int i = 0; i < selectedRoom.facility.size(); i++){
            System.out.println(selectedRoom.facility.get(i));
            switch (selectedRoom.facility.get(i)){
                case AC:
                    ac.setChecked(true);
                    break;
                case Balcony:
                    balcony.setChecked(true);
                    break;
                case Bathtub:
                    bathtub.setChecked(true);
                    break;
                case WiFi:
                    wifi.setChecked(true);
                    break;
                case Refrigerator:
                    refrigerator.setChecked(true);
                    break;
                case Restaurant:
                    restaurant.setChecked(true);
                    break;
                case FitnessCenter:
                    fitnessCenter.setChecked(true);
                    break;
                case SwimmingPool:
                    swimmpomgPool.setChecked(true);
                    break;
            }
        }

        // Make Booking
        makeBooking = findViewById(R.id.makeBooking);
        fromDateButton = findViewById(R.id.fromDateButton);
        toDateButton = findViewById(R.id.toDateButton);
        fromDateButton.setText(getTodaysDate());
        toDateButton.setText(getTodaysDate());
        makeBookingButton = findViewById(R.id.makeBookingButton);
        cancelButton = findViewById(R.id.cancelButton);
        initDatePicker(fromDateButton);

        makeBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeBooking.setVisibility(View.INVISIBLE);
                fromDateButton.setVisibility(View.VISIBLE);
                toDateButton.setVisibility(View.VISIBLE);
                makeBookingButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
            }
        });

        makeBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toConfirm = new Intent(DetailRoomActivity.this, ConfirmRoomOrderActivity.class);
                startActivity(toConfirm);
                Toast.makeText(DetailRoomActivity.this, "Booking Successful", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeBooking.setVisibility(View.VISIBLE);
                fromDateButton.setVisibility(View.INVISIBLE);
                toDateButton.setVisibility(View.INVISIBLE);
                makeBookingButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    // Date Picker
    private String getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(Button DateSpinner) {
        int style = AlertDialog.THEME_HOLO_DARK;
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                if(DateSpinner == fromDateButton){
                    fromDate = makeDateString(day, month, year);
                    fromDateButton.setText(fromDate);
                    initDatePicker(toDateButton);
                }
                if(DateSpinner == toDateButton){
                    toDate = makeDateString(day, month, year);
                    toDateButton.setText(toDate);
                }
            }

        };
        getTodaysDate();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year){
        return year + "-" + month + "-" + day;
    }

    // Intent to home for back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}