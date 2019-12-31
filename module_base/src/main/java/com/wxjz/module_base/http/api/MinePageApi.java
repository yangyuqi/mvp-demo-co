package com.wxjz.module_base.http.api;

import com.wxjz.module_base.base.BaseResponse;
import com.wxjz.module_base.base.BaseResponse2;
import com.wxjz.module_base.bean.ChangePassBean;
import com.wxjz.module_base.bean.ClassRankBean;
import com.wxjz.module_base.bean.ErrorProblemBean;
import com.wxjz.module_base.bean.FilterErrorExerciseBean;
import com.wxjz.module_base.bean.GradeRankBean;
import com.wxjz.module_base.bean.LearnRecordBean;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.bean.NoteBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.SelectVideoBean;
import com.wxjz.module_base.bean.TopTabBean;
import com.wxjz.module_base.bean.UpdateInfoBean;
import com.wxjz.module_base.bean.UserInfoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by a on 2019/9/17.
 */

public interface MinePageApi {
    @GET("course-select/api/OwnLearingRecord/getClassRecord")
    Observable<BaseResponse<ClassRankBean>> getClassRank(@Query("flag") String data);

    @GET("course-select/api/OwnLearingRecord/getGradeRecord")
    Observable<BaseResponse<List<GradeRankBean>>> getGradeRank(@Query("flag") String data);

    @GET("course-select/api/ownSubject/getSubjectListByLevelId")
    Observable<BaseResponse<List<TopTabBean>>> getTopTabs(@Query("levelId") int levelId);

    /**
     * @param subId
     * @param domType 2错题 3 笔记
     * @param page
     * @param rows
     * @return
     */
    @GET("course-select/api/dom/selectDomInfoPad")
    Observable<BaseResponse<List<NoteBean>>> getMyNotes(@Query("userId") String userId, @Query("subId") Integer subId,
                                                        @Query("domType") int domType, @Query("page") int page, @Query("rows") int rows, @Query("stuLevelId") int stuLevelId
    );

    @GET("course-select/api/ownUSerVideo/getUserVideoRecord")
    Observable<BaseResponse<LearnRecordBean>> getLearnRecord(@Query("levelId") int levelId);

    /**
     * @param subId
     * @param domType 2错题 3 笔记
     * @param page
     * @param rows
     * @return
     */
    @GET("course-select/api/dom/selectDomInfoPad")
    Observable<BaseResponse<List<ErrorProblemBean>>> getMyErrorProblem(@Query("userId") String userId, @Query("subId") Integer subId,
                                                                       @Query("domType") int domType, @Query("page") int page, @Query("rows") int rows, @Query("stuLevelId") int stuLevelId
    );

    @GET("backend-web/bind/profileIOS")
    Observable<BaseResponse<UserInfoBean>> getUserInfo();

    @FormUrlEncoded
    @POST("cas-admin/cas/user/modifyPassWorld")
    Observable<BaseResponse2<ChangePassBean>> changePassword(@Field("userId") String userId, @Field("ps") String ps, @Field("passworld") String passSure);

    @GET("backend-web/bind/updateUserUrl")
    Observable<BaseResponse<UpdateInfoBean>> updateHead(@Query("headUrl") String headUrl);

    @FormUrlEncoded
    @POST("course-select/api/userCourse/insertUserCourse")
    Observable<BaseResponse<LoginBean>> addCourseClickCount(@Field("courseId") int courseId);

    /**
     * 删除学习记录
     *
     * @param ids
     * @return
     */
    @FormUrlEncoded
    @POST("course-select/api/ownUSerVideo/deleteUserVideo")
    Observable<BaseResponse<LoginBean>> deleteLearnRecord(@Field("ids") String ids);

    /**
     * 删除笔记或者错题
     *
     * @param id
     * @param domType 2错题 3 笔记
     * @return
     */
    @GET("course-select/api/dom/deleteDomInfoPad")
    Observable<BaseResponse<LoginBean>> deleteNoteOrError(@Query("id") String id, @Query("domType") int domType);

    @GET("course-select/api/download/uploadFilePlayAuth")
    Observable<BaseResponse<PlayAuthBean>> getPlayAuthByVid(@Query("videoId") String vid);

    /**
     * 筛选的接口
     */
    @GET("course-select/api/dom/selectDomScreen")
    Observable<BaseResponse<List<FilterErrorExerciseBean>>> getFilterErrorBean(@Query("subId") Integer id, @Query("userId") String userId, @Query("domType") int domType);
}
