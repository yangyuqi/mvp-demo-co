package com.wxjz.module_aliyun.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.icu.util.IslamicCalendar;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wxjz.module_aliyun.NineGridView.ImageInfo;
import com.wxjz.module_aliyun.NineGridView.NineGridView;
import com.wxjz.module_aliyun.NineGridView.NineGridViewClickAdapter;
import com.wxjz.module_aliyun.NineGridView.PhotoActivity;
import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;
import com.wxjz.module_aliyun.aliyun.utils.TimeFormater;
import com.wxjz.module_aliyun.event.DialogSeekToEvent;
import com.wxjz.module_base.bean.ExerciseListBean;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.bean.TerminologyListBean;
import com.wxjz.module_base.db.bean.ExerciseDBBean;
import com.wxjz.module_base.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/8/5
 * Time: 9:10
 */
public class ExerciseLoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PointListBean.PointBean> dataList;

    private Context context;
    /**
     * 普通布局
     */
    private final int TYPE_ITEM = 1;

    /**
     * 脚布局
     */
    private final int TYPE_FOOTER = 2;

    /**
     * 当前加载状态，默认为加载完成
     */
    private int loadState = 2;
    /**
     * 加载状态，正在加载中
     */
    public final int LOADING = 1;
    /**
     * 加载状态，加载完成
     */
    public final int LOADING_COMPLETE = 2;
    /**
     * 加载状态，加载结束
     */
    public final int LOADING_END = 3;
    /**
     * 返回不同的view的Type值
     */
    private View view;

    public AdapterOnItemViewClickListener itemViewClickListener;

    private OnAnswerQuestionCheck onAnswerQuestionCheck;

    private OnTextToSpeech onTextToSpeech;

    public ExerciseLoadMoreAdapter(List<PointListBean.PointBean> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    /**
     * 清除数据
     */
    public void clearData() {
        if (dataList != null) {
            dataList.clear();
        }
    }

    /**
     * 添加数据,整个list
     *
     * @param dataLists
     */
    public void addDataList(List<PointListBean.PointBean> dataLists) {
        if (dataList != null) {
            this.dataList.addAll(dataLists);
        } else {
            this.dataList = dataLists;
        }
        notifyDataSetChanged();
    }

    /**
     * 重新设置数据
     */
    public void setDataList(List<PointListBean.PointBean> dataLists) {
        if (dataLists != null) {
            this.dataList = dataLists;
            notifyDataSetChanged();
        }

    }


    /**
     * 添加单条数据
     *
     * @param data
     */
    public void addDataSingleData(PointListBean.PointBean data) {
        if (dataList != null) {
            this.dataList.add(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 根据位置移除数据
     *
     * @param index
     */
    public void removeSingleDate(int index) {
        if (dataList != null) {
            this.dataList.remove(index);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        if (i == TYPE_ITEM) {
        //返回正常的ItemType
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_recycler_exercise_item, viewGroup, false);
        return new RecyclerViewHolder(view, i);
//        } else if (i == TYPE_FOOTER) {
//            //返回上拉加载的Item
//            view = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.layout_refresh_footer, viewGroup, false);
//            return new RecyclerViewHolder(view, i);
//        }
//        return null;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
//        if (getItemViewType(i) == TYPE_ITEM) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
        final Map<Integer, String> options = new HashMap<>();
        //问题的图片的url
        String contentUrl = null;
        //选项的图片url
        HashMap<String, String> imageOptionHashMap = new HashMap<>();
        //答题状态
        final PointListBean.PointBean exerciseBean = dataList.get(i);
        if (exerciseBean != null) {
            //题干
            if (!TextUtils.isEmpty(exerciseBean.getDomContent())) {
                recyclerViewHolder.ivSpeekIcon.setVisibility(View.VISIBLE);
                recyclerViewHolder.tvRecyclerViewContent.setText(exerciseBean.getDomContent());
                recyclerViewHolder.ivSpeekIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onTextToSpeech != null) {
                            onTextToSpeech.OnSpeech(exerciseBean.getDomContent());
                        }
                    }
                });
            } else {
                recyclerViewHolder.ivSpeekIcon.setVisibility(View.GONE);
            }
            //对应的题目是单选还是多选
            if (exerciseBean.getIsSelect() == 1) {
                recyclerViewHolder.tvExerciseType.setText("单选题");
            } else {
                recyclerViewHolder.tvExerciseType.setText("多选题");
            }

            //对应的视频进度
            recyclerViewHolder.tvTime.setText(TimeFormater.formatMs(exerciseBean.getVideoDom() * 1000));

            //控制内容是展示还是隐藏
            if (exerciseBean.isStatus()) {
                //展开状态
                recyclerViewHolder.ll_content.setVisibility(View.VISIBLE);
                recyclerViewHolder.ivShowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.hide));
                recyclerViewHolder.tvShowContent.setText("收起");
            } else {
                //收起状态
                recyclerViewHolder.ll_content.setVisibility(View.GONE);
                recyclerViewHolder.ivShowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.show));
                recyclerViewHolder.tvShowContent.setText("展开");
            }
            //获取用户的回答信息
            final String userAnswer = exerciseBean.getUserAnswer();
            if (TextUtils.isEmpty(userAnswer)) {
                if (exerciseBean.isStatus()) {
                    //展开的状态
                    recyclerViewHolder.tv_btn_check.setVisibility(View.VISIBLE);
                } else {
                    //收起状态
                    recyclerViewHolder.tv_btn_check.setVisibility(View.GONE);
                }
                recyclerViewHolder.llAnswerType.setVisibility(View.GONE);
            } else {
                //说明已经答题了
                recyclerViewHolder.llAnswerType.setVisibility(View.VISIBLE);
                if (exerciseBean.getIsRight() == 1) {
                    recyclerViewHolder.tv_answer.setText(context.getResources().getText(R.string.answer_correct));
                    recyclerViewHolder.tv_answer.setTextColor(context.getResources().getColor(R.color.green57B87F));
                    recyclerViewHolder.tv_answer_tips.setText(context.getResources().getText(R.string.answer_correct_tips));
                    recyclerViewHolder.tv_answer_tips.setTextColor(context.getResources().getColor(R.color.green57B87F));
                    recyclerViewHolder.tv_btn_check.setVisibility(View.GONE);
                } else {
                    //回答错误
                    recyclerViewHolder.tv_answer.setText(context.getResources().getText(R.string.answer_error));
                    recyclerViewHolder.tv_answer.setTextColor(context.getResources().getColor(R.color.originEF7459));
                    recyclerViewHolder.tv_answer_tips.setTextColor(context.getResources().getColor(R.color.originEF7459));
                    recyclerViewHolder.tv_answer_tips.setText(context.getResources().getText(R.string.answer_error_tips));
                    if (exerciseBean.isStatus()) {
                        recyclerViewHolder.tv_btn_check.setVisibility(View.VISIBLE);
                    } else {
                        recyclerViewHolder.tv_btn_check.setVisibility(View.GONE);
                    }
                }
            }

            //题目的图片
            if (exerciseBean.gettOwnDomOptionPictures() != null && exerciseBean.gettOwnDomOptionPictures().size() > 0) {
                List<PointListBean.QuestionIcon> questionIcons = exerciseBean.gettOwnDomOptionPictures();
                //将所有图片集合分类，问题图片只有一张，其他是选项图片
                for (int j = 0; j < questionIcons.size(); j++) {
                    if (!TextUtils.isEmpty(questionIcons.get(j).getLocation())) {
                        if (questionIcons.get(j).getLocation().equals("project")) {
                            contentUrl = questionIcons.get(j).getUrl();
                        } else if (questionIcons.get(j).getLocation().equals("option")) {
                            if (!TextUtils.isEmpty(questionIcons.get(j).getOptionTag())) {
                                imageOptionHashMap.put(questionIcons.get(j).getOptionTag(), questionIcons.get(j).getUrl());
                            }
                        }
                    }
                }
                //如果题目图片内容不为空，就加载题目图片
                if (!TextUtils.isEmpty(contentUrl)) {
                    recyclerViewHolder.ivImageOne.setVisibility(View.VISIBLE);
                    Glide.with(context).asBitmap().format(DecodeFormat.PREFER_RGB_565).load(contentUrl).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            int actualHight = resource.getHeight();
                            int actualWidth = resource.getWidth();

                            float viewHeight = 320;
                            float viewWidth = 560;
                            if (actualHight > 0 && actualWidth > 0) {
                                // 要保证图片的长宽比不变
                                float ratio = (actualHight * 1.0f) / actualWidth;
                                viewHeight = (actualHight > viewHeight ? viewHeight : actualHight);
                                viewWidth = viewHeight / ratio;

                                float scaleWidth = (viewWidth * 1.0f) / actualWidth;
                                float scaleHeight = (viewHeight * 1.0f) / actualHight;

                                Log.i("xixi", "scaleWidth : " + scaleWidth + ", scaleHeight : " + scaleHeight + ", ratio : " + ratio);

                                Matrix matrix = new Matrix();
                                matrix.postScale(scaleWidth, scaleHeight);

                                Log.i("xixi", "actualHight : " + actualHight + ", actualWidth : " + actualWidth);

                                resource = Bitmap.createBitmap(resource, 0, 0, actualWidth, actualHight, matrix, true);
                                Log.i("xixi", "width : " + viewWidth + ", height : " + viewHeight);
                                recyclerViewHolder.ivImageOne.setImageBitmap(resource);
                            }
                        }
                    });
                    final List<String> urlList = new ArrayList<>();
                    urlList.add(contentUrl);
                    recyclerViewHolder.ivImageOne.setOnClickListener(new OnLimitDoubleListener() {
                        @Override
                        public void onLimitDouble(View v) {
                            PhotoActivity.openPhotoAlbum(context, urlList, null, 0);
                        }
                    });
                } else {
                    recyclerViewHolder.ivImageOne.setVisibility(View.GONE);
                }
            }

            if (exerciseBean.gettOwnDomOptions() != null) {
                final List<PointListBean.ChooseItem> chooseItems = exerciseBean.gettOwnDomOptions();
                //先默认循环，将列表中所有选项设置为false
                //然后选项里面有图片就塞入图片，没有图片就不塞入
                for (int j = 0; j < chooseItems.size(); j++) {
                    chooseItems.get(j).setSelect(false);
                    if (imageOptionHashMap.get(chooseItems.get(j).getOption()) != null) {
                        chooseItems.get(j).setUrl(imageOptionHashMap.get(chooseItems.get(j).getOption()));
                    }
                }
                //获取用户回答的选项，如果用户有选择的话就将其设置为选择
                String s = exerciseBean.getUserAnswer();
                if (!TextUtils.isEmpty(s)) {
                    String a[] = s.split(",");
                    for (int j = 0; j < a.length; j++) {
                        for (int k = 0; k < chooseItems.size(); k++) {
                            if ((a[j].toUpperCase()).equals(chooseItems.get(k).getOption())) {
                                chooseItems.get(k).setSelect(true);
                                options.put(k, chooseItems.get(k).getOption());
                            }
                        }
                    }
                }

                final CheckBoxOptionAdapter mCheckBoxOptionAdapter = new CheckBoxOptionAdapter(R.layout.layout_option_item, chooseItems);
                recyclerViewHolder.mRyAnswerCheckItem.setNestedScrollingEnabled(false);
                recyclerViewHolder.mRyAnswerCheckItem.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerViewHolder.mRyAnswerCheckItem.setAdapter(mCheckBoxOptionAdapter);
                mCheckBoxOptionAdapter.setOnOptionCheckListener(new CheckBoxOptionAdapter.OnOptionCheckListener() {
                    @Override
                    public void onCheckChange(boolean isChecked, int i) {
                        if (exerciseBean.getIsSelect() == 1) {
                            //这是单选
                            if (isChecked) {
                                for (int j = 0; j < chooseItems.size(); j++) {
                                    if (i == j) {
                                        options.clear();
                                        options.put(i, chooseItems.get(j).getOption());
                                        chooseItems.get(j).setSelect(true);
                                    } else {
                                        chooseItems.get(j).setSelect(false);
                                    }
                                }

                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mCheckBoxOptionAdapter.notifyDataSetChanged();
                                }
                            }, 200);

                        } else {
                            if (isChecked) {
                                chooseItems.get(i).setSelect(true);
                                options.put(i, chooseItems.get(i).getOption());
                            } else {
                                chooseItems.get(i).setSelect(false);
                                if (options.containsKey(i)) {
                                    options.remove(i);
                                }
                            }
                        }

                    }

                    @Override
                    public void onImageClick(String url) {
                        final List<String> urlList = new ArrayList<>();
                        urlList.add(url);
                        PhotoActivity.openPhotoAlbum(context, urlList, null, 0);
                    }

                    @Override
                    public void onSpeekContent(String text) {
                        if (onTextToSpeech != null) {
                            onTextToSpeech.OnSpeech(text);
                        }
                    }
                });
            }
            //是否展示隐藏内容
            recyclerViewHolder.llShowContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (exerciseBean.isStatus()) {
                        //如果是展开的状态，就关闭
                        recyclerViewHolder.ll_content.setVisibility(View.GONE);
                        recyclerViewHolder.ivShowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.show));
                        recyclerViewHolder.tvShowContent.setText("展开");
                        exerciseBean.setStatus(false);
                        recyclerViewHolder.tv_btn_check.setVisibility(View.GONE);
                    } else {
                        //如果是关闭的状态就展开
                        recyclerViewHolder.ll_content.setVisibility(View.VISIBLE);
                        recyclerViewHolder.ivShowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.hide));
                        recyclerViewHolder.tvShowContent.setText("收起");
                        exerciseBean.setStatus(true);
                        if (TextUtils.isEmpty(userAnswer)) {
                            recyclerViewHolder.tv_btn_check.setVisibility(View.VISIBLE);
                        } else {
                            //说明已经答题了
                            if (exerciseBean.getIsRight() == 1) {
                                recyclerViewHolder.tv_btn_check.setVisibility(View.GONE);
                            } else {
                                //回答错误
                                recyclerViewHolder.tv_btn_check.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            });

            recyclerViewHolder.tv_btn_check.setOnClickListener(new OnLimitDoubleListener() {
                @Override
                public void onLimitDouble(View v) {
                    StringBuffer answer = new StringBuffer();
                    String s1 = "";

                    if (options.size() == 0) {
                        ToastUtil.showTextToas(context, "请注意，至少选择一个选项");
                    } else if (exerciseBean.getIsSelect() == 1 && options.size() > 1) {
                        ToastUtil.showTextToas(context, "请注意，这是单选题");
                    } else {
                        ArrayList<Integer> keys = new ArrayList<>();
                        for (Map.Entry<Integer, String> entry : options.entrySet()) {
                            keys.add(entry.getKey());
                        }
                        Collections.sort(keys);
                        for (int i = 0; i < keys.size(); i++) {
                            answer = answer.append(options.get(keys.get(i)) + ",");
                        }
                        s1 = answer.substring(0, answer.length() - 1);
                        if (onAnswerQuestionCheck != null) {
                            onAnswerQuestionCheck.OnAnsweCheck(s1, i, exerciseBean.getId(), exerciseBean.getRightAnswer());
                        }
                    }
                }
            });

        }

