package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Account;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.BaseApiService;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username, email, password;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getAPIService();
        mContext = this;
        Button registerButton = findViewById(R.id.registerButton);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account register = requestRegister();
            }
        });
    }
    protected Account requestRegister() {
        username = findViewById(R.id.usernameBox);
        email = findViewById(R.id.emailBox);
        password = findViewById(R.id.passwordBox);
        mApiService.register(username.getText().toString(), email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Intent move = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(move);
                    Toast.makeText(mContext,"Account registered successfully", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext,"Account already registered", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}