package com.wxjz.module_base.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxjz.module_base.R;
import com.wxjz.module_base.base.BaseDialog;
import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.LevelListBean;
import com.wxjz.module_base.db.bean.GuestChooseGrade;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.event.GuestChooseGradeEvent;
import com.wxjz.module_base.http.api.MainPageApi;
import com.wxjz.module_base.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * @ClassName TouristChooseDialog
 * @Description 游客身份和会员身份统一登陆选择的对话框
 * @Author liufang
 * @Date 2019-10-29 10:40
 * @Version 1.0
 */
public class TouristChooseDialog extends BaseDialog {

    private Context mContext;
    /**
     * 学习阶段recyclerview的加载
     */
    private RecyclerView ryleaningPash;
    /**
     * 年级recyclerview的加载
     */
    private RecyclerView rygradeclassification;

    /**
     * 关闭对话框
     */
    private ImageView ivClose;

    /**
     * 当前学习阶段对应的主键id
     */
    private int levelId = -1;
    /**
     * 当前年级对应后台的主键ID
     */
    private String gradeId;
    /**
     * 选择的学习阶段在列表中的位置
     */
    private int levelIndex;
    /**
     * 选择的年级在列表中的位置
     */
    private int gradeIndex;

    /**
     * 学习阶段加载的Adapter
     */
    private LearningPhaseAdapter mLearningPhaseAdapter;
    /**
     * 年级加载的Adapter
     */
    private GradeClassificationAdapter mGradeClassificationAdapter;
    /**
     * 用户信息
     */
    private UserInfoBean userInfo;

    //学习阶段的bean
    private List<LevelListBean> levelListBeans;
    //年级的bean
    private List<LevelListBean.GradeBean> gradeBeans;
    //记录之前存储的选择
    private GuestChooseGrade guestChooseGrade;

    public TouristChooseDialog(Context context) {
        super(context);
        init(context, 0);

    }

