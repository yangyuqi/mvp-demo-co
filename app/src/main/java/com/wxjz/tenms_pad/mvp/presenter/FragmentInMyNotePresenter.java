package com.wxjz.tenms_pad.mvp.presenter;

import android.text.TextUtils;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.bean.NoteBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.http.api.MinePageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.module_base.util.ToastUtil;
import com.wxjz.tenms_pad.fragment.mine.FragmentInMyNote;
import com.wxjz.tenms_pad.mvp.contract.FragmentInMyNoteContract;

import java.util.List;

/**
 * Created by a on 2019/9/17.
 */

public class FragmentInMyNotePresenter extends BasePresenter<FragmentInMyNoteContract.View> implements FragmentInMyNoteContract.Presenter {
    private FragmentInMyNote fragment ;
    private final MinePageApi minePageApi;

    public FragmentInMyNotePresenter(FragmentInMyNote fragmentInMyNote){
        this.fragment = fragmentInMyNote;
        minePageApi = create(MinePageApi.class);
    }

    @Override
    public void getMyNotes(String userId, Integer subId, int domType, final int page, int rows, int stuLevelId) {
        makeRequest(minePageApi.getMyNotes( userId,subId, domType, page, rows,stuLevelId), new BaseObserver<List<NoteBean>>() {
            @Override
            protected void onSuccess(List<NoteBean> noteBeans) {
                if (page==1){
                    getView().onMyNotes(noteBeans);

                }else{
                    getView().onMyNotesMore(noteBeans);
                }
            }
        });
    }

    @Override
    public void deleteNotes(String ids, int domType) {
        makeRequest(minePageApi.deleteNoteOrError(ids, domType), new BaseObserver<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean loginBean) {
                getView().onDeleteNotes();
            }
        });
    }

    @Override
    public void getPlayAuthByVid(String vid) {
        makeRequest(minePageApi.getPlayAuthByVid(vid), new BaseObserver<PlayAuthBean>() {
            @Override
            protected void onSuccess(PlayAuthBean playAuthBean) {
                if (TextUtils.isEmpty(playAuthBean.getPlayAuth())){
                    ToastUtil.show(fragment.getContext(),"获取播放凭证失败");
                    return;
                }
                getView().onPlayAuth(playAuthBean);
            }
        });
    }
}
