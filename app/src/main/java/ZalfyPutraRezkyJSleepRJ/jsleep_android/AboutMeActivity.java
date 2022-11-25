package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Account;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Renter;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.BaseApiService;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    TextView name, email, balance, nameRenter, addressRenter, phoneNumberRenter;
    EditText nameRenterBox, addressRenterBox, phoneNumberRenterBox, balanceBox;
    Button registerRenter, registerButton, cancelButton, addButton;
    CardView cardInput, cardInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        if(getSupportActionBar() != null){
            setTitle("Back");
        }

        // Get data from intent
        name = findViewById(R.id.nameDisplay);
        email = findViewById(R.id.emailDisplay);
        balance = findViewById(R.id.balanceDisplay);
        nameRenterBox = findViewById(R.id.nameRenterBox);
        addressRenterBox = findViewById(R.id.addressRenterBox);
        phoneNumberRenterBox = findViewById(R.id.phoneNumberRenterBox);
        balanceBox = findViewById(R.id.balanceBox);
        registerRenter = findViewById(R.id.registerRenter);
        registerButton = findViewById(R.id.registerButton);
        cancelButton = findViewById(R.id.cancelButton);
        addButton = findViewById(R.id.addButton);
        cardInput = findViewById(R.id.cardInput);

        // Get data from intent
        name.setText(MainActivity.nameLog);
        email.setText(MainActivity.emailLog);
        balance.setText(MainActivity.balanceLog);

        // Register renter
        /**
        if(MainActivity.renter == null){
            registerRenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardInput.setVisibility(View.VISIBLE);
                    registerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println("Register renter");
                        }
                    });
                }
            });
        } else {
            registerRenter.setVisibility(View.INVISIBLE);
            cardInput.setVisibility(View.INVISIBLE);
            nameRenter.setText(MainActivity.renter.name);
            addressRenter.setText(MainActivity.renter.address);
            phoneNumberRenter.setText(MainActivity.renter.phoneNumber);
            cardInfo.setVisibility(View.VISIBLE);
        }
    }
    protected Renter registerRenter(){
        mApiService.registerRenter(MainActivity.idLog, nameRenter.getText().toString(), addressRenter.getText().toString(), phoneNumberRenter.getText().toString()).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()){
                    Renter renter;
                    renter = response.body();
                    Toast.makeText(mContext, "Renter successfully registered", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(AboutMeActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }
            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                System.out.println("Fail");
                Toast.makeText(mContext, "Renter already registered", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
         **/
    }
}