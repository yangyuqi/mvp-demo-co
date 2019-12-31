package com.wxjz.module_base.http.api;

import com.wxjz.module_base.base.BaseResponse;
import com.wxjz.module_base.bean.AddNoteItemBean;
import com.wxjz.module_base.bean.AnswerQuestionBean;
import com.wxjz.module_base.bean.CourseOutlineBean;
import com.wxjz.module_base.bean.DeleteNoteItemBean;
import com.wxjz.module_base.bean.LevelListBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.ExerciseListBean;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.bean.TakeNotesListBean;
import com.wxjz.module_base.bean.TerminologyListBean;
import com.wxjz.module_base.bean.TipsListBean;
import com.wxjz.module_base.bean.UpdateNoteItemBean;
import com.wxjz.module_base.bean.UserInfoBean;
import com.wxjz.module_base.bean.VideoInPlayPageBean;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @ClassName AliyunVideoApi
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-25 19:05
 * @Version 1.0
 */
public interface AliyunVideoApi {
    /**
     * 获取所有笔记
     *
     * @param userId
     * @param domType
     * @param videoId
     * @return
     */
    @GET("course-select/api/dom/selectDomNote")
    Observable<BaseResponse<PointListBean>> getSelectDomNote(@Query("userId") String userId, @Query("domType") int domType, @Query("videoId") int videoId);

    /**
     * 删除一个笔记
     */
    @GET("course-select/api/dom/deleteDomInfoPad")
    Observable<BaseResponse<DeleteNoteItemBean>> deleteDomInfoPad(@Query("id") String id, @Query("domType") int domType);

    /**
     * 添加一个笔记
     */
    @FormUrlEncoded
    @POST("course-select/api/dom/insertDomNote")
    Observable<BaseResponse<AddNoteItemBean>> insertDomNote(@Field("userId") String userId, @Field("videoId") int videoId, @Field("domType") int domType, @Field("videoDom") int videoDom, @Field("domContent") String domContent);

    /**
     * 更新一个笔记
     */
    @FormUrlEncoded
    @POST("course-select/api/dom/updateDomNote")
    Observable<BaseResponse<UpdateNoteItemBean>> updateDomNote(@Field("id") int id, @Field("domContent") String domContent);

    /**
     * 更新学习记录
     */
    @FormUrlEncoded
    @POST("course-select/api/ownUSerVideo/ediUserVideo")
    Observable<BaseResponse<UpdateNoteItemBean>> updateLearnTime(@Field("videoId") int videoId, @Field("progress") int progress, @Field("todayTimeRealRecord") int todayTimeRealRecord);

    /**
     * 新增学习记录
     */
    @FormUrlEncoded
    @POST("course-select/api/ownUSerVideo/insertUserVideo")
    Observable<BaseResponse<UpdateNoteItemBean>> insertLearnTime(@Field("videoId") int videoId, @Field("subId") int subId);

    /**
     * 查找所有提示接口
     */
    @GET("course-select/api/dom/selectDomInfoPc")
    Observable<BaseResponse<PointListBean>> getTipsList(@Query("videoId") int videoId, @Query("domType") int domType, @Query("userId") String userId);

    /**
     * 查找所有术语接口
     */
    @GET("course-select/api/dom/selectDomInfoPc")
    Observable<BaseResponse<PointListBean>> getTerminologysList(@Query("videoId") int videoId, @Query("domType") int domType, @Query("userId") String userId);

    /**
     * 获取播放凭证
     *
     * @param vid
     * @return
     */
    @GET("course-select/api/download/uploadFilePlayAuth")
    Observable<BaseResponse<PlayAuthBean>> getPlayAuthByVid(@Query("videoId") String vid);

    /**
     * 获取所有课堂练习
     *
     * @param videoId
     * @param domType
     * @param userId
     * @return
     */

    @GET("course-select/api/dom/selectDomInfoPc")
    Observable<BaseResponse<PointListBean>> getExerciseList(@Query("videoId") int videoId, @Query("domType") int domType, @Query("userId") String userId);

    /**
     * 提交了回答问题的选项
     */
    @FormUrlEncoded
    @POST("course-select/api/dom/insertUserAnswer")
    Observable<BaseResponse<AnswerQuestionBean>> getInsertUserAnswer(@Field("userId") String userId, @Field("domId") int domId, @Field("userAnswer") String userAnswer);

    /**
     * 查找所有点位接口
     */
    @GET("course-select/api/dom/selectDomInfoPc")
    Observable<BaseResponse<PointListBean>> getAllPointList(@Query("userId") String userId, @Query("domType") int domType, @Query("videoId") int videoId);

    @GET("course-select/api/ownVideo/getAboutVideoList")
    Observable<BaseResponse<VideoInPlayPageBean>> getVideoList(@Query("chapterId") Integer chapterId, @Query("sectionId") Integer sectionId, @Query("gradeId") String gradeId, @Query("page") int page, @Query("rows") int rows);

    /**
     * 查找用户信息
     *
     * @return
     */
    @GET("backend-web/bind/profileIOS")
    Observable<BaseResponse<UserInfoBean>> getUserInfo();

    /**
     * 查询年级的接口
     */
    @GET("course-select/api/ownLevel/getLevelList")
    Observable<BaseResponse<List<LevelListBean>>> getLevelListNoMember(@Query("isMember") boolean isMember);

}
