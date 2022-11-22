package ZalfyPutraRezkyJSleepRJ.jsleep_android.request;

import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Account;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Room;
import retrofit2.*;
import retrofit2.http.*;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);
    @GET("room/{id}")
    Call<Room> getRoom(@Path("id") int id);
    @POST("account/register")
    Call<Account> register(@Query("username") String username, @Query("email") String email, @Query("password") String password);
    @POST("account/login")
    Call<Account> login(@Query("email") String email, @Query("password") String password);
    @POST("account/{id}/registerRenter")
    Call<Account> registerRenter(@Path("id") int id,
                                 @Query("username") String username,
                                 @Query("address") String address,
                                 @Query("phoneNumber") String phoneNumber);
    @POST("account/{id}/topUp")
    Call<Account> topUp(@Path("id") int id, @Query("amount") double amount);
}
