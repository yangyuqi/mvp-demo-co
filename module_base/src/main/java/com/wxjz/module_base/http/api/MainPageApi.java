package com.wxjz.module_base.http.api;


import com.wxjz.module_base.base.BaseResponse;
import com.wxjz.module_base.bean.BrannerBean;
import com.wxjz.module_base.bean.CourseDetailBean;
import com.wxjz.module_base.bean.CourseItemBean;
import com.wxjz.module_base.bean.CourseListItemBean;
import com.wxjz.module_base.bean.CourseOutlineBean;
import com.wxjz.module_base.bean.FreeVideoListBean;
import com.wxjz.module_base.bean.LevelListBean;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.PopularMutiItem;
import com.wxjz.module_base.bean.RecommendBean;
import com.wxjz.module_base.bean.RecommendCourseBean;
import com.wxjz.module_base.bean.SchoollistBean;
import com.wxjz.module_base.bean.SearchBean;
import com.wxjz.module_base.bean.SelectVideoBean;
import com.wxjz.module_base.bean.TopTabBean;
import com.wxjz.module_base.bean.UpdateNoteItemBean;
import com.wxjz.module_base.bean.UserInfoBean;
import com.wxjz.module_base.bean.VideoDetailBean;
import com.wxjz.module_base.db.bean.SearchTabBean;
import com.wxjz.module_base.db.bean.SearchTabContentBean;
import com.wxjz.module_base.db.bean.SubjectChapterBean;
import com.wxjz.module_base.db.bean.SubjectHomeBean;
import com.wxjz.module_base.db.bean.SubjectSectionBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by a on 2019/9/3.
 */

public interface MainPageApi {
    /**
     * ********
     * 登录
     * ********
     */
    @FormUrlEncoded
    @POST("cas/v1/tickets")
    Observable<BaseResponse<LoginBean>> postToLogin(@Field("username") String username, @Field("password") String password, @Field("schId") int schId);

    /**
     * *******
     * 获取总的学校列表
     * *******
     */
    @GET("backend-web/school/pageList")
    Observable<BaseResponse<SchoollistBean>> getSchoolList(@Query("page") String page, @Query("rows") String rows);

    /**
     * 首页tab
     *
     * @param levelId
     * @returnGET /api/ownSubject/getSubjectListByLevelId
     */
    @GET("course-select/api/ownSubject/getSubjectListByLevelId")
    Observable<BaseResponse<List<TopTabBean>>> getTopTabs(@Query("levelId") int levelId);

    @GET("course-select/banner/getBannerList")
    Observable<BaseResponse<BrannerBean>> getBanners(@Query("schoolSection") String schoolSection, @Query("gradeId") String gradeId, @Query("schId") String shId);

//    @GET("course-select/banner/getBannerList")
//    Observable<BaseResponse<BrannerBean>> getBanners(@Query("schoolSection") String schoolSection, @Query("gradeId") String gradeId);

    /**
     * 获取通用tab下的课程列表
     *
     * @param
     * @return
     */
    @GET("course-select/api/course/getCourseList")
    Observable<BaseResponse<List<CourseItemBean>>> getCourseOnTab(@Query("id") int id, @Query("courseStatus") int courseStatus, @Query("page") int page, @Query("rows") int rows);

    /**
     * 游客模式下获取学习阶段和年级的接口
     */
    @GET("course-select/api/ownLevel/getLevelList")
    Observable<BaseResponse<List<LevelListBean>>> getLevelListByGuest();

    /**
     * 最受欢迎课程
     *
     * @param levelId
     * @param page
     * @param rows
     * @return
     */
    @GET("course-select/api/ownVideo/getBestWelcomeVideoList")
    Observable<BaseResponse<PopularMutiItem>> getWelcomeCourse(@Query("levelId") int levelId, @Query("gradeId") String gradeId, @Query("page") int page, @Query("rows") int rows);

    @GET("course-select/api/course/getRecommendCourse")
    Observable<BaseResponse<RecommendCourseBean>> getRecommendCourse(@Query("levelId") int levelId, @Query("page") int page);

    @GET("course-select/api/course/getAboutCourse")
    Observable<BaseResponse<RecommendCourseBean>> getAboutCourse(@Query("subId") int subId, @Query("levelId") int levelId, @Query("courseId") int courseId);

