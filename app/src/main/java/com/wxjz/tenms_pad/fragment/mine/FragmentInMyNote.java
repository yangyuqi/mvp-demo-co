package com.wxjz.tenms_pad.fragment.mine;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.bean.NoteBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.event.HaveNoteEvent;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.adapter.MyNoteAdapter;
import com.wxjz.tenms_pad.mvp.contract.FragmentInMyNoteContract;
import com.wxjz.tenms_pad.mvp.presenter.FragmentInMyNotePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by a on 2019/9/17.
 */

@SuppressLint("ValidFragment")
public class FragmentInMyNote extends BaseMvpFragment<FragmentInMyNotePresenter> implements FragmentInMyNoteContract.View {
    private int subId;
    private RecyclerView rvNote;
    private MyNoteAdapter noteAdapter;
    private RelativeLayout rlEmpty;
    private String userId1;
    private MyNoteFragment fragment;
    private int levelId;
    private NoteBean noteBean;
    private int page = 1;

    @SuppressLint("ValidFragment")
    public FragmentInMyNote(int subId, MyNoteFragment fragment) {
        this.subId = subId;
        this.fragment = fragment;
    }

    @Override
    protected FragmentInMyNotePresenter createPresenter() {
        return new FragmentInMyNotePresenter(this);
    }

    @Override
    protected void initView(View view) {
        rvNote = view.findViewById(R.id.rv_note);
        rlEmpty = view.findViewById(R.id.rl_empty);
    }

    @Override
    protected void initData() {
        page = 1;
        UserInfoBean currentUserInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        userId1 = currentUserInfo.getUserId();
        //学段Id
        levelId = CheckGradeDao.getInstance().queryleveId();
        mPresenter.getMyNotes(userId1, subId == 0 ? null : subId, 3, page, Integer.MAX_VALUE, levelId);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fragment_in_my_note;
    }

    public static FragmentInMyNote getInstance(int subId, MyNoteFragment fragment) {
        return new FragmentInMyNote(subId, fragment);
    }

    @Override
    public void onMyNotes(final List<NoteBean> noteBeans) {

//        if (subId == 0 && noteBeans.size() == 0) {
//            EventBus.getDefault().post(new HaveNoteEvent(false));
//        }
        if (noteBeans.size() == 0) {
            rlEmpty.setVisibility(View.VISIBLE);
            rvNote.setVisibility(View.GONE);
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvNote.setLayoutManager(layoutManager);
        noteAdapter = new MyNoteAdapter(R.layout.layout_note_item, noteBeans, this);
        rvNote.setAdapter(noteAdapter);
        noteAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                noteBean = noteBeans.get(position);
                mPresenter.getPlayAuthByVid(noteBean.getVideAlipayVideoId());
            }
        });
//        noteAdapter.setEnableLoadMore(true);
//        noteAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                ++page;
//                mPresenter.getMyNotes(userId1, subId == 0 ? null : subId, 3, page, 10, levelId);
//            }
//        }, rvNote);
    }

    @Override
    public void onMyNotesMore(List<NoteBean> noteBeans) {
        if (noteBeans.size() == 0) {
            noteAdapter.loadMoreEnd();
        } else {
            noteAdapter.addData(noteBeans);
            noteAdapter.notifyDataSetChanged();
            noteAdapter.loadMoreComplete();
        }

    }

    @Override
    public void onDeleteNotes() {
        //删除完刷新数据
        page = 1;
        mPresenter.getMyNotes(userId1, subId == 0 ? null : subId, 3, page, Integer.MAX_VALUE, levelId);
        fragment.setManageButtonVisible(true);

    }

    @Override
    public void onPlayAuth(PlayAuthBean playAuthBean) {
        String gradeId = CheckGradeDao.getInstance().getGuestChooseGrade().getGradeId();
        JumpUtil.jump2VideoActivity(mContext, LandscapeVideoActivity.class, playAuthBean.getPlayAuth(), noteBean.getVideAlipayVideoId(), noteBean.getVideoId(),
                subId, String.valueOf(noteBean.getChapterId()), String.valueOf(noteBean.getSectionId()), gradeId, noteBean.getVideoDuration());


    }

    public void deleteCheckedNotes(String ids) {
        if (ids.length() != 0) {
            String id = ids.substring(0, ids.length() - 1);
            mPresenter.deleteNotes(id, 3);
        }


    }

    /**
     * 显示条目上的checkbox
     *
     * @param show
     */
    public void showCheckBox(boolean show) {
        if (noteAdapter != null) {
            noteAdapter.setCheckBoxVisible(show);
        }

    }

    /**
     * 全选
     *
     * @param chooseAll
     */
    public void chooseAll(boolean chooseAll) {
        noteAdapter.checkAll(chooseAll);

    }

    /**
     * 删除选中的条目
     */
    public void deleteCheckedItem() {
        if (noteAdapter != null) {
            noteAdapter.deleteCheckItem();
        }
    }

    /**
     * 获取是否有radiobutton被选中
     *
     * @return
     */
    public boolean getRadioCheckStatus() {
        return noteAdapter.getCheckStatus();
    }
}
