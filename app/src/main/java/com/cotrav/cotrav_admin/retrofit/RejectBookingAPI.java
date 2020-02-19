package com.cotrav.cotrav_admin.retrofit;

import com.cotrav.cotrav_admin.model.RejectBookingResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RejectBookingAPI
{
    @FormUrlEncoded
    @POST(APIurls.REJECTBOOKING_TAXI)
    Call<RejectBookingResponse> rejectTaxiBooking(@Header("Authorization") String authorization,
                                                  @Header("USERTYPE") String userType,
                                                  @Field("booking_id") String bookingId,
                                                  @Field("user_id") String userId);

    @FormUrlEncoded
    @POST(APIurls.REJECTBOOKING_BUS)
    Call<RejectBookingResponse> rejectBusBooking(@Header("Authorization") String authorization,
                                                 @Header("USERTYPE") String userType,
                                                 @Field("booking_id") String bookingId,
                                                 @Field("user_id") String userId);
    @FormUrlEncoded
    @POST(APIurls.REJECTBOOKING_TRAIN)
    Call<RejectBookingResponse> rejectTrainBooking(@Header("Authorization") String authorization,
                                                   @Header("USERTYPE") String userType,
                                                   @Field("booking_id") String bookingId,
                                                   @Field("user_id") String userId);
    @FormUrlEncoded
    @POST(APIurls.REJECTBOOKING_HOTEL)
    Call<RejectBookingResponse> rejectHotelBooking(@Header("Authorization") String authorization,
                                                   @Header("USERTYPE") String userType,
                                                   @Field("booking_id") String bookingId,
                                                   @Field("user_id") String userId);

    @FormUrlEncoded
    @POST(APIurls.REJECTBOOKING_FLIGHT)
    Call<RejectBookingResponse> rejectFlightBooking(@Header("Authorization") String authorization,
                                                    @Header("USERTYPE") String userType,
                                                    @Field("booking_id") String bookingId,
                                                    @Field("user_id") String userId);

}
