package com.cotrav.cotrav_admin.retrofit;



import com.cotrav.cotrav_admin.model.home_model.bus_model.show_bus.BusBookingApiResponse;
import com.cotrav.cotrav_admin.model.home_model.bus_model.view_bus.ViewBusBookingResponse;
import com.cotrav.cotrav_admin.ui.home.bus.AddBusBookingActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BusBookingAPI {
    @FormUrlEncoded
    @POST(APIurls.BUSBOOKINGS)
    Call<BusBookingApiResponse> getBusBookings(@Header("Authorization") String Authorization,
                                               @Header("USERTYPE") String Usertype,
                                               @Field("corporate_id") String corporate_id,
                                               @Field("booking_type") String booking_type,
                                               @Field("page_no") String pageNo

    );

    @FormUrlEncoded
    @POST(APIurls.BUSBOOKINGSDETAILS)
    Call<ViewBusBookingResponse> getBusBookingDetails(@Header("Authorization") String Authorization,
                                                      @Header("USERTYPE") String Usertype,
                                                      @Field("booking_id") String booking_id);


    @FormUrlEncoded
    @POST(APIurls.addBusBooking)
    Call<AddBusBookingActivity.Responce> addBusBooking(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("user_id") String userId,
            @Field("corporate_id") String corporateId,
            @Field("spoc_id") String spocId,
            @Field("group_id") String groupId,
            @Field("subgroup_id") String subgroupId,
            @Field("from") String fromCity,
            @Field("to") String toCity,
            @Field("booking_datetime") String bookingDateTime,
            @Field("journey_datetime") String journeyDateTime,
            @Field("journey_datetime_to")String dropDateTime,
            @Field("entity_id") String entityId,
            @Field("preferred_bus") String preferredBus,
            @Field("assessment_code") String assessmentCode,
            @Field("assessment_city_id") String assessmentCityId,
            @Field("reason_booking") String reasonBooking,
            @Field("no_of_seats") String seatCount,
            @Field("employees") ArrayList<String> employees,
            @Field("bus_type") String busType);
}
