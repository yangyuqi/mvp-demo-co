package com.wxjz.module_aliyun.dialogFragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.adapter.VideoListAdapter;
import com.wxjz.module_base.bean.CourseOutlineBean;
import com.wxjz.module_base.bean.VideoInPlayPageBean;
import com.wxjz.module_base.util.ToastUtil;

import java.util.List;

/**
 * Created by a on 2019/10/12.
 */

@SuppressLint("ValidFragment")
public class VideoListDialog extends BaseDialog {
    private List<VideoInPlayPageBean.VideoListBean> mVideoList;
    private String currentVid;
    private VideoListAdapter videoListAdapter;
    private VideoInPlayPageBean.VideoListBean video;

    @SuppressLint("ValidFragment")
    public VideoListDialog(List<VideoInPlayPageBean.VideoListBean> mVideoList, String videoId) {
        this.mVideoList = mVideoList;
        this.currentVid = videoId;
    }

    public static VideoListDialog getInstance(List<VideoInPlayPageBean.VideoListBean> mVideoList, String videoId) {
        return new VideoListDialog(mVideoList, videoId);
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.video_list_dialog;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        initView(holder);
    }

    private void initView(ViewHolder holder) {
        RecyclerView rvVideo = (RecyclerView) holder.findView(R.id.rv_video);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvVideo.setLayoutManager(linearLayoutManager);
        videoListAdapter = new VideoListAdapter(R.layout.video_list_item, mVideoList, currentVid);
        rvVideo.setAdapter(videoListAdapter);

        videoListAdapter.setEnableLoadMore(true);
        videoListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                ((LandscapeVideoActivity) getContext()).loadMoreVideoList();
            }
        }, rvVideo);


        videoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                video = mVideoList.get(position);
                if (video.getDownload_status() == 0) {
                    //为下载
                    onItemClickListener.onItemClick(video);
                } else if (video.getDownload_status() == 1) {
                    //下载中
                    ToastUtil.show(getContext(), "该视频正在下载");
                } else {
                    //已下载
                    ToastUtil.show(getContext(), "该视频已下载");
                }
            }
        });
    }

    public void onLoadMoreVideo(List<VideoInPlayPageBean.VideoListBean> mNewVideolis) {
        if (videoListAdapter != null) {
            if (mNewVideolis.size() > 0) {
                videoListAdapter.addData(mNewVideolis);
                videoListAdapter.loadMoreComplete();
            } else {
                videoListAdapter.loadMoreEnd();
            }

        }
    }

    /**
     * 视频下载后 更新UI
     * @param currentSelectVideo
     */
    public void updateViewByDownloadingVideo(VideoInPlayPageBean.VideoListBean currentSelectVideo) {
        try {
            if (currentSelectVideo!=null){
                int indexOf = mVideoList.indexOf(video);
                video.setDownload_status(currentSelectVideo.getDownload_status());
                videoListAdapter.notifyItemChanged(indexOf);
            }
        }catch (Exception e){

        }


    }

    public interface OnItemClickListener {
        void onItemClick(VideoInPlayPageBean.VideoListBean downloadVideo);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
