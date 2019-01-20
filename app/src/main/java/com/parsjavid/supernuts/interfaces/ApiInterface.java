/*
Copyright 2014 David Morrissey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.parsjavid.supernuts.interfaces;

import com.parsjavid.supernuts.BuildConfig;
import com.parsjavid.supernuts.models.ApiSuccess;
import com.parsjavid.supernuts.models.Product;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiInterface {

//    @FormUrlEncoded
//    @POST(BuildConfig.getCallLog)
//    Observable<List<CallLogItem>> getCallLog(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST(BuildConfig.addcallLog)
//    Call<ResponseBody> AddCallLog(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST(BuildConfig.Register)
    Call<ResponseBody> EmdadApplicantRegister(@FieldMap Map<String, String> data);

    @GET(BuildConfig.LoadProducts)
    Call<List<Product>> LoadProducts();

    @GET(BuildConfig.LoadProductById)
    Call<Product> LoadProductById(@Path("productId")Long productId);

    @FormUrlEncoded
    @POST(BuildConfig.Verification)
    Call<ResponseBody> Verification(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST(BuildConfig.SaveProductOrder)
    Call<ApiSuccess> SaveProductOrder(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST("api/apiNews/")
//    Observable<List<CommonFeedItem>> News(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST("api/emdadapplicantcamerashow/")
//    Observable<List<CommonFeedItem>> EmdadCamera(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST(BuildConfig.SelectPlaces)
//    Call<List<PlaceFeedItem>> Places(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST(BuildConfig.CoordinatePlaces)
//    Call<List<PlaceFeedItem>> CoordinatePlaces(@FieldMap Map<String, String> data);
//
//    @GET(BuildConfig.SelectPlacesdetail)
//    Call<ResponseBody> PlaceDetails(@Path("placeId") String UserId);
//
//    @FormUrlEncoded
//    @POST(BuildConfig.GetAllQuestion)
//    Observable<List<QuestionFeedItem>> GetAllQuestion(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST(BuildConfig.GetMyQuestion)
//    Observable<List<QuestionFeedItem>> GetAllMYQuestion(@FieldMap Map<String, String> data);
//
    @FormUrlEncoded
    @POST(BuildConfig.RefreshToken)
    Call<ResponseBody> RefreshToken(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST("api/EmdadMessage/")
//    Call<ResponseBody> EmdadMessage(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST("Api/Store/gtc/Unit")
//    Call<List<ProvinceItem>> GetUnit(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST("Api/Store/Gtc/UnitReport")
//    Call<List<UnitItem>> UnitReport(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST("Api/Store/Gtc/Ostan/")
//    Call<List<ProvinceItem>> GetOstan(@Field("code") String code);
//
//    @FormUrlEncoded
//    @POST("Api/Store/Gtc/Ostan/")
//    Call<List<ProvinceItem>> GetOstanCitys(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST("Api/Store/gtc/UnitType")
//    Call<List<UnitTypeItem>> GetUnitType(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST(BuildConfig.onlineexperts)
//    Observable<ResponseBody> OnlineExperts(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST(BuildConfig.AddRate)
//    Call<ResponseBody> AddCallRating(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST("Api/Store/Gtc/RAssignmentReportUnit")
//    Observable<List<StaticticsModel>> AssignmentReportUnit(@FieldMap Map<String, String> data);
//
//    @FormUrlEncoded
//    @POST("Api/Store/Gtc/RAssignmentReport")
//    Observable<List<StaticticsModel>> RAssignmentReport(@FieldMap Map<String, String> data);
//
//    @Multipart
//    @POST(BuildConfig.Profilepic)
//    Call<ResponseBody> Profilepic(@Part MultipartBody.Part filePart, @PartMap Map<String, RequestBody> params);
//
//    @Multipart
//    @POST(BuildConfig.applicantRequest)
//    Call<ResponseBody> EmdadapplicantRequest(@Part MultipartBody.Part[] surveyImage, @PartMap Map<String, RequestBody> params);
//
//    @Multipart
//    @POST(BuildConfig.applicantQuestion)
//    Observable<ResponseBody> Question(@Part MultipartBody.Part[] surveyImage, @PartMap Map<String, RequestBody> params);
//
//    @Multipart
//    @POST("api/EmdadApplicantCamera/")
//    Call<ResponseBody> EmdadApplicantCamera(@Part MultipartBody.Part[] surveyImage, @PartMap Map<String, RequestBody> params);
//
//    @POST("api/EmdadApplicantUpdateApp/")
//    Call<ResponseBody> UpdateApp();
//
//    @GET("api/emdadactivity/")
//    Observable<List<CategoryItem>> Emdadactivity();
//
//    @GET("api/EmdadApplicantRequest/")
//    Call<ResponseBody> GetTypeRequest();
//
//    @GET("api/apinewsdetails/{NewsId}")
//    Call<ResponseBody> NewsDetails(@Path("NewsId") String UserId);
//
//    @GET(BuildConfig.GetProfile)
//    Call<ResponseBody> GetProfile(@Path("UserId") String UserId);
//
//    @GET(BuildConfig.getmyanswer)
//    Call<ResponseBody> MyAnswer(@Path("QuestionId") String QuestionId);
//
//    @GET(BuildConfig.weathercondition)
//    Call<ResponseBody> Weather(@Path("CityId") String CityId);
//
//    @GET(BuildConfig.Inquery)
//    Call<ResponseBody> Inquery(@Path("NationalId") String NationalId);
//
//    @FormUrlEncoded
//    @POST(BuildConfig.expertanswer)
//    Call<ResponseBody> AnswerQuestion(@FieldMap Map<String, String> data);
//
//    @GET("Api/Store/Goods/{GoodsId}")
//    Call<List<DialogDetailedModel>> GetGoods(@Path("GoodsId") String GoodsId);
//
//    @GET(BuildConfig.AgriAssignment)
//    Call<List<ReceiptModel>> AgriAssignment(@Path("customerId") String customerId, @Path("goodsCode") String goodsCode);
//
//    @FormUrlEncoded
//    @POST(BuildConfig.Login)
//    Call<ResponseBody> Login(@FieldMap Map<String, String> data);
    /*@GET("api/Search")
    Call<ResponseBody> topSearch(@Query("query") String query);*/

//    @GET("api/EmdadApplicantGetAdvertising")
//    Call<ResponseBody> GetAdvertise();

//    @POST(BuildConfig.PlaceUnit)
//    Call<ResponseBody> RegisterPlace(@Body UnitPlaceItem data);


//    @POST("auth/api-key")
//    Call<AccessTokenResponse> ACCESS_TOKEN_CALL(@Body AccessToken token);
//
//    @POST("/videos")
//    Call<VideoIdResponse> GetVideoId(VideoId item);
//
//    /*EmdadMarket*/
//    @GET("api/getAmazingProduct")
//    Call<List<ParentModel>> getProduct();

    @FormUrlEncoded
    @POST("api/getProductinfo")
    Call<ResponseBody> getProductinfo(@FieldMap Map<String, String> data);
}