package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Account;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.BaseApiService;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Activity to login to the app using registered accounts
 * Cancel order and make payment
 * @author Zalfy Putra Rezky
 */
public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText email, password;
    Button loginButton, registerButton;
    Context mContext;
    public static int filterUsed = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Declare variable
        mApiService = UtilsApi.getApiService();
        mContext = this;
        email = findViewById(R.id.emailBox);
        password = findViewById(R.id.passwordBox);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        // Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestLogin();
            }
        });

        // Register Button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });
    }

    protected Account requestLogin() {
        mApiService.getAccount(email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                    MainActivity.emailLog = email.getText().toString();
                    MainActivity.passwordLog = password.getText().toString();
                    MainActivity.nameLog = account.name;
                    MainActivity.balanceLog = String.valueOf(account.balance);
                    MainActivity.renter = account.renter;
                    MainActivity.idLog = account.id;
                    Toast.makeText(mContext,"Login Successful", Toast.LENGTH_LONG).show();
                    Intent move = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext,"Incorrect Email or Password", Toast.LENGTH_LONG).show();
                System.out.println("Incorrect Email or Password!");
            }
        });
        return null;
    }
}