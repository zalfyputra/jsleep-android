package ZalfyPutraRezkyJSleepRJ.jsleep_android;

import static ZalfyPutraRezkyJSleepRJ.jsleep_android.MainActivity.accountList;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Account;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Payment;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.BaseApiService;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Activity for accepting order
 * @author Zalfy Putra Rezky
 */
public class AcceptOrderActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    private ListView orderList, completeOrder;
    public static int listSelected, idSelected;
    public static ArrayList<String> customerList;
    public static ArrayList<Payment> watingListPayment;
    public static ArrayList<Account> customerAcc;
    public static ArrayList<Payment> completedPaymentList;
    public static ArrayList<String> customerHistoryList;
    int page = 0;
    int pageSize = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setHomeAsUpIndicator(R.drawable.backbutton);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(getSupportActionBar() != null){
            setTitle("");
        }

        mApiService = UtilsApi.getApiService();
        mContext = this;
        orderList = findViewById(R.id.orderList);
        completeOrder = findViewById(R.id.completeOrder);
        watingListPayment = new ArrayList<Payment>();
        customerList = new ArrayList<>();
        customerAcc = new ArrayList<>();
        completedPaymentList = new ArrayList<>();
        customerHistoryList = new ArrayList<>();
        getWaitingPayment(page, pageSize);
        getCompletedPayment(page, pageSize);
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listSelected = i;
                for(int k = 0; k < accountList.size(); k++){
                    if(customerList.get(i).equals(accountList.get(k).name)){
                        System.out.println("ID Sama : " + accountList.get(k).id);
                        idSelected = accountList.get(k).id;
                    }
                    System.out.println("k = "  + k);
                }
                Toast.makeText(mContext, "Selected: " + i, Toast.LENGTH_SHORT);
                startActivity(new Intent(AcceptOrderActivity.this, AcceptOrderConfirmActivity.class));
            }
        });
        System.out.println(customerAcc);
    }

    public void displayPaymentList(int page){
        for(int i = 0; i < customerList.size(); i++){
            System.out.println("Renter " + i + ": " + customerList.get(i));
        }
        System.out.println("Customer acc size : " + customerAcc.size());
        System.out.println("Customer history size: ");
        ArrayAdapter<String> customerArray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customerList);
        orderList.setAdapter(customerArray);
        ArrayAdapter<String> historyArray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customerHistoryList);
        completeOrder.setAdapter(historyArray);
    }

    protected List<Payment> getWaitingPayment(int page, int pageSize){
        mApiService.waitingPayment(MainActivity.idLog, page, pageSize).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if(response.isSuccessful()){
                    if(watingListPayment != null)
                        watingListPayment.clear();
                    watingListPayment.addAll(response.body());
                    System.out.println("List payment: ");
                    System.out.println(watingListPayment);
                    for(int i = 0; i < watingListPayment.size(); i++){
                        for(int j = 0; j < accountList.size(); j++) {
                            if(accountList.get(j).id == watingListPayment.get(i).buyerId)
                                customerList.add(accountList.get(j).name);
                        }
                    }
                    displayPaymentList(page);
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Order list not found", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected List<Payment> getCompletedPayment(int page, int pageSize){
        mApiService.completedPayment(MainActivity.idLog, page, pageSize).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if(response.isSuccessful()){
                    if(completedPaymentList != null)
                        completedPaymentList.clear();
                    completedPaymentList.addAll(response.body());
                    System.out.println("Completed list: ");
                    System.out.println(completedPaymentList);
                    for(int i = 0; i < completedPaymentList.size(); i++){
                        for(int j = 0; j < accountList.size(); j++) {
                            if(accountList.get(j).id == completedPaymentList.get(i).buyerId)
                                customerHistoryList.add(accountList.get(j).name);
                        }
                    }
                    displayPaymentList(page);
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
        return null;
    }

}