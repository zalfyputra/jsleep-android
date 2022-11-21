package ZalfyPutraRezkyJSleepRJ.jsleep_android.request;

import ZalfyPutraRezkyJSleepRJ.jsleep_android.model.Account;
import retrofit2.*;
import retrofit2.http.*;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);
}
