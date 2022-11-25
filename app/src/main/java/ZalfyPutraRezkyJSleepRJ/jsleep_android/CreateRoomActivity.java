package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CreateRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        if(getSupportActionBar() != null){
            setTitle("Back");
        }
    }
}