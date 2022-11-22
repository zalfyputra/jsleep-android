package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {
    TextView name, email, balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        if(getSupportActionBar() != null){
            setTitle("About Me");
        }

        TextView username = findViewById(R.id.name);
        username.setText(MainActivity.accountLogin.name);
        TextView email = findViewById(R.id.email);
        email.setText(MainActivity.accountLogin.email);
        TextView balance = findViewById(R.id.balance);
        balance.setText(String.valueOf(MainActivity.accountLogin.balance));
    }
}