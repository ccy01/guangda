package com.example.ccy.tes.retrofit

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by ccy on 2017/11/9.
 */
interface ApiService {
    @GET("cas_server/login?service=http%3a%2f%2f202.192.18.182%2fLogin_gzdx.aspx")
    fun getLoginArg(): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("cas_server/login?service=http%3a%2f%2f202.192.18.182%2fLogin_gzdx.aspx")
    fun login(@HeaderMap headers: Map<String, String>, @FieldMap map: Map<String, String>): Observable<ResponseBody>

    @GET("xf_xstyxk.aspx")//xh=1619300046&xm=%B2%DC%B4%CA%D3%EE&gnmkdm=N121205
    fun getPhysicalCourse(@Header("Referer") header: String, @Query("xh") number: String,
                          @Query("xm") name: String, @Query("gnmkdm") gnmkdm: String = "N121205"): Observable<ResponseBody>

    @GET("xsgrxx.aspx")
    fun getMessage(@Header("Referer") header: String, @Query("xh") number: String,
                   @Query("xm") name: String, @Query("gnmkdm") gnmkdm: String = "N121205"): Observable<ResponseBody>

    @GET("{path}")
    fun getImg(@Header("Referer") header: String, @Path("path") path: String): Observable<ResponseBody>

    @GET("xsxkqk.aspx")
    fun getSelectedCourse( @Header("Referer") header: String, @Query("xh") number: String,
                          @Query("xm") name: String, @Query("gnmkdm") gnmkdm: String = "N121615",@Header("Host") host: String = "202.192.18.182"): Observable<ResponseBody>

    @GET("xf_xsqxxxk.aspx")
    fun getXuanXiuCourse(@Header("Host") host: String = "202.192.18.182", @Header("Referer") header: String, @Query("xh") number: String,
                         @Query("xm") name: String, @Query("gnmkdm") gnmkdm: String = "N121203"): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("xf_xsqxxxk.aspx")
    fun getXuanXiuNextCourse(@Header("Referer") header: String, @FieldMap(encoded = true) map: Map<String, String>, @Query("xh") number: String,
                             @Query("xm") name: String, @Query("gnmkdm") gnmkdm: String = "N121203", @Header("Host") host: String = "202.192.18.182"): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("xsxkqk.aspx")
    fun getSelectedCourse(@Header("Referer") header: String, @FieldMap(encoded = true) map: Map<String, String>, @Query("xh") number: String,
                          @Query("xm") name: String, @Query("gnmkdm") gnmkdm: String = "N121615", @Header("Host") host: String = "202.192.18.182"): Observable<ResponseBody>

    @GET("xscj_gc.aspx")
    fun getScore( @Header("Referer") header: String, @Query("xh") number: String,
                           @Query("xm") name: String, @Query("gnmkdm") gnmkdm: String = "N121605",@Header("Host") host: String = "202.192.18.182"): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("xscj_gc.aspx")
    fun getScore(@Header("Referer") header: String, @FieldMap(encoded = true) map: Map<String, String>, @Query("xh") number: String,
                          @Query("xm") name: String, @Query("gnmkdm") gnmkdm: String = "N121605", @Header("Host") host: String = "202.192.18.182"): Observable<ResponseBody>

    @GET("tjkbcx.aspx")
    fun getCourse( @Header("Referer") header: String, @Query("xh") number: String,
                  @Query("xm") name: String, @Query("gnmkdm") gnmkdm: String = "N121601",@Header("Host") host: String = "202.192.18.182"): Observable<ResponseBody>

}