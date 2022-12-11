package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.util.*;
import retrofit2.*;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.*;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.BaseApiService;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.UtilsApi;
/**
 * Activity to request and create room order
 * @author Zalfy Putra Rezky
 */
public class CreateRoomActivity extends AppCompatActivity implements View.OnClickListener{
    // Declare variable
    public Room room;
    private BedType bedType;
    private City city;
    BaseApiService mApiService;
    Context mContext;

    EditText nameBox, priceBox, addressBox, sizeBox;
    CheckBox ac, bathtub, balcony, wifi, refrigerator, restaurant, fitnessCenter, swimmingPool;
    Spinner citySpinner, bedSpinner;
    Button createButton, cancelButton;
    ArrayList<Facility> facility = new ArrayList<Facility>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        // Add a back button to the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            setTitle("");
        }
        mApiService = UtilsApi.getApiService();
        mContext = this;

        // Data Inputs
        nameBox = findViewById(R.id.nameBox);
        priceBox = findViewById(R.id.priceBox);
        addressBox = findViewById(R.id.addressBox);
        sizeBox = findViewById(R.id.sizeBox);

        // Facilities
        ac = findViewById(R.id.ac);
        ac.setOnClickListener(this::onClick);
        bathtub = findViewById(R.id.bathtub);
        bathtub.setOnClickListener(this::onClick);
        balcony = findViewById(R.id.balcony);
        balcony.setOnClickListener(this::onClick);
        wifi = findViewById(R.id.wifi);
        wifi.setOnClickListener(this::onClick);
        refrigerator = findViewById(R.id.refrigerator);
        refrigerator.setOnClickListener(this::onClick);
        restaurant = findViewById(R.id.restaurant);
        restaurant.setOnClickListener(this::onClick);
        fitnessCenter = findViewById(R.id.fitnessCenter);
        fitnessCenter.setOnClickListener(this::onClick);
        swimmingPool = findViewById(R.id.swimmingPool);
        swimmingPool.setOnClickListener(this::onClick);

        // Spinners
        citySpinner = findViewById(R.id.citySpinner);
        bedSpinner = findViewById(R.id.bedSpinner);

        // Buttons
        createButton = findViewById(R.id.createButton);
        cancelButton = findViewById(R.id.cancelButton);

        // City Spinner
        ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(getApplicationContext(), android.R.layout.simple_spinner_item, City.values());
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCity = adapterView.getItemAtPosition(i).toString();
                city = City.valueOf(selectedCity);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Bed Spinner
        ArrayAdapter<BedType> bedAdapter = new ArrayAdapter<BedType>(getApplicationContext(), android.R.layout.simple_spinner_item, BedType.values());
        bedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bedSpinner.setAdapter(bedAdapter);
        bedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedBed = adapterView.getItemAtPosition(i).toString();
                bedType = BedType.valueOf(selectedBed);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Create Button
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                List<Room> room = requestCreateRoom();
            }
        });

        // Cancel Button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(move);
            }
        });
    }

    // Check Facilities Checkbox
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ac:
                if(ac.isChecked())
                    facility.add(Facility.AC);
                break;
            case R.id.bathtub:
                if(bathtub.isChecked())
                    facility.add(Facility.Bathtub);
                break;
            case R.id.balcony:
                if(balcony.isChecked())
                    facility.add(Facility.Balcony);
                break;
            case R.id.wifi:
                if(wifi.isChecked())
                    facility.add(Facility.WiFi);
                break;
            case R.id.refrigerator:
                if(refrigerator.isChecked())
                    facility.add(Facility.Refrigerator);
                break;
            case R.id.restaurant:
                if(restaurant.isChecked())
                    facility.add(Facility.Restaurant);
                break;
            case R.id.fitnessCenter:
                if(fitnessCenter.isChecked())
                    facility.add(Facility.FitnessCenter);
                break;
            case R.id.swimmingPool:
                if(swimmingPool.isChecked())
                    facility.add(Facility.SwimmingPool);
                break;
        }
    }

    protected List<Room> requestCreateRoom(){
        mApiService.createRoom(MainActivity.idLog, nameBox.getText().toString(), Integer.parseInt(sizeBox.getText().toString()),
                Integer.parseInt(priceBox.getText().toString()), facility, city, addressBox.getText().toString(), bedType).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    List<Room> room;
                    room = response.body();
                    Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Room is already exist!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    // Add a back button to the action bar
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