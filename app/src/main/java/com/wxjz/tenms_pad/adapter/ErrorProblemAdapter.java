package com.wxjz.tenms_pad.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.bean.ErrorProblemBean;
import com.wxjz.module_base.event.ErrorItemChekEvent;
import com.wxjz.module_base.event.NoteItemChekEvent;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.PictureActivity;
import com.wxjz.tenms_pad.fragment.mine.FragmentInMyError;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

/**
 * Created by a on 2019/9/18.
 */

public class ErrorProblemAdapter extends BaseQuickAdapter<ErrorProblemBean, BaseViewHolder> {

    private boolean boxVisible, checkAll;
    /**
     * 保存checkbox的状态集合
     */
    private List<Boolean> mCheckStatusList;
    private List<ErrorProblemBean> problemList;
    private FragmentInMyError fragmentInMyError;

    public ErrorProblemAdapter(int layoutResId, @Nullable List<ErrorProblemBean> data, FragmentInMyError fragmentInMyError) {
        super(layoutResId, data);
        this.problemList = data;
        this.fragmentInMyError = fragmentInMyError;
        mCheckStatusList = new ArrayList<>(data.size());
        for (int i = 0; i < data.size(); i++) {
            mCheckStatusList.add(i, false);
        }
    }

    @Override
    protected void convert(final BaseViewHolder helper, ErrorProblemBean item) {
        String minute = String.format("%02d", item.getVideoDom() / 60);
        String second = String.format("%02d", item.getVideoDom() % 60);
        helper.setText(R.id.tv_time, minute + ":" + second);
        helper.setText(R.id.tv_title, "题目：" + item.getDomContent());
        RecyclerView rvOption = helper.getView(R.id.rv_option);
        List<ErrorProblemBean.TOwnDomOptionsBean> options = item.getTOwnDomOptions();
        rvOption.setLayoutManager(new LinearLayoutManager(mContext));
        rvOption.setAdapter(new ErrorProblemOptionsAdapter(R.layout.error_problem_option_item, options, item.getTOwnDomOptionPictures()));
        helper.setText(R.id.tv_my_answer, item.getUserAnswer());
        helper.setText(R.id.tv_right_answer, item.getRightAnswer());
        helper.setText(R.id.tv_video_name, item.getVideoTitle());
        final TextView tvZhankai = helper.getView(R.id.tv_zhankai);
        final LinearLayout llZhanKai = helper.getView(R.id.llZhanKai);
        final TextView tv_close = helper.getView(R.id.tv_close);
        ImageView iv_exercise_cover = helper.getView(R.id.iv_exercise_cover);
        String pictureUrl = null;
        List<ErrorProblemBean.TOwnDomOptionPicturesBean> tOwnDomOptionPictures = item.getTOwnDomOptionPictures();
        if (tOwnDomOptionPictures == null || tOwnDomOptionPictures.size() == 0) {
            iv_exercise_cover.setVisibility(View.GONE);
        } else {
            for (final ErrorProblemBean.TOwnDomOptionPicturesBean picture : tOwnDomOptionPictures) {
                if (picture.getLocation().equals("project")) {
                    pictureUrl = picture.getUrl();
                    if (TextUtils.isEmpty(pictureUrl)) {
                        iv_exercise_cover.setVisibility(View.GONE);
                    } else {
                        iv_exercise_cover.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(pictureUrl).error(R.drawable.example2).into(iv_exercise_cover);

                    }
                    break;
                } else {
                    iv_exercise_cover.setVisibility(View.GONE);
                }

            }
        }

        final String finalPictureUrl = pictureUrl;
        iv_exercise_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(finalPictureUrl)) {
                    Intent intent = new Intent(mContext, PictureActivity.class);
                    intent.putExtra("imgUrl", finalPictureUrl);
                    mContext.startActivity(intent);
                }

            }
        });
        tvZhankai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvZhankai.setVisibility(View.GONE);
                tv_close.setVisibility(View.VISIBLE);
                llZhanKai.setVisibility(View.VISIBLE);
            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvZhankai.setVisibility(View.VISIBLE);
                tv_close.setVisibility(View.GONE);
                llZhanKai.setVisibility(View.GONE);
            }
        });
        helper.addOnClickListener(R.id.ll_play);
        CheckBox checkBox = helper.getView(R.id.cb_problem);

        //  checkBox.setChecked(false);
        final int layoutPosition = helper.getLayoutPosition();
        checkBox.setOnCheckedChangeListener(null);
        try {
            checkBox.setChecked(mCheckStatusList.get(layoutPosition));
        } catch (Exception e) {

        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    checkAll = false;
                }
                mCheckStatusList.set(helper.getLayoutPosition(), isChecked);
                EventBus.getDefault().post(new ErrorItemChekEvent(true));
            }
        });
        checkBox.setVisibility(boxVisible ? View.VISIBLE : View.GONE);
//        if (checkAll) {
//            checkBox.setChecked(true);
//            EventBus.getDefault().post(new ErrorItemChekEvent(true));
//        }
    }

    public void addCheckList(int size) {
        for (int i = 0; i < size; i++) {
            mCheckStatusList.add(false);
        }
    }

    public void setCheckBoxVisible(boolean boxVisible) {
        this.boxVisible = boxVisible;

        setBoxUnChecked();

        notifyDataSetChanged();
    }

    public void checkAll(boolean checkAll) {
        this.checkAll = checkAll;
        notifyDataSetChanged();
        for (int i = 0; i < mCheckStatusList.size(); i++) {
            mCheckStatusList.set(i, true);
        }
        EventBus.getDefault().post(new ErrorItemChekEvent(true));
    }

    public void deleteCheckItem() {
        String ids = "";
        for (int i = mCheckStatusList.size() - 1; i >= 0; i--) {
            if (mCheckStatusList.get(i)) {
                ids += problemList.get(i).getId() + "-";
                problemList.remove(i);
                mCheckStatusList.remove(i);

            }
        }
        fragmentInMyError.deleteError(ids);
        notifyDataSetChanged();
        EventBus.getDefault().post(new ErrorItemChekEvent(true));
    }

    public boolean getCheckStatus() {
        for (int i = 0; i < mCheckStatusList.size(); i++) {
            if (mCheckStatusList.get(i)) {
                return true;
            }

        }
        return false;
    }

    /**
     * 将checkbox恢复未选中状态
     */
    private void setBoxUnChecked() {
        for (int i = 0; i < mCheckStatusList.size(); i++) {

            mCheckStatusList.set(i, false);

        }
        // EventBus.getDefault().post(new NoteItemChekEvent(true));
    }

}
