package com.cotrav.cotrav_admin.retrofit;

import com.cotrav.cotrav_admin.model.admin_model.AdminApiResponse;
import com.cotrav.cotrav_admin.model.all_cities_model.AllCitiesApiResponse;
import com.cotrav.cotrav_admin.model.assesmentcity_model.AssesmentCityApiResponse;
import com.cotrav.cotrav_admin.model.assesmentcode_model.AssesmentCodeApiResponse;
import com.cotrav.cotrav_admin.model.employees_model.EmployeeApiResponse;
import com.cotrav.cotrav_admin.model.entities_model.EntityApiResponse;
import com.cotrav.cotrav_admin.model.group_model.AuthenticatorResponse;
import com.cotrav.cotrav_admin.model.group_model.GroupsApiResponse;
import com.cotrav.cotrav_admin.model.rates_model.RatesApiResponse;
import com.cotrav.cotrav_admin.model.spoc_model.SpocApiResponse;
import com.cotrav.cotrav_admin.model.subgroup_model.SubGroupApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetUtilitiesApi
{

    @FormUrlEncoded
    @POST(APIurls.getAllCities)
    Call<AllCitiesApiResponse> getAllCities(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId);


    @FormUrlEncoded
    @POST(APIurls.billingEntity)
    Call<EntityApiResponse> getBillingEntities(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId);


    @FormUrlEncoded
    @POST(APIurls.companyRates)
    Call<RatesApiResponse> getcompanyRates(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId);

    @FormUrlEncoded
    @POST(APIurls.assesmentCities)
    Call<AssesmentCityApiResponse> getAssesmentCities(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId);

    @FormUrlEncoded
    @POST(APIurls.assesmentCodes)
    Call<AssesmentCodeApiResponse> getAssesmentCodes(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId);

    @FormUrlEncoded
    @POST(APIurls.adminGroups)
    Call<GroupsApiResponse> getAllAdminGroups(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId);

    @FormUrlEncoded
    @POST(APIurls.adminGroupAuth)
    Call<AuthenticatorResponse> getAuthenticator(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("group_id")String groupId);



    @FormUrlEncoded
    @POST(APIurls.adminSubGroupAuth)
    Call<AuthenticatorResponse> getSubAuthenticator(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("group_id")String groupId);




    @FormUrlEncoded
    @POST(APIurls.adminSubGroups)
    Call<SubGroupApiResponse> getAllAdminSubGroups(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId);


    @FormUrlEncoded
    @POST(APIurls.getAdmins)
    Call<AdminApiResponse> getAdmins(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId);

    @FormUrlEncoded
    @POST(APIurls.getSpocs)
    Call<SpocApiResponse> getSpocs(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId);

    @FormUrlEncoded
    @POST(APIurls.getEmployees)
    Call<EmployeeApiResponse> getEmployees(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId);

}
