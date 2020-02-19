package com.cotrav.cotrav_admin.retrofit;

import com.cotrav.cotrav_admin.model.admin_model.DeleteAdminResponse;
import com.cotrav.cotrav_admin.model.assesmentcity_model.DeleteAssementCityResponse;
import com.cotrav.cotrav_admin.model.entities_model.DeleteEntityResponse;
import com.cotrav.cotrav_admin.model.spoc_model.DeleteSpocResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DeleteUtilitiesApi {

    @FormUrlEncoded
    @POST(APIurls.deleteEntities)
    Call<DeleteEntityResponse> deleteEntities
            (
             @Header("Authorization")String authorization,
             @Header("usertype")String userType,
             @Field("user_id") String userId,
             @Field("entity_id")String entityId
            );
    @FormUrlEncoded
    @POST(APIurls.deleteRates)
    Call<DeleteEntityResponse> deleteRates
            (
                    @Header("Authorization")String authorization,
                    @Header("usertype")String userType,
                    @Field("user_id") String userId,
                    @Field("entity_id")String entityId
            );
    @FormUrlEncoded
    @POST(APIurls.deleteAssCity)
    Call<DeleteAssementCityResponse> deleteAssesmentCity
            (
                    @Header("Authorization")String authorization,
                    @Header("usertype")String userType,
                    @Field("user_id") String entityId,
                    @Field("city_id")String userId
            );
    @FormUrlEncoded
    @POST(APIurls.deleteAssCode)
    Call<DeleteEntityResponse> deleteAssesmentCode
            (
                    @Header("Authorization")String authorization,
                    @Header("usertype")String userType,
                    @Field("code_id") String userId,
                    @Field("user_id")String entityId
            );
    @FormUrlEncoded
    @POST(APIurls.deleteGroup)
    Call<DeleteEntityResponse> deleteGroups
            (
                    @Header("Authorization")String authorization,
                    @Header("usertype")String userType,
                    @Field("user_id") String userId,
                    @Field("group_id")String entityId
            );
    @FormUrlEncoded
    @POST(APIurls.deleteSubGroup)
    Call<DeleteEntityResponse> deleteSubGroups
            (
                    @Header("Authorization")String authorization,
                    @Header("usertype")String userType,
                    @Field("user_id") String userId,
                    @Field("subgroup_id")String entityId
            );
    @FormUrlEncoded
    @POST(APIurls.deleteAdmins)
    Call<DeleteAdminResponse> deleteAdmins
            (
                    @Header("Authorization")String authorization,
                    @Header("usertype")String userType,
                    @Field("admin_id") String adminId,
                    @Field("user_id")String userId
            );
    @FormUrlEncoded
    @POST(APIurls.deleteSpocs)
    Call<DeleteSpocResponse> deleteSpocs
            (
                    @Header("Authorization")String authorization,
                    @Header("usertype")String userType,
                    @Field("spoc_id") String userId,
                    @Field("user_id")String entityId
            );
    @FormUrlEncoded
    @POST(APIurls.deleteEmployees)
    Call<DeleteEntityResponse> deleteEmployees
            (
                    @Header("Authorization")String authorization,
                    @Header("usertype")String userType,
                    @Field("employee_id") String userId,
                    @Field("user_id")String entityId
            );
}
