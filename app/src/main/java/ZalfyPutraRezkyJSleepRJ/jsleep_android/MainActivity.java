package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.*;
import java.util.*;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Account;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Payment;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Renter;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Room;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.BaseApiService;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.*;
/**
 * Main page of the app that contains the list of rooms
 * Can go to room details, account details, and create rooms
 * @author Zalfy Putra Rezky
 */
public class MainActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    String name;
    EditText page;
    Button next, prev, go, filter;
    TextView welcomeText, noOnGoing;
    public static String emailLog;
    public static String passwordLog;
    public static String balanceLog;
    public static String nameLog;
    public static Renter renter;
    public static int idLog;
    public static int roomSelected;
    public static int listSelected;
    public static int paymentSelected;
    private ListView lv, onGoinglv;
    private SearchView searchBar;
    private String searchedRoom;
    public static int isOnGoing; //Nandain kalo yang dipilih list onGoing
    public static ArrayList<Room> roomList = new ArrayList<Room>();
    public static ArrayList<Room> roomSearchList = new ArrayList<>();
    public static ArrayList<Account> accountList = new ArrayList<>();
    public static ArrayList<String> nameList = new ArrayList<>();
    public static ArrayList<String> onGoingList = new ArrayList<>();
    public static ArrayList<Payment> paymentListonGoing = new ArrayList<Payment>();
    public static int currPage = 0;
    public static int pageSize = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide shadow and set back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);

        // Declare variable
        mApiService = UtilsApi.getApiService();
        mContext = this;
        nameList = new ArrayList<>();
        lv = findViewById(R.id.mainList);
        onGoinglv = findViewById(R.id.onGoingList);
        prev = findViewById(R.id.prevButton);
        next = findViewById(R.id.nextButton);
        go = findViewById(R.id.goButton);
        page = findViewById(R.id.findPage);
        filter = findViewById(R.id.filterButton);

        // Title
        welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome, " + nameLog);

        // Filter and Search
        if(LoginActivity.filterUsed == 0)
            getAllRoom(currPage, pageSize);
        displayList(currPage);
        getOnGoingPayment();
        searchBar = findViewById(R.id.searchBar);

        // Prev Button
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currPage -= 1;
                displayList(currPage);
            }
        });

        // Next Button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currPage += 1;
                displayList(currPage);
            }
        });

        // Go Button
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameList = new ArrayList<>();
                getAllRoom(currPage, pageSize);
            }
        });

        // Filter Button
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(intent);
            }
        });

        mApiService.getAllACcount().enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if(response.isSuccessful()) {
                    if (accountList != null)
                        accountList.clear();
                    accountList.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                Toast.makeText(mContext, "No Account found", Toast.LENGTH_SHORT);
            }
        });

        // Main List
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isOnGoing = 0;
                listSelected = i;
                System.out.println("Selected: " + nameList.get(listSelected));
                for(Room j : roomList){
                    if(j.name.equals(nameList.get(listSelected))){
                        roomSelected = j.id;
                        System.out.println("Selected id: " + roomSelected);
                    }
                }
                Intent toDetailRoom = new Intent(MainActivity.this, DetailRoomActivity.class);
                startActivity(toDetailRoom);
                Toast.makeText(mContext, "Room : "+ i, Toast.LENGTH_SHORT).show();

            }
        });

        // On Going List
        onGoinglv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int onGoingRoomSelected;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int onGoingSelected, long l) {
                isOnGoing = 1;
                for(Room i : roomList){
                    if(i.name.equals(onGoingList.get(onGoingSelected)))
                        onGoingRoomSelected = i.id;
                }
                for(Payment i : paymentListonGoing){
                    if(i.getRoomId() == onGoingRoomSelected && i.buyerId == idLog)
                        paymentSelected = i.id;
                    startActivity(new Intent(MainActivity.this, ConfirmRoomOrderActivity.class));
                }
            }
        });

        // Search Bar
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchedRoom = s;
                System.out.println(s);
                mApiService.searchRoom(searchedRoom, currPage, pageSize).enqueue(new Callback<List<Room>>() {
                    @Override
                    public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                        if(response.isSuccessful()){
                            System.out.println("masuk tidak");
                            if(roomSearchList != null)
                                roomSearchList.clear();
                            roomSearchList.addAll(response.body());
                            nameList.clear();
                            for(Room i : roomSearchList){
                                nameList.add(i.name);
                            }
                            System.out.println("Name list baru: " + nameList);
                            Collections.sort(nameList);
                        }
                        displayList(currPage);
                    }

                    @Override
                    public void onFailure(Call<List<Room>> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu, menu);
        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                return true;
        }
        if (item.getItemId() == R.id.search_button) {
            Toast.makeText(this, "Nothing to see here :)", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.person_button) {
            Intent aboutMeIntent = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(aboutMeIntent);
            return true;
        } else if (item.getItemId() == R.id.add_button) {
            Intent addRoomIntent = new Intent(MainActivity.this, CreateRoomActivity.class);
            startActivity(addRoomIntent);
            return true;
        } else if (item.getItemId() == R.id.notify_button) {
            Intent addRoomIntent = new Intent(MainActivity.this, AcceptOrderActivity.class);
            startActivity(addRoomIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayOnGoing(){
        if(onGoingList.isEmpty()){
            noOnGoing = findViewById(R.id.noOrderFound);
            onGoinglv.setVisibility(View.INVISIBLE);
            noOnGoing.setVisibility(View.VISIBLE);
        }else{
            ArrayAdapter<String> onGoingRoom = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, onGoingList);
            onGoinglv.setAdapter(onGoingRoom);
        }

    }
    public void displayList(int currpage){
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
        lv.setAdapter(arrA);
    }

    protected List<Payment> getOnGoingPayment(){
        mApiService.getOnGoingPayment(MainActivity.idLog).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> responsePayment) {
                System.out.println("Respon: " + responsePayment);
                if(paymentListonGoing != null)
                    paymentListonGoing.clear();
                System.out.println("MEMEK");
                if(responsePayment.body().isEmpty() == false){
                    System.out.println("kok masuk?");
                    paymentListonGoing.addAll(responsePayment.body());
                    System.out.println("payment di sini: " + paymentListonGoing.get(0).buyerId) ;
                    for(int i = 0; i < paymentListonGoing.size(); i++) {
                        System.out.println("masuk ke " + i);
                        for (int j = 0; j < roomList.size(); j++) {
                            if (roomList.get(j).id == paymentListonGoing.get(i).getRoomId())
                                onGoingList.add(roomList.get(j).name + " (" + paymentListonGoing.get(i).status + ")");
                        }
                    }
                }
                displayOnGoing();

            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
        return null;
    }

    protected List<Room> getAllRoom(int page, int pagesize){
        mApiService.getAllRoom(page, pagesize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(@NonNull Call<List<Room>> call, @NonNull Response<List<Room>> response) {
                if(response.isSuccessful()){
                    if(roomList != null)
                        roomList.clear();
                    roomList.addAll(response.body());
                    for(Room i : roomList)
                        nameList.add(i.name);
                    Collections.sort(nameList);
                    List<String> nameList = new ArrayList<>();
                    for (Room i : roomList){
                        nameList.add(i.name);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Room not found", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }
}