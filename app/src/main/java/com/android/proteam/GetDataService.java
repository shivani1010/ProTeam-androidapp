package com.android.proteam;

import com.android.proteam.model.GenerateOTPModel;
import com.android.proteam.model.HealthDetailsModel;
import com.android.proteam.model.RegisterModel;
import com.android.proteam.model.SearchByMobileModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetDataService
{

    @FormUrlEncoded
    @POST("/proTeam/apis/public/getUserByPhone")
    Call<SearchByMobileModel> searchByMobileNo(@Field("ph_no") String ph_no);


    @FormUrlEncoded
    @POST("/proTeam/apis/public/register")
    Call<RegisterModel> register(@Field("first_name") String first_name,@Field("middle_name") String middle_name,@Field("last_name") String last_name,@Field("ph_no") String ph_no,@Field("city_id") String city_id,@Field("case") String caser);

    @FormUrlEncoded
    @POST("/proTeam/apis/public/logBodyTemp")
    Call<HealthDetailsModel> updateHealthDetails(@Field("cid") int cid, @Field("body_temp") String body_temp, @Field("remarks") String remarks, @Field("emer_alert") String emer_alert);

    @Multipart
    @POST("/proTeam/apis/public/uploadSelfie")
    Call<HealthDetailsModel> uploadSelfie(@Part MultipartBody.Part image, @Part("cid") RequestBody customer_id);

    @FormUrlEncoded
    @POST("/proTeam/apis/public/send_otp")
    Call<GenerateOTPModel> generateOtp(@Field("ph_no") String ph_no);

}
