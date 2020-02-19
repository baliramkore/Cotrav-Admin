package com.cotrav.cotrav_admin.retrofit;

import com.cotrav.cotrav_admin.model.entities_model.AddUtilitiesApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddUtilitiesApi
{
    @FormUrlEncoded
    @POST(APIurls.addEntities)
    Call<AddUtilitiesApiResponse> addEntities(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("entity_name") String entityName,
            @Field("billing_city_id") String billingCityId,
            @Field("contact_person_name") String contactName,
            @Field("contact_person_email") String contactEmail,
            @Field("contact_person_no") String contactNumber,
            @Field("address_line_1") String address1,
            @Field("address_line_2") String address2,
            @Field("address_line_3") String address3,
            @Field("gst_id") String gstId,
            @Field("pan_no") String panNumber);


    @FormUrlEncoded
    @POST(APIurls.updateEntities)
    Call<AddUtilitiesApiResponse> updateEntities(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("entity_name") String entityName,
            @Field("billing_city_id") String billingCityId,
            @Field("contact_person_name") String contactName,
            @Field("contact_person_email") String contactEmail,
            @Field("contact_person_no") String contactNumber,
            @Field("address_line_1") String address1,
            @Field("address_line_2") String address2,
            @Field("address_line_3") String address3,
            @Field("gst_id") String gstId,
            @Field("pan_no") String panNumber,
            @Field("entity_id") String entityId

    );


    @FormUrlEncoded
    @POST(APIurls.addAdmins)
    Call<AddUtilitiesApiResponse> addAdmins(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("name") String userName,
            @Field("email") String userEmail,
            @Field("contact_no") String contactNumber,
            @Field("is_radio") String isRadio,
            @Field("is_local") String isLocal,
            @Field("is_outstation") String isOutstation,
            @Field("is_bus") String isBus,
            @Field("is_train") String isTrain,
            @Field("is_hotel") String isHotel,
            @Field("is_meal") String isMeal,
            @Field("is_flight") String isFlight,
            @Field("is_water_bottles") String isWaterBottles,
            @Field("is_reverse_logistics") String isReverseLosgistic,
            @Field("access_token_auth") String isAccessToken,
            @Field("password") String password
            );

    @FormUrlEncoded
    @POST(APIurls.updateAdmins)
    Call<AddUtilitiesApiResponse> updateAdmins(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("name") String entityName,
            @Field("email") String billingCityId,
            @Field("contact_no") String contactName,
            @Field("is_radio") String isRadio,
            @Field("is_local") String isLocal,
            @Field("is_outstation") String isOutstation,
            @Field("is_bus") String isBus,
            @Field("is_train") String isTrain,
            @Field("is_hotel") String isHotel,
            @Field("is_meal") String isMeal,
            @Field("is_flight") String isFlight,
            @Field("is_water_bottles") String isWaterBottles,
            @Field("is_reverse_logistics") String isReverseLosgistic,
            @Field("admin_id") String isAccessToken
    );

    @FormUrlEncoded
    @POST(APIurls.addAssCode)
    Call<AddUtilitiesApiResponse> addAssCodes(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("assessment_code") String assCode,
            @Field("code_desc") String codeDesc,
            @Field("from_date") String fromDate,
            @Field("to_date") String toDate,
            @Field("service_from") String fromServiceDate,
            @Field("service_to") String toServiceDate
    );

    @FormUrlEncoded
    @POST(APIurls.updateAssCode)
    Call<AddUtilitiesApiResponse> updateAssCode(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("assessment_code") String assCode,
            @Field("code_desc") String codeDesc,
            @Field("from_date") String fromDate,
            @Field("to_date") String fromServiceDate,
            @Field("service_from") String toDate,
            @Field("service_to") String toServiceDate,
            @Field("code_id") String codeId
    );

    @FormUrlEncoded
    @POST(APIurls.addAssCity)
    Call<AddUtilitiesApiResponse> addAssCity(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("city_name") String assCity
    );

    @FormUrlEncoded
    @POST(APIurls.updateAssCity)
    Call<AddUtilitiesApiResponse> updateAssCity(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("city_name") String assCity,
            @Field("city_id") String cityId);




    @FormUrlEncoded
    @POST(APIurls.addAdminGroup)
    Call<AddUtilitiesApiResponse> addAdminGroup(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporate_id,
            @Field("user_id") String userId,
            @Field("group_name") String groupName,
            @Field("zone_name") String zoneName,
            @Field("name") String authName,
            @Field("email") String authEmail,
            @Field("cid") String authCID,
            @Field("contact_no") String authContact,

            @Field("is_radio") String isRadio,
            @Field("is_local") String isLocal,
            @Field("is_outstation") String isOutstation,
            @Field("is_bus") String is_bus,
            @Field("is_train") String is_train,
            @Field("is_hotel") String is_hotel,
            @Field("is_meal") String is_meal,
            @Field("is_flight") String is_flight,
            @Field("is_water_bottles") String isWaterBottle,
            @Field("is_reverse_logistics") String isRevLogistic);

    @FormUrlEncoded
    @POST(APIurls.updateAdminGroup)
    Call<AddUtilitiesApiResponse> updateAdminGroup(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("group_name") String groupName,
            @Field("zone_name") String zoneName,
            @Field("group_id") String groupId
            );

    @FormUrlEncoded
    @POST(APIurls.addAdminSubGroup)
    Call<AddUtilitiesApiResponse> addAdminSubGroup(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporate_id,
            @Field("user_id") String userId,
            @Field("subgroup_name") String subgroupName,
            @Field("group_id") String groupId,
            @Field("name") String authName,
            @Field("email") String authEmail,
            @Field("cid") String authCID,
            @Field("contact_no") String authContactNo,
            @Field("is_radio") String isRadio,
            @Field("is_local") String isLocal,
            @Field("is_outstation") String isOutstation,
            @Field("is_bus") String is_bus,
            @Field("is_train") String is_train,
            @Field("is_hotel") String is_hotel,
            @Field("is_meal") String is_meal,
            @Field("is_flight") String is_flight,
            @Field("is_water_bottles") String isWaterBottle,
            @Field("is_reverse_logistics") String isRevLogistic,
            @Field("access_token_auth") String accessTokenAuth,
            @Field("password") String password

    );


    @FormUrlEncoded
    @POST(APIurls.updateAdminSubGroup)
    Call<AddUtilitiesApiResponse> updateAdminSubGroup(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("group_name") String groupName,
            @Field("group_id") String groupId
    );


    @FormUrlEncoded
    @POST(APIurls.addSpocs)
    Call<AddUtilitiesApiResponse> addSpocsByAdmin(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("group_id") String groupId,
            @Field("subgroup_id") String subgroupId,
            @Field("user_cid") String userCID,
            @Field("user_name") String userName,
            @Field("user_contact") String userContact,
            @Field("email") String contactEmail,
            @Field("username") String username,
            @Field("budget") String spocBudget,
            @Field("expense") String spocExpence,
            @Field("is_radio") String isRadio,
            @Field("is_local") String isLocal,
            @Field("is_outstation") String isOutstation,
            @Field("is_bus") String isBus,
            @Field("is_train") String isTrain,
            @Field("is_hotel") String isHotel,
            @Field("is_meal") String isMeal,
            @Field("is_flight") String isFlight,
            @Field("is_water_bottles") String isWaterBottles,
            @Field("is_reverse_logistics") String isReverseLosgistic,
            @Field("access_token_auth") String isAccessToken,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(APIurls.updateSpocs)
    Call<AddUtilitiesApiResponse> updateSpoc(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("group_id") String groupId,
            @Field("subgroup_id") String subgroupId,
            @Field("user_cid") String userCID,
            @Field("user_name") String userName,
            @Field("user_contact") String userContact,
            @Field("email") String contactEmail,
            @Field("username") String username,
            @Field("budget") String spocBudget,
            @Field("expense") String spocExpence,
            @Field("is_radio") String isRadio,
            @Field("is_local") String isLocal,
            @Field("is_outstation") String isOutstation,
            @Field("is_bus") String isBus,
            @Field("is_train") String isTrain,
            @Field("is_hotel") String isHotel,
            @Field("is_meal") String isMeal,
            @Field("is_flight") String isFlight,
            @Field("is_water_bottles") String isWaterBottles,
            @Field("is_reverse_logistics") String isReverseLosgistic,
            @Field("access_token_auth") String isAccessToken,
            @Field("password") String password,
            @Field("spoc_id") String spocId);


    @FormUrlEncoded
    @POST(APIurls.addEmployee)
    Call<AddUtilitiesApiResponse> addEmployee(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("spoc_id") String spocId,
            @Field("billing_entity_id") String billEntityId,
            @Field("core_employee_id") String coreEmpId,
            @Field("employee_cid") String empCId,
            @Field("employee_name") String empName,
            @Field("employee_email") String empEmail,
            @Field("employee_contact") String empContact,
            @Field("age") String empAge,
            @Field("gender") String empGender,
            @Field("id_proof_type") String idProofType,
            @Field("id_proof_no") String idProofNo,
            @Field("is_active") String isActive,
            @Field("fcm_regid") String fcmRegId,
            @Field("is_cxo") String isCXO,
            @Field("designation") String empDesigntn,
            @Field("home_city") String homeCity,
            @Field("home_address") String homeAddress,
            @Field("assistant_id") String assistentId,
            @Field("date_of_birth") String dateOfBirth,
            @Field("password") String password,
            @Field("username") String username);

    @FormUrlEncoded
    @POST(APIurls.updateEmployee)
    Call<AddUtilitiesApiResponse> updateEmployee(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id") String corporateId,
            @Field("user_id") String userId,
            @Field("spoc_id") String entityName,
            @Field("billing_entity_id") String billingCityId,
            @Field("core_employee_id") String coreEmpId,
            @Field("employee_cid") String empId,
            @Field("employee_name") String empName,
            @Field("employee_email") String empEmail,
            @Field("employee_contact") String empContact,
            @Field("age") String empAge,
            @Field("gender") String empGender,
            @Field("id_proof_type") String idProofType,
            @Field("id_proof_no") String idProofNo,
            @Field("is_active") String isActive,
            @Field("fcm_regid") String fcmRegId,
            @Field("is_cxo") String isCxo,
            @Field("designation") String designation,
            @Field("home_city") String homeCity,
            @Field("home_address") String homeAddress,
            @Field("assistant_id") String assistantId,
            @Field("date_of_birth") String dateOfBirth,
            @Field("reporting_manager") String reportingManager,
            @Field("employee_band") String employeeBand,
            @Field("employee_id") String employeeId

    );

}
