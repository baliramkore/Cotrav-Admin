package com.cotrav.cotrav_admin.retrofit;



import com.cotrav.cotrav_admin.model.taxi_types_model.TaxiTypeApiResponse;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddTaxiBookingAPI {


    @POST(APIurls.TAXITYPE)
    Call<TaxiTypeApiResponse> getTaxiType(@Header("Authorization") String Authorization,
                                          @Header("USERTYPE") String Usertype);
}
