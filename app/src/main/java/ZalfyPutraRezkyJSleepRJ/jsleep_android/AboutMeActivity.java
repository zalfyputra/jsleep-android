package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        if(getSupportActionBar() != null){
            setTitle("About Me");
        }
    }
}