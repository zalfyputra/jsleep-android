package ZalfyPutraRezkyJSleepRJ.jsleep_android.request;

import java.util.*;
import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.*;
import retrofit2.*;
import retrofit2.http.*;
/**
 * A base API service that contains all the API calls.
 * @author Zalfy Putra Rezky
 */
public interface BaseApiService {
    @POST("account/login")
    Call<Account> getAccount(@Query("email") String email, @Query("password") String password);
    @POST("account/register")
    Call<Account> registerAccount(@Query("name") String name, @Query("email") String email, @Query("password") String password);
    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id, @Query("username") String username, @Query("address") String address, @Query("phoneNumber") String phoneNumber);
    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom(@Query("page") int page, @Query("pageSize")int pageSize);
    @POST("room/create")
    Call<List<Room>> createRoom(@Query("accountId") int id, @Query("name") String name, @Query("size") int size, @Query("price") int price,
                                @Query("facility") ArrayList<Facility> facility, @Query("city")City city, @Query("address") String address, @Query("typeBed")BedType typeBed);
    @POST("account/{id}/topUp")
    Call<Account> topUp(@Path("id") int id, @Query("balance") double balance);
    @POST("payment/create")
    Call<List<Payment>> createPayment(@Query("buyerId") int buyerId, @Query("renterId") int renterId, @Query("roomId") int roomId, @Query("from") String from, @Query("to") String to);
    @POST("payment/{id}/accept")
    Call<List<Payment>> acceptPayment(@Path("id") int id);
    @GET("payment/{id}/getWaitingPayment")
    Call<List<Payment>> waitingPayment(@Path("id") int id, @Query("page") int page, @Query("pageSize") int pageSize);
    @GET("payment/{id}/getCompletedPayment")
    Call<List<Payment>> completedPayment(@Path("id") int id, @Query("page") int page, @Query("pageSize") int pageSize);
    @GET("account/{id}/getById")
    Call<Account> getById(@Path("id") int id);
    @GET("account/getAllAccount")
    Call<List<Account>> getAllACcount();
    @GET("room/getFilteredRoom")
    Call<List<Room>> getFilteredRoom(@Query("page") int page, @Query("pageSize") int pageSize, @Query("priceMin") double priceMin, @Query("priceMax") double priceMax, @Query("city") City city,
                                     @Query("bed") BedType bed, @Query("minSize") int minSize, @Query("maxSize") int maxSize, @Query("facility") ArrayList<Facility> facility);
    @GET("payment/{id}/getMyOnGoingPayment")
    Call<List<Payment>> getOnGoingPayment(@Path("id") int id);
    @POST("payment/{id}/cancel")
    Call<List<Payment>> cancel(@Path("id") int id);
    @GET("room/searchRoom")
    Call<List<Room>> searchRoom(@Query("roomName") String roomName, @Query("page") int page, @Query("pageSize") int pageSize);
}
