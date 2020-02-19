package com.cotrav.cotrav_admin.retrofit;

import com.cotrav.cotrav_admin.model.home_model.flight_model.show_flight.FlightBookingApiResponse;
import com.cotrav.cotrav_admin.model.home_model.flight_model.view_flight.ViewFlightBookingDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FlightBookingAPI {

    @FormUrlEncoded
    @POST(APIurls.FLIGHTBOOKINGS)
    Call<FlightBookingApiResponse> getFlightBookings(@Header("Authorization") String Authorization,
                                                     @Header("USERTYPE") String Usertype,
                                                     @Field("corporate_id") String corporate_id,
                                                     @Field("booking_type") String booking_type,
                                                     @Field("page_no") String pageNo
    );

    @FormUrlEncoded
    @POST(APIurls.FLIGHTBOOKINGSDETAILS)
    Call<ViewFlightBookingDetailsResponse> getFlightBookingDetails(@Header("Authorization") String Authorization,
                                                                   @Header("USERTYPE") String Usertype,
                                                                   @Field("booking_id") String booking_id);
}
