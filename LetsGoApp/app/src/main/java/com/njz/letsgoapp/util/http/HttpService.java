package com.njz.letsgoapp.util.http;

import com.njz.letsgoapp.bean.BasePageModel;
import com.njz.letsgoapp.bean.BaseResponse;
import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.bean.find.DynamicCommentModel;
import com.njz.letsgoapp.bean.home.BannerModel;
import com.njz.letsgoapp.bean.home.DynamicListModel;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.bean.home.EvaluateModel;
import com.njz.letsgoapp.bean.home.GuideDetailModel;
import com.njz.letsgoapp.bean.home.GuideListModel;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.bean.home.ServiceDetailModel;
import com.njz.letsgoapp.bean.home.ServiceListModel;
import com.njz.letsgoapp.bean.login.LoginInfoModel;
import com.njz.letsgoapp.bean.login.LoginModel;
import com.njz.letsgoapp.bean.login.VerifyModel;
import com.njz.letsgoapp.bean.mine.FansListModel;
import com.njz.letsgoapp.bean.mine.LabelModel;
import com.njz.letsgoapp.bean.mine.MyCommentModel;
import com.njz.letsgoapp.bean.mine.MyInfoData;
import com.njz.letsgoapp.bean.order.AliPay;
import com.njz.letsgoapp.bean.MovieSubject;
import com.njz.letsgoapp.bean.order.OrderDetailChildModel;
import com.njz.letsgoapp.bean.order.OrderDetailModel;
import com.njz.letsgoapp.bean.order.OrderModel;
import com.njz.letsgoapp.bean.order.OrderRefundDetailModel;
import com.njz.letsgoapp.bean.order.OrderRefundModel;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.bean.other.ProvinceModel;
import com.njz.letsgoapp.bean.send.SendOrderCancelModel;
import com.njz.letsgoapp.bean.send.SendOrderModel;
import com.njz.letsgoapp.bean.send.SendOrderRefundModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by LGQ
 * Time: 2018/7/17
 * Function: 请求
 */

public interface HttpService {

    //获取豆瓣Top250 榜单
    @GET("top250")
    Observable<MovieSubject> getTop250(@Query("start") int start, @Query("count")int count);

    @GET("alipay/appPay")
    Observable<AliPay> appPay();

    @GET("wxpay/appPay")
    Observable<AliPay> appPayWX();

    //登录系列   start
    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResponse<LoginModel>> login(
            @Field("mobile") String mobile,
            @Field("password") String password
    );

    //注册
    @FormUrlEncoded
    @POST("sms/msgCheckRegister")
    Observable<BaseResponse<EmptyModel>> msgCheckRegister(
            @Field("mobile") String mobile,
            @Field("msg") String msg,
            @Field("password") String password
    );

    //找回密码
    @FormUrlEncoded
    @POST("sms/msgCheckFindBy")
    Observable<BaseResponse<EmptyModel>> msgCheckFindBy(
            @Field("mobile") String mobile,
            @Field("msg") String msg,
            @Field("newPassword") String newPassword
    );

    //手机验证码登录
    @FormUrlEncoded
    @POST("sms/msgCheckLogin")
    Observable<BaseResponse<LoginModel>> msgCheckLogin(
            @Field("mobile") String mobile,
            @Field("msg") String msg
    );

    //短信验证码
    @FormUrlEncoded
    @POST("sms/userSmsSend")
    Observable<BaseResponse<VerifyModel>> userSmsSend(
            @Field("mobile") String mobile,
            @Field("type") String type
    );


    //修改密码
    @FormUrlEncoded
    @POST("user/changePwd")
    Observable<BaseResponse<EmptyModel>> changePwd(
//            @Field("X-Nideshop-Token") String token,
            @Field("password") String password,
            @Field("newPassword") String newPassword
    );

    //修改手机号
    @FormUrlEncoded
    @POST("sms/updateMobile")
    Observable<BaseResponse<EmptyModel>> updateMobile(
//            @Field("X-Nideshop-Token") String token,
            @Field("mobile") String mobile,
            @Field("msg") String msg,
            @Field("password") String password
    );

    //登录系列   end


    //首页 start

    //首页获取热门导游
    @GET("orderReviews/sortTop")
        Observable<BaseResponse<List<GuideModel>>> orderReviewsSortTop(
            @Query("location") String location
    );

    //首页获取热动态
    @GET("region/getSterByLocation")
    Observable<BaseResponse<DynamicListModel>> regionGetSterByLocation(
            @Query("location") String location,
            @Query("limit") int limit,
            @Query("page") int page
    );