    public TouristChooseDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context, themeResId);
    }

    private void init(Context context, int themeResId) {
        mContext = context;
        canceledOnTouchOutside(false);
        setContentView(R.layout.dialog_tourist_choose_dialog);
        dimAmount(0.5f);
        gravity(Gravity.CENTER);
        initview();
        initdata();
    }


    private void initview() {
        ivClose = findViewById(R.id.iv_dialog_close);
        ryleaningPash = findViewById(R.id.ry_level_list);
        rygradeclassification = findViewById(R.id.ry_grade_list);
    }

    private void initdata() {
        levelListBeans = new ArrayList<>();
        gradeBeans = new ArrayList<>();
        levelId = CheckGradeDao.getInstance().queryleveId();
        userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        if (userInfo != null) {
            //说明是登陆状态，需要判断是否是会员
            int ismember = userInfo.getIsMember();
            if (ismember == 1) {
                //如果是会员，就调会员接口
                sendUser(levelId, true);
            } else {
                //如果不是，就调不是会员的接口
                sendUser(levelId, false);
            }
        } else {
            //如果没有登陆，就需要调查所有的接口
            sendGuestion();
        }


        /**
         *  关闭对话框的按钮
         */
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    /**
     * 登陆状态下，不是会员，获取学习阶段和年级的接口
     */

    private void sendUser(int levelId, boolean isMember) {
        makeRequest(create(MainPageApi.class).getLevelList(levelId, isMember), new BaseObserver<List<LevelListBean>>() {
            @Override
            protected void onSuccess(List<LevelListBean> levelListBeans1) {
                levelListBeans = levelListBeans1;
                showDialog(levelListBeans);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    /**
     * 未登陆，游客模式下获取学习阶段，年级的接口
     */
    private void sendGuestion() {
        makeRequest(create(MainPageApi.class).getLevelListByGuest(), new BaseObserver<List<LevelListBean>>() {
            @Override
            protected void onSuccess(List<LevelListBean> levelListBeans1) {
                levelListBeans = levelListBeans1;
                showDialog(levelListBeans);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    private void showDialog(List<LevelListBean> levelListBeans) {
        guestChooseGrade = CheckGradeDao.getInstance().getGuestChooseGrade();
        if (guestChooseGrade == null || TextUtils.isEmpty(guestChooseGrade.getGradeName())) {
            ivClose.setVisibility(View.GONE);
        } else {
            ivClose.setVisibility(View.VISIBLE);
        }
        if (levelListBeans != null && levelListBeans.size() > 0) {
            //做判空处理
            mLearningPhaseAdapter = new LearningPhaseAdapter(R.layout.ry_item_learning_pash, levelListBeans);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            ryleaningPash.setLayoutManager(layoutManager);
            ryleaningPash.setAdapter(mLearningPhaseAdapter);
            //之前是否有选择的数据。在数据中的位置
            levelIndex = getLevelListIndex(levelListBeans);
            selectLeftRecyclerview(mLearningPhaseAdapter, rygradeclassification, levelIndex);
            mLearningPhaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    levelIndex = position;
                    selectLeftRecyclerview(mLearningPhaseAdapter, rygradeclassification, levelIndex);
                }
            });

        }
    }

    /**
     * 选择学习阶段的时候对应改变年级，同时刷新界面UI
     *
     * @param mLearningPhaseAdapter
     * @param rygradeclassification
     * @param levelIndex
     */
    public void selectLeftRecyclerview(LearningPhaseAdapter mLearningPhaseAdapter, RecyclerView rygradeclassification, final int levelIndex) {
        gradeBeans = levelListBeans.get(levelIndex).getGradeList();
        levelId = levelListBeans.get(levelIndex).getId();
        gradeIndex = getGradeListIndex(gradeBeans);
        if (gradeIndex != -1) {
            gradeId = gradeBeans.get(gradeIndex).getId();
        }
        if (mLearningPhaseAdapter != null) {
            mLearningPhaseAdapter.setMcurrentposition(levelIndex);
        }
        mGradeClassificationAdapter = new GradeClassificationAdapter(R.layout.ry_item_grade, gradeBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rygradeclassification.setLayoutManager(layoutManager);
        rygradeclassification.setAdapter(mGradeClassificationAdapter);
        selectRightRecyclerview(mGradeClassificationAdapter, gradeIndex);
        mGradeClassificationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                gradeIndex = position;
                gradeId = gradeBeans.get(gradeIndex).getId();
                selectRightRecyclerview(mGradeClassificationAdapter, gradeIndex);
                savechoose(levelId, levelListBeans.get(levelIndex).getLevelName(), gradeId, gradeBeans.get(gradeIndex).getGradeName());
                EventBus.getDefault().post(new GuestChooseGradeEvent(levelId, levelListBeans.get(levelIndex).getLevelName(), gradeId, gradeBeans.get(gradeIndex).getGradeName()));
                dismiss();
            }
        });
    }

    /**
     * 保存选择的学段和年级
     *
     * @param levelId
     * @param levelName
     * @param gradeId
     * @param gradeName
     */
    private void savechoose(int levelId, String levelName, String gradeId, String gradeName) {
        if (levelId == -1) {
            levelId = 1;
        }

        CheckGradeDao.getInstance().addGuestChooseGrade(levelId, levelName, gradeId, gradeName, true);
    }

    /**
     * 选择年级时实现单选效果
     *
     * @param mGradeClassificationAdapter
     * @param gradeIndex
     */
    public void selectRightRecyclerview(GradeClassificationAdapter mGradeClassificationAdapter, int gradeIndex) {
        if (mGradeClassificationAdapter != null) {
            mGradeClassificationAdapter.setMcurrentposition(gradeIndex);
        }
    }

    /**
     * 遍历获取历史学习阶段在数据中的位置
     *
     * @param levelListBeans
     * @return
     */
    public int getLevelListIndex(List<LevelListBean> levelListBeans) {
        int i = 0;
        if (guestChooseGrade != null) {
            //如果不为空，说明之前是有选择数据的，我们一一赋值
            levelId = guestChooseGrade.getLevelid();
            for (int j = 0; j < levelListBeans.size(); j++) {
                if (levelListBeans.get(j).getId() == levelId) {
                    i = j;
                    return i;
                }
            }

        }
        return i;
    }

    /**
     * 遍历获取年级在数据中的位置

     * @param gradeBeans
     * @return
     */

    public int getGradeListIndex(List<LevelListBean.GradeBean> gradeBeans) {
        int i = -1;
        if (guestChooseGrade != null) {
            gradeId = guestChooseGrade.getGradeId();
            for (int j = 0; j < gradeBeans.size(); j++) {
                if (gradeBeans.get(j).getId().equals(gradeId)) {
                    i = j;
                    return i;
                }
            }
        }

        return i;
    }
}
