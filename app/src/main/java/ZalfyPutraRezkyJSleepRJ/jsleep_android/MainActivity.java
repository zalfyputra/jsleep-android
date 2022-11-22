package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import java.io.*;
import java.util.*;

import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Account;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Room;

public class MainActivity extends AppCompatActivity {
    protected static Account accountLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        //Read JSON to ListView
        String name;
        ArrayList<Room> roomList = new ArrayList<Room>();
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
                Intent move = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(move);
                return true;
            case R.id.add_button:
                startActivity(new Intent(this, RegisterActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}