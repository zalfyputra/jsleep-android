package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Payment;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.BaseApiService;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Activity to confirm room order
 * Cancel order and make payment
 * @author Zalfy Putra Rezky
 */
public class ConfirmRoomOrderActivity extends AppCompatActivity {
    TextView name, renter, address, city, roomName, bedType, from, to, price;
    Button accept, cancel;
    BaseApiService mApiService;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_room_order);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        name = findViewById(R.id.name);
        renter = findViewById(R.id.renter);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        roomName = findViewById(R.id.roomName);
        bedType = findViewById(R.id.bedType);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        price = findViewById(R.id.price);
        accept = findViewById(R.id.acceptButton);
        cancel = findViewById(R.id.cancelButton);
        if(MainActivity.isOnGoing != 1){
            cancel.setVisibility(View.INVISIBLE);
            name.setText("Name: " + MainActivity.nameLog);
            renter.setText("Renter: " + MainActivity.roomList.get(DetailRoomActivity.selectedRoom.accountId).name);
            address.setText("Address: " + DetailRoomActivity.selectedRoom.address);
            city.setText("City: " + DetailRoomActivity.selectedRoom.city.toString());
            roomName.setText("Room : " + DetailRoomActivity.selectedRoom.name);
            bedType.setText("Bed Type: " + DetailRoomActivity.selectedRoom.bedType.toString());
            from.setText("From : " + DetailRoomActivity.fromDate);
            to.setText("To: " + DetailRoomActivity.toDate);
            price.setText("Price: Rp " + String.valueOf(DetailRoomActivity.selectedRoom.price.price));
            accept.setVisibility(View.VISIBLE);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createPayment();
                }
            });
        }else if(MainActivity.isOnGoing == 1){
            accept.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.VISIBLE);
            name.setText("Name: " + MainActivity.nameLog);
            renter.setText("Renter: " + MainActivity.accountList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).renterId).name);
            address.setText("Address: " + MainActivity.roomList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).getRoomId()).address);
            city.setText("City: " + MainActivity.roomList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).getRoomId()).city);
            roomName.setText("Room : " + MainActivity.roomList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).getRoomId()).name);
            bedType.setText("Bed Type: " + MainActivity.roomList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).getRoomId()).bedType);
            from.setText("From: " + MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).from.toString());
            to.setText("From: " + MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).to.toString());
            price.setText("Price: " + MainActivity.roomList.get(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).getRoomId()).price.price);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mApiService.cancel(MainActivity.paymentListonGoing.get(MainActivity.paymentSelected).id).enqueue(new Callback<List<Payment>>() {
                        @Override
                        public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                            Toast.makeText(mContext, "Order has been cancelled", Toast.LENGTH_SHORT);
                            startActivity(new Intent(ConfirmRoomOrderActivity.this, MainActivity.class));
                        }

                        @Override
                        public void onFailure(Call<List<Payment>> call, Throwable t) {
                            Toast.makeText(mContext, "Failed to cancel order", Toast.LENGTH_SHORT);
                        }
                    });
                }
            });
        }

    }

    protected List<Payment> createPayment(){
        mApiService.createPayment(MainActivity.idLog, DetailRoomActivity.selectedRoom.accountId, DetailRoomActivity.selectedRoom.id,
                DetailRoomActivity.fromDate, DetailRoomActivity.toDate).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                Toast.makeText(mContext, "Payment Successful", Toast.LENGTH_SHORT).show();
                Intent backtoMain = new Intent(ConfirmRoomOrderActivity.this, MainActivity.class);
                startActivity(backtoMain);
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}