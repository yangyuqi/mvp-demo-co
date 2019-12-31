package com.wxjz.tenms_pad.mvp.presenter;

import android.text.TextUtils;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.ErrorProblemBean;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.http.api.MinePageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.module_base.util.ToastUtil;
import com.wxjz.tenms_pad.fragment.mine.FragmentInMyError;
import com.wxjz.tenms_pad.mvp.contract.FragmentInMyErrorContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2019/9/18.
 */

public class FragmentInMyErrorPresenter extends BasePresenter<FragmentInMyErrorContract.View> implements FragmentInMyErrorContract.Presenter {
    private FragmentInMyError mFragment;
    private final MinePageApi minePageApi;

    public FragmentInMyErrorPresenter(FragmentInMyError fragmentInMyError) {
        minePageApi = create(MinePageApi.class);
        this.mFragment = fragmentInMyError;
    }

    @Override
    public void getErrorProblem(String userId, Integer subId, int domType, final int page, int rows, int stuLevelId) {
        makeRequest(minePageApi.getMyErrorProblem(userId, subId, domType, page, rows, stuLevelId), new BaseObserver<List<ErrorProblemBean>>() {
            @Override
            protected void onSuccess(List<ErrorProblemBean> problemBeans) {
                if (problemBeans == null) {
                    problemBeans = new ArrayList<>();
                }
                if (page == 1) {
                    getView().onErrorProblem(problemBeans);

                } else {
                    getView().onErrorProblemMore(problemBeans);

                }

            }

        });
    }

    @Override
    public void deleteError(String id, int domType) {
        makeRequest(minePageApi.deleteNoteOrError(id, domType), new BaseObserver<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean loginBean) {
                getView().onDeleteError();
            }
        });
    }

    @Override
    public void getPlayAuthByVid(String vid) {
        makeRequest(minePageApi.getPlayAuthByVid(vid), new BaseObserver<PlayAuthBean>() {
            @Override
            protected void onSuccess(PlayAuthBean playAuthBean) {
                if (TextUtils.isEmpty(playAuthBean.getPlayAuth())) {
                    ToastUtil.show(mFragment.getContext(), "获取播放凭证失败");
                } else {
                    getView().onPlayAuth(playAuthBean);
                }
            }
        });
    }
}
