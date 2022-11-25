package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Renter;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Room;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.BaseApiService;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.UtilsApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    //Declare variable
    BaseApiService mApiService;
    Context mContext;
    String name;
    public static String emailLog;
    public static String passwordLog;
    public static String balanceLog;
    public static String nameLog;
    public static Renter renter;
    public static int idLog;
    ArrayList<Room> roomList = new ArrayList<Room>();
    ArrayList<String> nameList = new ArrayList<>();
    int currentPage = 0;
    int pageSize = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
        Button nextButton = findViewById(R.id.nextButton);
        EditText page = findViewById(R.id.findPage);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getAPIService();
        mContext = this;
        getAllRoom(currentPage, pageSize);
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
        ListView lv = findViewById(R.id.mainList);
        lv.setAdapter(arrA);
        System.out.println("Current page" + currentPage);
        **/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        //Read JSON to ListView
        Gson gson = new Gson();
        try {
            InputStream filepath = getAssets().open("randomRoomList.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
            Room[] accountList = new Gson().fromJson(reader, Room[].class);
            Collections.addAll(roomList, accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> names = new ArrayList<String>();
        for(Room room : roomList){
            names.add(room.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        ListView listView = findViewById(R.id.mainList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button:
                return super.onOptionsItemSelected(item);
            case R.id.user_button:
                startActivity(new Intent(MainActivity.this, AboutMeActivity.class));
                return true;
            case R.id.add_button:
                startActivity(new Intent(MainActivity.this, CreateRoomActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected List<Room> getAllRoom(int page, int pagesize){
        mApiService.getAllRoom(page, pagesize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    if(roomList != null)
                        roomList.clear();
                    roomList.addAll(response.body());
                    for(Room i : roomList){
                        nameList.add(i.name);
                    }
                    Collections.sort(nameList);

                    System.out.println(roomList);
                    List<String> nameList = new ArrayList<>();
                    for (Room i : roomList){
                        nameList.add(i.name);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Room not found", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}