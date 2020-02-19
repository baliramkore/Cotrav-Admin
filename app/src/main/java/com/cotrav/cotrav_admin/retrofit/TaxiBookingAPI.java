package com.cotrav.cotrav_admin.retrofit;

import com.cotrav.cotrav_admin.model.home_model.taxi_model.show_taxi.TaxiBookingApiResponse;
import com.cotrav.cotrav_admin.model.home_model.taxi_model.view_taxi.TaxiDetailApiResponse;
import com.cotrav.cotrav_admin.ui.home.bus.AddBusBookingActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TaxiBookingAPI
{
    @FormUrlEncoded
    @POST(APIurls.TAXIBOOKINGS)
    Call<TaxiBookingApiResponse> getTaxiBookings(@Header("Authorization") String Authorization,
                                                 @Header("usertype") String Usertype,
                                                 @Field("corporate_id") String corporate_id,
                                                 @Field("booking_type") String booking_type,
                                                 @Field("page_no") String pageNo

    );
    @FormUrlEncoded
    @POST(APIurls.TAXIBOOKINGSDETAILS)
    Call<TaxiDetailApiResponse> getTaxiBookingDetails(@Header("Authorization") String Authorization,
                                                      @Header("USERTYPE") String Usertype,
                                                      @Field("corporate_id") String corporate_id,
                                                      @Field("booking_id") String booking_id)
            ;
    @FormUrlEncoded
    @POST(APIurls.addTaxiBooking)
    Call<AddBusBookingActivity.Responce> addTaxiBooking(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("user_id") String userId,
            @Field("corporate_id") String corporateId,
            @Field("spoc_id") String spocId,
            @Field("group_id") String groupId,
            @Field("subgroup_id") String subgroupId,
            @Field("tour_type") String tourType,
            @Field("pickup_city") String pickUpCity,
            @Field("pickup_location") String pickUpLocation,
            @Field("drop_location") String dropLocation,
            @Field("booking_datetime") String bookingDateTime,
            @Field("pickup_datetime") String pickup_datetime,
            @Field("entity_id")String entity_id,
            @Field("taxi_type") String taxiType,
            @Field("assessment_code") String assessmentCode,
            @Field("assessment_city_id") String assessmentCityId,
            @Field("reason_booking") String reasonBooking,
            @Field("no_of_seats") String seatCount,
            @Field("employees") ArrayList<String> employees,
            @Field("package_id") String pacakageId,
            @Field("no_of_days") String noOfDays);
}