    //获取导游列表
    @GET("guide/sortTop10ByLocation")
    Observable<BaseResponse<GuideListModel>> guideSortTop10ByLocation(
            @Query("location") String location,
            @Query("type") int type,
            @Query("limit") int limit,
            @Query("page") int page
    );

    //获取导游列表
    @GET("guide/sortTop10ByLocation")
    Observable<BaseResponse<GuideListModel>> guideSortTop10ByLocation2(
            @Query("location") String location,
            @Query("type") int type,
            @Query("limit") int limit,
            @Query("page") int page,
            @QueryMap Map<String, String> maps
    );

    //获取导游详情
    @GET("guide/findGuideDetails")
    Observable<BaseResponse<GuideDetailModel>> guideFindGuideDetails(
            @Query("location") String location,
            @Query("guideId") int guideId
    );

    //获取导游服务详情
    @GET("guide/getGuideService")
    Observable<BaseResponse<ServiceDetailModel>> getGuideService(
            @Query("serviceId") int serviceId
    );

    //获取导游单个服务列表
    @GET("guide/getServiceList")
    Observable<BaseResponse<List<ServiceListModel>>> getServiceList(
            @Query("guideId") int guideId,
            @Query("serveType") int serveType
    );

    //--------首页 end--------

    //-------发现 start-------
    //全部动态列表
    @GET("friend/findAll")
    Observable<BaseResponse<DynamicListModel>> friendFindAll(
            @Query("location") String location,
            @Query("limit") int limit,
            @Query("page") int page
    );

    //friend/friendSter 我关注的动态列表
    @GET("friend/friendSter")
    Observable<BaseResponse<List<DynamicModel>>> friendFriendSter(
            @Query("limit") int limit,
            @Query("page") int page
    );


    //sys/oss/sendSter 发布动态
    @Multipart
    @POST("up/sendSter")
    Observable<BaseResponse<EmptyModel>> sendSter(
            @Part("location") RequestBody location,
            @Part("lon") double lon,
            @Part("lat") double lat,
            @Part("content") RequestBody content,
            @Part List<MultipartBody.Part> files

    );

    //-------发现 end---------


    //-------订单 start---------
    //up/userReview 订单评价
    @Multipart
    @POST("up/userReview")
    Observable<BaseResponse<EmptyModel>> upUserReview(
            @Part("orderId") int orderId,
            @Part("guideId") int guideId,
            @Part("guideService") int guideService,
            @Part("carCondition") int carCondition,
            @Part("buyService") int buyService,
            @Part("travelArrange") int travelArrange,
            @Part("userContent") RequestBody userContent,
            @Part List<MultipartBody.Part> files
    );

    //orderReviews/findGuideReviews 导游评价列表
    @GET("orderReviews/findGuideReviews")
    Observable<BaseResponse<BasePageModel<EvaluateModel>>> orderReviewsFindGuideReviews(
            @Query("guideId") int guideId,
            @Query("limit") int limit,
            @Query("page") int page
    );

    //order/createOrder 创建订单
    @POST("order/createOrder")
    Observable<BaseResponse<PayModel>> orderCreateOrder(
            @Body SendOrderModel data
    );

    //orderPay/aliPay 阿里支付
    @FormUrlEncoded
    @POST("orderPay/aliPay")
    Observable<BaseResponse<String>> orderPayAliPay(
            @Field("outTradeNo") String toutTradeNo
    );

    //order/queryOrderList 订单列表
    @GET("order/queryOrderList")
    Observable<BaseResponse<BasePageModel<OrderModel>>> orderQueryOrderList(
            @Query("payStatus") int payStatus,
            @Query("limit") int limit,
            @Query("page") int page
    );

    //订单详情order/queryOrder
    @GET("order/queryOrder")
    Observable<BaseResponse<OrderDetailModel>> orderQueryOrder(
            @Query("orderId") int orderId
    );

    //退款单列表 orderRefund/queryOrderRefundList
    @GET("orderRefund/queryOrderRefundList")
    Observable<BaseResponse<BasePageModel<OrderRefundModel>>> orderRefundQueryOrderRefundList(
            @Query("limit") int limit,
            @Query("page") int page
    );

    //退款单详情orderRefund/queryOrderRefundDetails
    @GET("orderRefund/queryOrderRefundDetails")
    Observable<BaseResponse<OrderRefundDetailModel>> orderRefundQueryOrderRefundDetails(
            @Query("refundId") int refundId
    );

