package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Payment;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.BaseApiService;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Activity to accept payment
 * @author Zalfy Putra Rezky
 */
public class AcceptOrderConfirmActivity extends AppCompatActivity {
    TextView name, buyerId, room, bed, from, to, price;
    Button acceptOrder;
    BaseApiService mApiService;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_order_confirm);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        // Get data from previous activity
        name = findViewById(R.id.name);
        buyerId = findViewById(R.id.buyerId);
        room = findViewById(R.id.room);
        bed = findViewById(R.id.bed);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        price = findViewById(R.id.price);

        name.setText("Buyer's name: " + AcceptOrderActivity.customerList.get(AcceptOrderActivity.listSelected));
        buyerId.setText("Buyer's ID: " + AcceptOrderActivity.idSelected);
        room.setText("Room id: " + AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).getRoomId());
        bed.setText("Bed Type: " + MainActivity.roomList.get(AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).getRoomId()).bedType.toString());
        from.setText("From: " + AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).from.toString());
        to.setText("To: " + AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).to.toString());
        price.setText("Price: Rp " + MainActivity.roomList.get(AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).getRoomId()).price.price);
        acceptOrder = findViewById(R.id.AcceptOrder);
        acceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiService.acceptPayment(AcceptOrderActivity.watingListPayment.get(AcceptOrderActivity.listSelected).id).enqueue(new Callback<List<Payment>>() {
                    @Override
                    public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                        Toast.makeText(mContext, "Payment accepted", Toast.LENGTH_SHORT);
                        startActivity(new Intent(AcceptOrderConfirmActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<List<Payment>> call, Throwable t) {
                        Toast.makeText(mContext, "Failed to accept order", Toast.LENGTH_SHORT);
                        startActivity(new Intent(AcceptOrderConfirmActivity.this, MainActivity.class));
                    }
                });
            }
        });

    }
}