    @GET("course-select/api/ownVideo/getFreeVideoList")
    Observable<BaseResponse<FreeVideoListBean>> getFreeCourse(@Query("levelId") int levelId, @Query("gradeId") String gradeId, @Query("page") int page, @Query("rows") int rows);

    @GET("course-select/api/course/getMatchingCourse")
    Observable<BaseResponse<List<SearchTabBean>>> getSearchTab(@Query("level_Id") int levelId);

    @GET("course-select/api/ownVideo/getSearchVideoList")
    Observable<BaseResponse<SearchTabContentBean>> getSearchTabContent(@Query("videoName") String videoName, @Query("gradeId") String gradeId, @Query("levelId") int levelId);

    @GET("course-select/api/ownVideo/getRecommendedVideoList")
    Observable<BaseResponse<RecommendBean>> getRecommend(@Query("levelId") int levelId, @Query("gradeId") String gradeId, @Query("page") int page);

    @GET("course-select/api/ownVideo/getVideoListBySubId")
    Observable<BaseResponse<List<SearchBean>>> getVideoListBySubId(@Query("videoName") String videoName, @Query("levelId") int levelId, @Query("subId") int subId, @Query("gradeId") String gradeId);

    @GET("course-select/api/course/getDKSY")
    Observable<BaseResponse<List<CourseListItemBean>>> getAllCourseList(@Query("subId") int subId, @Query("levelId") int levelId);

    @GET("course-select/api/video/selectVideoList")
    Observable<BaseResponse<SelectVideoBean>> getSelectVideoList(@Query("courseId") int courseId, @Query("page") int page, @Query("rows") int rows);

    @GET("backend-web/bind/profileIOS")
    Observable<BaseResponse<UserInfoBean>> getUserInfo();

    @GET("course-select/api/ownVideo/getDetailVideo")
    Observable<BaseResponse<VideoDetailBean>> getVideoDetail(@Query("id") Integer id, @Query("chapterId") Integer chapterId, @Query("sectionId") Integer sectionId, @Query("gradeId") String gradeId, @Query("levelId") int levelId);

    @FormUrlEncoded
    @POST("course-select/api/userCourse/insertUserCourse")
    Observable<BaseResponse<LoginBean>> addCourseClickCount(@Field("courseId") int courseId);

    @FormUrlEncoded
    @POST("course-select/api/userVideo/insertUserVideo")
    Observable<BaseResponse<LoginBean>> addVideoClickCount(@Field("courseId") int courseId, @Field("videoId") int videoId);

    @GET("course-select/api/video/selectVideoList")
    Observable<BaseResponse<CourseOutlineBean>> getVideoList(@Query("courseId") int courseId, @Query("page") int page, @Query("rows") int rows);

    @GET("course-select/api/download/uploadFilePlayAuth")
    Observable<BaseResponse<PlayAuthBean>> getPlayAuthByVid(@Query("videoId") String vid);

    /**
     * 获取学习阶段和年级的接口
     */
    @GET("course-select/api/ownLevel/getLevelList")
    Observable<BaseResponse<List<LevelListBean>>> getLevelList(@Query("levelId") int levelId, @Query("isMember") boolean isMember);

    /**
     * 获取学习阶段和年级的接口
     * 没有课程ID
     */
    @GET("course-select/api/ownLevel/getLevelList")
    Observable<BaseResponse<List<LevelListBean>>> getLevelListNoMember(@Query("isMember") boolean isMember);

    /**
     * 获取所有的单科首页的信息
     */
    @GET("course-select/api/ownVideo/getSubjectHome")
    Observable<BaseResponse<SubjectHomeBean>> getSubjectHome(@Query("levelId") int levelId, @Query("subId") int subId, @Query("gradeId") String gradeId);

    /**
     * 获取章的视频内容
     */
    @GET("course-select/api/ownVideo/getVideoListByChapterId")
    Observable<BaseResponse<List<SubjectChapterBean>>> getSubjectChapterVideoList(@Query("chapterId") int chapterId);

    /**
     * 获取节的视频内容
     */
    @GET("course-select/api/ownVideo/getVideoListBySectionId")
    Observable<BaseResponse<List<SubjectSectionBean>>> getSubjectSectionVideoList(@Query("sectionId") int sectionId);

}