    //取消订单order/cancelOrder
    @POST("order/cancelOrder")
    Observable<BaseResponse<EmptyModel>> orderCancelOrder(
            @Body SendOrderCancelModel data
    );

    //orderRefund/aliRefund 申请退款
    @POST("orderRefund/aliRefund")
    Observable<BaseResponse<EmptyModel>> orderRefundAliRefund(
            @Body SendOrderRefundModel data
    );

    //orderRefund/refundAnalysis 退款分析
    @POST("orderRefund/refundAnalysis")
    Observable<BaseResponse<OrderRefundModel>> orderRefundRefundAnalysis(
            @Body SendOrderRefundModel data
    );

    //-------订单 end---------

    //-------我的 start-------
    //user/changePersonalData 修改个人资料.
    @POST("user/changePersonalData")
    Observable<BaseResponse<EmptyModel>> userChangePersonalData(
            @Body MyInfoData data
            );

    //user/userLabels 标签
    @GET("user/userLabels")
    Observable<BaseResponse<List<LabelModel>>> userLabels(
    );

    //up/upload
    @Multipart
    @POST("up/upload")
    Observable<BaseResponse<String>> upUpload(
            @Part MultipartBody.Part file
    );

    //user/findFocus 个人主页
    @GET("user/viewZone")
    Observable<BaseResponse<LoginInfoModel>> userViewZone(
            @Query("userId") int userId
    );

    //friend/personalFriendSter  个人动态
    @GET("friend/personalFriendSter")
    Observable<BaseResponse<DynamicListModel>> friendPersonalFriendSter(
            @Query("userId") int userId,
            @Query("limit") int limit,
            @Query("page") int page
    );

    //friend/findByFriendSterId 动态详情
    @GET("friend/findByFriendSterId")
    Observable<BaseResponse<DynamicModel>> friendFindByFriendSterId(
            @Query("friendSterId") int friendSterId
    );

    //user/findFans 我的粉丝
    @GET("user/findFans")
    Observable<BaseResponse<FansListModel>> userFindFans(
            @Query("userId") int userId,
            @Query("limit") int limit,
            @Query("page") int page
    );

    //user/findFocus 我的关注
    @GET("user/findFocus")
    Observable<BaseResponse<FansListModel>> userFindFocus(
            @Query("userId") int userId,
            @Query("limit") int limit,
            @Query("page") int page
    );

    //friend/queryLikes
    @GET("friend/queryLikes")
    Observable<BaseResponse<FansListModel>> friendQueryLikes(
            @Query("friendSterId") int friendSterId,
            @Query("limit") int limit,
            @Query("page") int page
    );

    //friend/queryLikes 点赞
    @FormUrlEncoded
    @POST("friend/doLike")
    Observable<BaseResponse<EmptyModel>> friendDoLike(
            @Field("friendSterId") int friendSterId
    );

    //friend/doUnLike 取消点赞
    @FormUrlEncoded
    @POST("friend/doUnLike")
    Observable<BaseResponse<EmptyModel>> friendDoUnLike(
            @Field("friendSterId") int friendSterId
    );

    //user/focusOn 关注
    @FormUrlEncoded
    @POST("user/focusOn")
    Observable<BaseResponse<EmptyModel>> userFocusOn(
            @Field("focusId") int focusId
    );

    //user/focusOff 取消关注
    @FormUrlEncoded
    @POST("user/focusOff")
    Observable<BaseResponse<EmptyModel>> userFocusOff(
            @Field("focusId") int focusId
    );

    //friend/discuss 动态评论
    @FormUrlEncoded
    @POST("friend/discuss")
    Observable<BaseResponse<DynamicCommentModel>> friendDiscuss(
            @Field("friendSterId") int friendSterId,
            @Field("discussUserId") int discussUserId,
            @Field("discussContent") String discussContent,
            @Field("toUserId") int toUserId
    );

    //我的评论 friend/myDiscuss
    @GET("friend/myDiscuss")
    Observable<BaseResponse<List<MyCommentModel>>> friendMyDiscuss(
            @Query("type") int type
    );

    //-------我的 end-------



    //--------other start---------
    //城市选择 region/findProAndCity
    @GET("region/findProAndCity")
    Observable<BaseResponse<List<ProvinceModel>>> regionFindProAndCity(
    );

    //轮播图 banner/findAll
    @GET("banner/findByTypeAndGuideId")
    Observable<BaseResponse<List<BannerModel>>> bannerFindByType(
            @Query("type") int type,
            @Query("id") int id
    );
    //--------other end---------




}
