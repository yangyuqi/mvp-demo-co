package com.wxjz.tenms_pad.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.bean.CourseOutlineBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.VideoDetailBean;
import com.wxjz.module_base.constant.PermissionConstants;
import com.wxjz.module_base.event.OnFinishVideoEvent;
import com.wxjz.module_base.util.PermissionUtil;
import com.wxjz.module_base.util.SPCacheUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.CourseDetailActivity;
import com.wxjz.tenms_pad.activity.OfficeActivity;
import com.wxjz.tenms_pad.activity.PdfActivity;
import com.wxjz.tenms_pad.adapter.CatalogueAdapter;
import com.wxjz.tenms_pad.adapter.KeJianAdapter;
import com.wxjz.tenms_pad.mvp.contract.CatalogueFragmentContract;
import com.wxjz.tenms_pad.mvp.presenter.CatalogueFragmentPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by a on 2019/9/20.
 */

public class CatalogueFragment extends BaseMvpFragment<CatalogueFragmentPresenter> implements CatalogueFragmentContract.View {
    private int courseId;
    private RecyclerView rvOutLine;
    private String videoId;
    private String courseName;
    private String courseCover;
    private int id;
    private int PAGE = 1;
    private CatalogueAdapter catalogueAdapter;
    private int videoDuration;
    private long clicktime = 0;
    private long oldclicktime = 0;
    private LinearLayout llKj;
    private RelativeLayout rl_empty;


    public CatalogueFragment() {

    }

    @Override
    protected CatalogueFragmentPresenter createPresenter() {
        return new CatalogueFragmentPresenter();
    }

    @Override
    protected void initView(View view) {
        llKj = view.findViewById(R.id.llKJ);
        rl_empty = view.findViewById(R.id.rl_empty);
        rvOutLine = view.findViewById(R.id.rv_outline);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        ArrayList<VideoDetailBean.DetailBean.CoursewareListBean> kjList = arguments.getParcelableArrayList("kejian");
        setRl_emptyVisible(kjList.size() == 0);
        setKejainView(kjList);
        //  mPresenter.getVideoList(courseId, PAGE, 20);
    }

    private void setKejainView(final ArrayList<VideoDetailBean.DetailBean.CoursewareListBean> kjList) {
        if (kjList.size() == 0) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvOutLine.setLayoutManager(linearLayoutManager);
        KeJianAdapter keJianAdapter = new KeJianAdapter(R.layout.kejian_item, kjList);
        rvOutLine.setAdapter(keJianAdapter);
        keJianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                PermissionUtil.requestPermission(mContext,
                        new PermissionUtil.OnPermissionListener() {
                            @Override
                            public void onDenied() {

                            }

                            @Override
                            public void onGranted() {
                                VideoDetailBean.DetailBean.CoursewareListBean bean = kjList.get(position);

                                if (bean.getFileType().equalsIgnoreCase("word") || bean.getFileType().equalsIgnoreCase("ppt"))
                                {
                                    Intent intent = new Intent(mContext, OfficeActivity.class);
                                    intent.putExtra("title", bean.getCoursewareName());
                                    intent.putExtra("pdf_url", bean.getShowAddress());
                                    startActivity(intent);
                                }else{
                                    //pdf
                                    Intent intent = new Intent(mContext, PdfActivity.class);
                                    intent.putExtra("title", bean.getCoursewareName());
                                    intent.putExtra("pdf_url", bean.getShowAddress());
                                    startActivity(intent);
                                }

                            }
                        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }
        });
    }

    private void setRl_emptyVisible(boolean visible) {
        rl_empty.setVisibility(visible ? View.VISIBLE : View.GONE);
        llKj.setVisibility(visible ? View.GONE : View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_catalogue;
    }

    public static CatalogueFragment getInstance() {

        return new CatalogueFragment();
    }


    @Override
    public void onVideoList(final CourseOutlineBean courseOutlineBean) {
        final List<CourseOutlineBean.ListBean> list = courseOutlineBean.getList();
        if (list.size() == 0) {
            return;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvOutLine.setLayoutManager(layoutManager);

        catalogueAdapter = new CatalogueAdapter(R.layout.course_outline_item, list);
        catalogueAdapter.disableLoadMoreIfNotFullPage(rvOutLine);
        rvOutLine.setAdapter(catalogueAdapter);
        catalogueAdapter.setEnableLoadMore(true);
        catalogueAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getLoadMoreVideoList(courseId, ++PAGE, 20);
            }
        }, rvOutLine);
        catalogueAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                PermissionUtil.requestPermission(mContext, new PermissionUtil.OnPermissionListener() {
                    @Override
                    public void onDenied() {

                    }

                    @Override
                    public void onGranted() {
                        clicktime = System.currentTimeMillis();
                        if (clicktime - oldclicktime > 1000) {
                            oldclicktime = clicktime;
                            mPresenter.addVideoClickCount(list.get(position).getCourseId(), list.get(position).getId());
                            videoId = list.get(position).getVideoId();
                            mPresenter.getPlayAuthByVid(videoId);
                            id = list.get(position).getId();
                            videoDuration = list.get(position).getVideoDuration();
                        }
                    }
                }, PermissionConstants.getPermissionsFromGroup(PermissionConstants.STORAGE));

            }
        });
    }

    @Override
    public void onPlayAuth(PlayAuthBean playAuthBean) {
        HashSet<String> preferences = SPCacheUtil.getHashSetData(SPCacheUtil.COOKIES, null);
        if (preferences.size() == 0 || preferences == null) {
            ((CourseDetailActivity) mContext).showLoginTipsDialog();
        } else {
            Intent intent = new Intent(mContext, LandscapeVideoActivity.class);
            intent.putExtra("playAuth", playAuthBean.getPlayAuth());
            intent.putExtra("videoId", videoId);
            intent.putExtra("id", id);
            intent.putExtra("courseId", courseId);
            intent.putExtra("courseName", courseName);
            intent.putExtra("courseCover", courseCover);
            intent.putExtra("videoDuration", videoDuration);
            startActivity(intent);
        }


    }

    @Override
    public void onLoadMoreVideoList(CourseOutlineBean courseOutlineBean) {
        List<CourseOutlineBean.ListBean> list = courseOutlineBean.getList();
        if (catalogueAdapter != null) {
            if (list.size() > 0) {
                catalogueAdapter.setNewData(list);
                catalogueAdapter.loadMoreComplete();
                // PAGE++;
            } else {
                catalogueAdapter.loadMoreEnd();
            }
        }
    }

    @Override
    public boolean needEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishVideo(OnFinishVideoEvent event) {

        //  mPresenter.getVideoList(courseId, 1, 20);
    }
}
