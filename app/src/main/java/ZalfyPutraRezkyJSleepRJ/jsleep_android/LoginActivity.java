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


public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText email, password;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getAPIService();
        mContext = this;
        email = findViewById(R.id.emailBox);
        password = findViewById(R.id.passwordBox);
        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestAccount();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });
    }

    protected Account requestAccount() {
        mApiService.getAccount(0).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account;
                    account = response.body();
                    System.out.println("Login Successful");
                    System.out.println(account.toString());
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println("Login Failed");
                System.out.println(t.toString());
                Toast.makeText(mContext,"no Account id=0", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}