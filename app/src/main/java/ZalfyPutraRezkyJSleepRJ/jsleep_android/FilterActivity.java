package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import static ZalfyPutraRezkyJSleepRJ.jsleep_android.MainActivity.nameList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.BedType;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.City;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Facility;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Room;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.BaseApiService;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Filter search results
 * @author Zalfy Putra Rezky
 */
public class FilterActivity extends AppCompatActivity implements View.OnClickListener{
    Spinner filter;
    TextView firstFilter, secondFilter;
    EditText minSize, maxSize, minPrice, maxPrice;
    Spinner citySpinner, bedSpinner;
    Button applyFilter;
    CheckBox ac, bathtub, balcony, wifi, refrigerator, restaurant, fitnessCenter, swimmingPool;
    City cityFilter;
    BedType bedFilter;
    private static ArrayList<Facility> facility = new ArrayList<>();
    private static ArrayList<Room> filteredRoom = new ArrayList<>();
    BaseApiService mApiService;
    Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mApiService = UtilsApi.getApiService();
        mcontext = this;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            setTitle("");
        }

        applyFilter = findViewById(R.id.applyFilter);
        firstFilter = findViewById(R.id.firstFilter);
        secondFilter = findViewById(R.id.secondFilter);
        filter = findViewById(R.id.spinnerFilter);

        citySpinner = findViewById(R.id.citySpinner);
        bedSpinner = findViewById(R.id.bedSpinner);

        minSize = findViewById(R.id.minSize);
        minSize.setText("0");
        maxSize = findViewById(R.id.maxSize);
        maxSize.setText("0");
        minPrice = findViewById(R.id.minPrice);
        minPrice.setText("0");
        maxPrice = findViewById(R.id.maxPrice);
        maxPrice.setText("0");

        // Facility
        ac = findViewById(R.id.ac);
        bathtub = findViewById(R.id.bathtub);
        balcony = findViewById(R.id.balcony);
        wifi = findViewById(R.id.wifi);
        refrigerator = findViewById(R.id.refrigerator);
        restaurant = findViewById(R.id.restaurant);
        fitnessCenter = findViewById(R.id.fitnessCenter);
        swimmingPool = findViewById(R.id.swimmingPool);
        if(facility != null){
            facility.clear();
        }

        ArrayAdapter<Filter> adapterFilter = new ArrayAdapter<Filter>(getApplicationContext(), android.R.layout.simple_spinner_item, Filter.values());
        filter.setAdapter(adapterFilter);
        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i) == (Filter.BEDTYPE)){
                    invisEverything();
                    firstFilter.setText("Bed Type: ");
                    firstFilter.setVisibility(View.VISIBLE);
                    bedSpinner.setAdapter(new ArrayAdapter<BedType>(getApplicationContext(), android.R.layout.simple_spinner_item, BedType.values()));
                    bedSpinner.setVisibility(View.VISIBLE);
                    bedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterBed, View view, int pos, long l) {
                            bedFilter = BedType.valueOf(adapterBed.getItemAtPosition(pos).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterBed) {
                        }
                    });
                }
                if(adapterView.getItemAtPosition(i) == (Filter.SIZE)){
                    invisEverything();
                    firstFilter.setText("Minimum Size: ");
                    firstFilter.setVisibility(View.VISIBLE);
                    minSize.setVisibility(View.VISIBLE);

                    secondFilter.setText("Maximum Size: ");
                    secondFilter.setVisibility(View.VISIBLE);
                    maxSize.setVisibility(View.VISIBLE);
                }
                if(adapterView.getItemAtPosition(i) == (Filter.CITY)){
                    invisEverything();
                    firstFilter.setText("City: ");
                    firstFilter.setVisibility(View.VISIBLE);
                    citySpinner.setAdapter(new ArrayAdapter<City>(getApplicationContext(), android.R.layout.simple_spinner_item, City.values()));
                    citySpinner.setVisibility(View.VISIBLE);
                    citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterCity, View view, int pos, long l) {
                            cityFilter = City.valueOf(adapterCity.getItemAtPosition(pos).toString());
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterCity) {
                        }
                    });
                }
                if(adapterView.getItemAtPosition(i) == (Filter.PRICE)){
                    invisEverything();
                    firstFilter.setText("Minimum Price: ");
                    firstFilter.setVisibility(View.VISIBLE);
                    minPrice.setVisibility(View.VISIBLE);
                    secondFilter.setText("Maximum Price: ");
                    secondFilter.setVisibility(View.VISIBLE);
                    maxPrice.setVisibility(View.VISIBLE);
                }
                if(adapterView.getItemAtPosition(i) == Filter.FACILITY){
                    invisEverything();
                    ac.setVisibility(View.VISIBLE);
                    bathtub.setVisibility(View.VISIBLE);
                    balcony.setVisibility(View.VISIBLE);
                    wifi.setVisibility(View.VISIBLE);
                    refrigerator.setVisibility(View.VISIBLE);
                    restaurant.setVisibility(View.VISIBLE);
                    fitnessCenter.setVisibility(View.VISIBLE);
                    swimmingPool.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                roomFilter();
                System.out.println("memek");
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ac:
                if (ac.isChecked())
                    facility.add(Facility.AC);
                break;
            case R.id.bathtub:
                if (bathtub.isChecked())
                    facility.add(Facility.Bathtub);
                break;
            case R.id.balcony:
                if (balcony.isChecked())
                    facility.add(Facility.Balcony);
                break;
            case R.id.wifi:
                if (wifi.isChecked())
                    facility.add(Facility.WiFi);
                break;
            case R.id.refrigerator:
                if (refrigerator.isChecked())
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


    protected List<Room> roomFilter(){
        System.out.println("Facility filter: ");
        System.out.println(facility);
        mApiService.getFilteredRoom(MainActivity.currPage, MainActivity.pageSize, Double.parseDouble(minPrice.getText().toString()), Double.parseDouble(maxPrice.getText().toString()), cityFilter, bedFilter,
                Integer.parseInt(minSize.getText().toString()), Integer.parseInt(maxSize.getText().toString()), facility).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                LoginActivity.filterUsed = 1;
                if(response.isSuccessful()){
                    nameList.clear();
                    filteredRoom.clear();
                    filteredRoom.addAll(response.body());
                    for(Room i : filteredRoom){
                        nameList.add(i.name);
                    }
                    System.out.println("name list sini : " + nameList);
                    Collections.sort(nameList);
                }

                System.out.println("Name list: ");
                System.out.println(nameList);
                startActivity(new Intent(FilterActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {

            }
        });
        return null;
    }

    public void invisEverything(){
        minSize.setVisibility(View.INVISIBLE);
        maxSize.setVisibility(View.INVISIBLE);
        citySpinner.setVisibility(View.INVISIBLE);
        minPrice.setVisibility(View.INVISIBLE);
        maxPrice.setVisibility(View.INVISIBLE);
        bedSpinner.setVisibility(View.INVISIBLE);
        secondFilter.setVisibility(View.INVISIBLE);
        ac.setVisibility(View.INVISIBLE);
        bathtub.setVisibility(View.INVISIBLE);
        balcony.setVisibility(View.INVISIBLE);
        wifi.setVisibility(View.INVISIBLE);
        refrigerator.setVisibility(View.INVISIBLE);
        restaurant.setVisibility(View.INVISIBLE);
        fitnessCenter.setVisibility(View.INVISIBLE);
        swimmingPool.setVisibility(View.INVISIBLE);
    }

    public enum Filter{
        SIZE, PRICE, FACILITY, CITY, BEDTYPE
    }
}