//        } else if (getItemViewType(i) == TYPE_FOOTER) {
//            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
//            switch (loadState) {
//                case LOADING://加载中
//                    recyclerViewHolder.pbLoading.setVisibility(View.VISIBLE);
//                    recyclerViewHolder.tvLoading.setVisibility(View.VISIBLE);
//                    recyclerViewHolder.llEnd.setVisibility(View.GONE);
//                    break;
//                case LOADING_COMPLETE://加载完成
//                    recyclerViewHolder.pbLoading.setVisibility(View.INVISIBLE);
//                    recyclerViewHolder.tvLoading.setVisibility(View.INVISIBLE);
//                    recyclerViewHolder.llEnd.setVisibility(View.GONE);
//                    break;
//                case LOADING_END://加载到底
//                    recyclerViewHolder.pbLoading.setVisibility(View.GONE);
//                    recyclerViewHolder.tvLoading.setVisibility(View.GONE);
//                    recyclerViewHolder.llEnd.setVisibility(View.VISIBLE);
//                    break;
//                default:
//                    break;
//            }
//        }
    }

    @Override
    public int getItemCount() {
//        return dataList.size() + 1;
        return dataList.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position + 1 == getItemCount()) {
//            return TYPE_FOOTER;
//        } else {
//            return TYPE_ITEM;
//        }
//    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        /**
         * 题目内容
         */
        private TextView tvRecyclerViewContent;
        /**
         * 题目类型
         */
        private TextView tvExerciseType;
        /**
         * 在视频的进度位置
         */
        private TextView tvTime;
        /**
         * 回答是否正确
         */
        private TextView tv_answer;
        /**
         * 回答是否正确的提示
         */
        private TextView tv_answer_tips;
        /**
         * 提交按钮
         */
        private TextView tv_btn_check;
        /**
         * 题目内容布局
         */
        private LinearLayout ll_content;
        /**
         * 加载选项的布局
         */
        private RecyclerView mRyAnswerCheckItem;
        /**
         * 加载图片的布局，支持九宫格
         */
        private NineGridView mNineGridView;
        /**
         * 是否展示UI
         */
        private ImageView ivShowImage;
        /**
         * 是否展示UI
         */
        private LinearLayout llShowContent;
        /**
         * 根据文字判断当前状态
         */
        private TextView tvShowContent;

        private LinearLayout llCheck;

        private ImageView ivImageOne;
        /**
         * 读取题干的内容
         */
        private ImageView ivSpeekIcon;

        private LinearLayout llAnswerType;

        private TextView tvAnalysis;


        private ProgressBar pbLoading;
        private TextView tvLoading;
        private LinearLayout llEnd;


        public RecyclerViewHolder(@NonNull View itemView, int i) {
            super(itemView);
//            if (i == TYPE_ITEM) {
            tvRecyclerViewContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_exercise_time);
            tv_answer = itemView.findViewById(R.id.tv_answer);
            tv_answer_tips = itemView.findViewById(R.id.tv_answer_tips);
            tv_btn_check = itemView.findViewById(R.id.tv_btn_check);
            ll_content = itemView.findViewById(R.id.ll_content);
            mNineGridView = itemView.findViewById(R.id.answer_question_nine_grid_view);
            mRyAnswerCheckItem = itemView.findViewById(R.id.recycler_check_item);
            ivShowImage = itemView.findViewById(R.id.iv_show_image);
            llShowContent = itemView.findViewById(R.id.ll_show_content);
            tvShowContent = itemView.findViewById(R.id.tv_show_content);
            tvExerciseType = itemView.findViewById(R.id.tv_exercise_type);
            ivImageOne = itemView.findViewById(R.id.iv_one_picture);
            ivSpeekIcon = itemView.findViewById(R.id.iv_to_speek);
            llAnswerType = itemView.findViewById(R.id.ll_answer_type);
            tvAnalysis = itemView.findViewById(R.id.tv_analysis);
//            } else if (i == TYPE_FOOTER) {
//                pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
//                tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
//                llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
//            }

        }
    }

    /**
     * 设置上拉加载状态
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public interface AdapterOnItemViewClickListener {
        void OnItemClick(View view, int position);
    }

    public void setItemViewClickListener(AdapterOnItemViewClickListener itemViewClickListener) {
        this.itemViewClickListener = itemViewClickListener;
    }

    public interface OnAnswerQuestionCheck {
        /**
         * @param answer   选择的内容
         * @param position recyclerview position 位置
         * @param id       问题的主键id,从接口中获取
         */
        void OnAnsweCheck(String answer, int position, int id, String rightAnswer);

    }

    public void updateItemUi(int exercisePosition, String exerciseAnswer) {
        if (dataList != null) {
            dataList.get(exercisePosition).setUserAnswer(exerciseAnswer);
            notifyItemChanged(exercisePosition, 1);
        }
    }

    public void setOnAnswerQuestionCheck(OnAnswerQuestionCheck onAnswerQuestionCheck) {
        this.onAnswerQuestionCheck = onAnswerQuestionCheck;
    }

    public interface OnTextToSpeech {
        void OnSpeech(String domContent);
    }

    public void setOnTextToSpeech(OnTextToSpeech onTextToSpeech) {
        this.onTextToSpeech = onTextToSpeech;
    }
}
