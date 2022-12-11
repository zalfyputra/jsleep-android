package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Renter;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.BaseApiService;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Account Details, Renter Information, and Renter Registration
 * @author Zalfy Putra Rezky
 */
public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    TextView nameDisplay, emailDisplay, balanceDisplay, nameRenter, addressRenter, phoneRenter;
    EditText name, address, phone, balanceBox;
    Button registerRenter, registerButton, cancelButton, addButton;
    LinearLayout regLayout, renterLayout;
    CardView registerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        // Add a back button to the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            setTitle("");
        }

        // About Me
        nameDisplay = findViewById(R.id.nameDisplay);
        emailDisplay = findViewById(R.id.emailDisplay);
        balanceDisplay = findViewById(R.id.balanceDisplay);
        nameDisplay.setText(MainActivity.nameLog);
        emailDisplay.setText(MainActivity.emailLog);
        balanceDisplay.setText(String.valueOf(MainActivity.balanceLog));
        regLayout = findViewById(R.id.regLayout);
        registerRenter = findViewById(R.id.registerRenter);

        // Topup Balance
        balanceBox = findViewById(R.id.balanceBox);
        addButton = findViewById(R.id.addButton);

        // Register Renter
        registerView = findViewById(R.id.registerView);
        name = findViewById(R.id.nameBox);
        address = findViewById(R.id.addressBox);
        phone = findViewById(R.id.phoneBox);
        registerButton = findViewById(R.id.registerButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Renter Info
        renterLayout = findViewById(R.id.renterLayout);
        nameRenter = findViewById(R.id.nameRenter);
        addressRenter = findViewById(R.id.addressRenter);
        phoneRenter = findViewById(R.id.phoneRenter);

        if(MainActivity.renter == null){
            regLayout.setVisibility(View.VISIBLE);
            registerView.setVisibility(View.INVISIBLE);
            renterLayout.setVisibility(View.INVISIBLE);
            registerRenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    regLayout.setVisibility(View.INVISIBLE);
                    registerView.setVisibility(View.VISIBLE);
                    registerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Renter renter = registerRenter();
                        }
                    });
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            regLayout.setVisibility(View.VISIBLE);
                            registerView.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            });
        } else {
            displayRenter();
        }
    }
    protected Renter registerRenter(){
        mApiService.registerRenter(MainActivity.idLog, name.getText().toString(), address.getText().toString(), phone.getText().toString()).enqueue(new Callback<Renter>()  {
            @Override
            public void onResponse(@NonNull Call<Renter> call, @NonNull Response<Renter> response) {
                if(response.isSuccessful()){
                    Renter renter = response.body();
                    System.out.println(renter.toString());
                    Toast.makeText(mContext, "Renter is successfully registered", Toast.LENGTH_LONG).show();
                    displayRenter();
                    Intent move = new Intent(AboutMeActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }
            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                Toast.makeText(mContext, "Renter is already registered", Toast.LENGTH_LONG).show();
                System.out.println(t);
            }
        });
        return null;
    }

    public void displayRenter(){
        regLayout.setVisibility(View.INVISIBLE);
        registerView.setVisibility(View.INVISIBLE);
        renterLayout.setVisibility(View.VISIBLE);

        nameRenter = findViewById(R.id.nameRenter);
        addressRenter = findViewById(R.id.addressRenter);
        phoneRenter = findViewById(R.id.phoneRenter);

        nameRenter.setText(MainActivity.renter.name);
        addressRenter.setText(MainActivity.renter.address);
        phoneRenter.setText(MainActivity.renter.phoneNumber);
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