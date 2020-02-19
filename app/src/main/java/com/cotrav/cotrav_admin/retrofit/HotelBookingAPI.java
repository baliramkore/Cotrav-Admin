package com.cotrav.cotrav_admin.retrofit;

import com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.hotel_type.HotelTypeApiResponse;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.add_hotel.room_type.RoomTypeApiResponse;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.show_hotel.HotelBookingApiResponse;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.view_hotel.HotelDetailApiResponse;
import com.cotrav.cotrav_admin.ui.home.bus.AddBusBookingActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HotelBookingAPI {
    @FormUrlEncoded
    @POST(APIurls.HOTELBOOKINGS)
    Call<HotelBookingApiResponse> getHotelBookings(@Header("Authorization") String Authorization,
                                                   @Header("usertype") String Usertype,
                                                   @Field("corporate_id") String corporate_id,
                                                   @Field("booking_type") String booking_type,
                                                   @Field("page_no") String pageNo
                                                   );

    @FormUrlEncoded
    @POST(APIurls.HOTELBOOKINGSDETAILS)
    Call<HotelDetailApiResponse> getHotelBookingDetails(@Header("Authorization") String Authorization,
                                                        @Header("usertype") String Usertype,
                                                        @Field("booking_id") String booking_id);


    @POST(APIurls.ROOMTYPE)
    Call<RoomTypeApiResponse> getRoomType(@Header("Authorization") String Authorization,
                                          @Header("USERTYPE") String Usertype);


    @POST(APIurls.HOTELTYPE)
    Call<HotelTypeApiResponse> getHotelType(@Header("Authorization") String Authorization,
                                            @Header("USERTYPE") String Usertype);


    @FormUrlEncoded
    @POST(APIurls.addHotelBooking)
    Call<AddBusBookingActivity.Responce> addHotelBooking(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("user_id") String userId,
            @Field("corporate_id") String corporateId,
            @Field("spoc_id") String spocId,
            @Field("group_id") String groupId,
            @Field("subgroup_id") String subgroupId,
            @Field("booking_datetime") String bookingDateTime,
            @Field("checkin_datetime") String journeyDateTime,
            @Field("checkout_datetime")String dropDateTime,
            @Field("billing_entity_id") String entityId,
            @Field("preferred_hotel") String preferredHotel,
            @Field("assessment_code") String assessmentCode,
            @Field("assessment_city_id") String assessmentCityId,
            @Field("reason_booking") String reasonBooking,
            @Field("no_of_seats") String seatCount,
            @Field("employees") ArrayList<String> employees,
            @Field("room_type_id") String roomTypeId,
            @Field("bucket_priority_1") String priority1,
            @Field("bucket_priority_2") String priority2,
            @Field("preferred_area") String prefArea,
            @Field("from_area_id") String FromAreaId,
            @Field("from_city_id") String FromCityId);
}
