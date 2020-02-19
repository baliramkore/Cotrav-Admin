package com.cotrav.cotrav_admin.retrofit;

import com.cotrav.cotrav_admin.model.home_model.train_model.add_train.train_station.AllStationCityResponse;
import com.cotrav.cotrav_admin.model.home_model.train_model.add_train.train_type.TrainTypesApiRespose;
import com.cotrav.cotrav_admin.model.home_model.train_model.show_train.TrainBookingApiResponse;
import com.cotrav.cotrav_admin.model.home_model.train_model.view_train.ViewTrainBookingDetailsResponse;
import com.cotrav.cotrav_admin.ui.home.bus.AddBusBookingActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TrainBookingAPI
{

    @FormUrlEncoded
    @POST(APIurls.TRAINBOOKINGS)
    Call<TrainBookingApiResponse> getTrainBookings(@Header("Authorization") String Authorization,
                                                   @Header("USERTYPE") String Usertype,
                                                   @Field("corporate_id") String spoc_id,
                                                   @Field("booking_type") String booking_type,
                                                   @Field("page_no") String pageNo
    );

    @FormUrlEncoded
    @POST(APIurls.TRAINBOOKINGSDETAILS)
    Call<ViewTrainBookingDetailsResponse> getTrainBookingDetails(@Header("Authorization") String Authorization,
                                                                 @Header("USERTYPE") String Usertype,
                                                                 @Field("booking_id") String booking_id);

    @POST(APIurls.TRAINTYPE)
    Call<TrainTypesApiRespose> getTrainType(@Header("Authorization") String Authorization,
                                            @Header("USERTYPE") String Usertype);

    @POST(APIurls.STATIONCITIES)
    Call<AllStationCityResponse> getAllStationCities(@Header("Authorization") String Authorization,
                                                     @Header("usertype") String usertype);

    @FormUrlEncoded
    @POST(APIurls.addTrainBooking)
    Call<AddBusBookingActivity.Responce> addTrainBooking(
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
            @Field("preferred_train") String preferredTrain,
            @Field("assessment_code") String assessmentCode,
            @Field("assessment_city_id") String assessmentCityId,
            @Field("reason_booking") String reasonBooking,
            @Field("no_of_seats") String seatCount,
            @Field("employees") ArrayList<String> employees,
            @Field("train_type") String trainType);
}
