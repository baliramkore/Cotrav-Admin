package com.cotrav.cotrav_admin.retrofit;

import com.cotrav.cotrav_admin.model.home_model.DashBoardApiResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetDashBoardDataAPI
{

    @FormUrlEncoded
    @POST(APIurls.DASHBOARD)
    Call<DashBoardApiResponse> getDashBoardData(
            @Header("Authorization") String authorization,
            @Header("usertype") String userType,
            @Field("admin_id") String adminId);
}
