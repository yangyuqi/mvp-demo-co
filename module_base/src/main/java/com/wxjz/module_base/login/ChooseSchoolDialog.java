package com.wxjz.module_base.login;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wxjz.module_base.R;
import com.wxjz.module_base.base.BaseDialog;
import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.SchoollistBean;
import com.wxjz.module_base.db.bean.CheckedSchool;
import com.wxjz.module_base.db.bean.SchoolItemBean;
import com.wxjz.module_base.db.dao.CheckIdSchoolDao;
import com.wxjz.module_base.db.dao.OwnStudyDBDao;
import com.wxjz.module_base.event.SchoolItemPosition;
import com.wxjz.module_base.http.api.MainPageApi;
import com.wxjz.module_base.listener.OnLimitDoubleListener;
import com.wxjz.module_base.login.recyclerview.PinyinComparator;
import com.wxjz.module_base.login.recyclerview.PinyinUtils;
import com.wxjz.module_base.login.recyclerview.SortAdapter;
import com.wxjz.module_base.login.recyclerview.TitleItemDecoration;
import com.wxjz.module_base.util.DialogUtil;
import com.wxjz.module_base.view.WaveSideBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName ChooseSchoolDialog
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-11 17:15
 * @Version 1.0
 */
public class ChooseSchoolDialog extends BaseDialog {

    private Context mContext;

    private RecyclerView schoolRecyclerView;

    private WaveSideBar waveSideBar;

    private EditText edChooseSchool;

    private TextView tvNotFindSchool;

    private LinearLayoutManager manager;

    private List<SchoolItemBean> mDateList;

    private TitleItemDecoration mDecoration;

    private SortAdapter mAdapter;
    private CheckedSchool checkedSchool;
    /**
     * 根据拼音来排列RecyclerView里面的数据类
     */
    private PinyinComparator mComparator;

    private ChooseSchoolItemView chooseSchoolItem;

    public ChooseSchoolDialog(Context context) {
        super(context);
        init(context);

    }

    public ChooseSchoolDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        contentView(R.layout.dialog_choose_school);
        dimAmount(0.5f);
        canceledOnTouchOutside(true);
        gravity(Gravity.CENTER);

        schoolRecyclerView = findViewById(R.id.recyclerview_school_list);
        waveSideBar = findViewById(R.id.sideBar);
        edChooseSchool = findViewById(R.id.ed_choose_school);
        tvNotFindSchool = findViewById(R.id.tv_not_find_school);
        initData();
    }

    private void initData() {
        checkedSchool = CheckIdSchoolDao.getInstance().getCheckedSchool();
        makeRequest(create(MainPageApi.class).getSchoolList("1", "999"), new BaseObserver<SchoollistBean>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected void onSuccess(SchoollistBean schoollistBean) {
                List<SchoollistBean.SchoolBean> list = schoollistBean.getDatas();

                mDateList = new ArrayList<>(list.size());
                List<SchoolItemBean> schoolItemBeans = OwnStudyDBDao.getInstance().findAllSchool();
                if (schoolItemBeans != null) {
                    schoolItemBeans.clear();
                }
                for (int i = 0; i < list.size(); i++) {
                    SchoolItemBean schoolItemBean = new SchoolItemBean();
                    schoolItemBean.setSchoolId(list.get(i).getId());
                    schoolItemBean.setName(list.get(i).getXxmc());
                    schoolItemBean.setIconUrl(list.get(i).getLogoimage());
                    schoolItemBean.setLxdh(list.get(i).getLxdh());
                    //汉字转换成拼音
                    String pinyin = PinyinUtils.getPingYin(list.get(i).getXxmc());
                    String sortString = pinyin.substring(0, 1).toUpperCase();
                    // 正则表达式，判断首字母是否是英文字母
                    if (sortString.matches("[A-Z]")) {
                        schoolItemBean.setLetters(sortString.toUpperCase());
                    } else {
                        schoolItemBean.setLetters("#");
                    }
                    schoolItemBean.save();
                    mDateList.add(i, schoolItemBean);
                }
                handleData();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void handleData() {
        mComparator = new PinyinComparator();

        waveSideBar = (WaveSideBar) findViewById(R.id.sideBar);

        //设置右侧SideBar触摸监听
        waveSideBar.setOnTouchLetterChangeListener(new WaveSideBar.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        // 根据a-z进行排序源数据
        Collections.sort(mDateList, mComparator);

        //RecyclerView设置manager
        manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        schoolRecyclerView.setLayoutManager(manager);
        mAdapter = new SortAdapter(mContext, mDateList);
        schoolRecyclerView.setAdapter(mAdapter);

        mDecoration = new TitleItemDecoration(mContext, mDateList);
        DividerItemDecoration decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getContext().getDrawable(R.drawable.shape_line_divider));
        //如果add两个，那么按照先后顺序，依次渲染。
        schoolRecyclerView.addItemDecoration(mDecoration);
        schoolRecyclerView.addItemDecoration(decoration);

        mAdapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String schName = mDateList.get(position).getName();
                int schoolId = mDateList.get(position).getSchoolId();
                String lxdh = mDateList.get(position).getLxdh();
                CheckIdSchoolDao.getInstance().addCheckedSchool(schName, schoolId, lxdh);
                if (checkedSchool == null) {
                    if (chooseSchoolItem != null) {
                        chooseSchoolItem.onLoginShow();
                    }
                }
                EventBus.getDefault().post(new SchoolItemPosition(schoolId, schName));
                dismiss();
            }
        });


        edChooseSchool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(editable.toString());
            }
        });

        tvNotFindSchool.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (chooseSchoolItem != null) {
                    chooseSchoolItem.onNotFindSchool();
                }
            }
        });
    }

    /**
     * 为RecyclerView填充数据
     *
     * @param date
     * @return
     */
    private List<SchoolItemBean> filledData(String[] date) {
        List<SchoolItemBean> mSortList = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            SchoolItemBean sortModel = new SchoolItemBean();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新RecyclerView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SchoolItemBean> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = OwnStudyDBDao.getInstance().findAllSchool();
        } else {
            filterDateList.clear();
            for (SchoolItemBean sortModel : mDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        //不区分大小写
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                ) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, mComparator);
        mDateList.clear();
        mDateList.addAll(filterDateList);
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 可能页面刚刚进来没有选择学校，在选择完学校以后需要吧登陆接口回调出去，打开登录的对话框
     *
     * @param onLoginDialogShow
     */
    public void setChooseSchoolItemView(ChooseSchoolItemView onLoginDialogShow) {
        this.chooseSchoolItem = onLoginDialogShow;
    }

    public interface ChooseSchoolItemView {
        void onLoginShow();

        void onNotFindSchool();
    }

}
