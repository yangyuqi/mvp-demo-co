package com.wxjz.tenms_pad.adapter;

import android.app.DownloadManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.constants.PlayParameter;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadManager;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadMediaInfo;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.bean.AliyunMediaInfo;
import com.wxjz.tenms_pad.fragment.mine.DownloadManageFragment2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a on 2019/9/30.
 */

public class CourseDownloadProgressAdapter extends BaseQuickAdapter<AliyunMediaInfo, BaseViewHolder> {
    private AliyunDownloadManager downloadManager;
    /**
     * 科目选中状态集合
     */
    private List<Boolean> mCheckStatusList;
    // private boolean checkAll;
    private DownloadManageFragment2 downloadManageFragment2;
    private List<AliyunMediaInfo> dataList = new ArrayList<>();
    private Map<Integer, VideoDownloadProgressAdapter> adapterMap = new HashMap<>();
    private boolean deleteVideo;


    public CourseDownloadProgressAdapter(int layoutResId, @Nullable List<AliyunMediaInfo> data, AliyunDownloadManager downloadManager, DownloadManageFragment2 downloadManageFragment2) {
        super(layoutResId, data);
        this.downloadManager = downloadManager;
        this.downloadManageFragment2 = downloadManageFragment2;
        dataList.addAll(data);
        if (data != null && data.size() > 0) {
            mCheckStatusList = new ArrayList<>(data.size());
            //默认状态是未选中
            for (int i = 0; i < data.size(); i++) {
                mCheckStatusList.add(i, false);
            }
        }

    }

    @Override
    protected void convert(final BaseViewHolder helper, final AliyunMediaInfo item) {
        final CheckBox check_course = helper.getView(R.id.check_course);


        helper.setText(R.id.tv_course_name, item.getSectionName());
        final List<AliyunMediaInfo.DownloadVideoMidiainfo> downloadVideoMidiainfos = item.getDownloadVideoMidiainfos();
        int hasDone = 0;
        int downloading = 0;
        for (AliyunMediaInfo.DownloadVideoMidiainfo midiainfo : downloadVideoMidiainfos) {
            if (midiainfo.getmStatus() == AliyunDownloadMediaInfo.Status.Complete) {
                ++hasDone;
            } else {
                ++downloading;
            }
        }
        helper.setText(R.id.tv_done, String.valueOf(hasDone));
        helper.setText(R.id.tv_downloading, String.valueOf(downloading));
        helper.setText(R.id.tv_total, String.valueOf(downloadVideoMidiainfos.size()));
        RecyclerView rv_video = helper.getView(R.id.rv_video_download);
        final boolean[] visible = {false};
        helper.getView(R.id.ll_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewVisible(helper, !visible[0]);
                visible[0] = !visible[0];
            }
        });
        final VideoDownloadProgressAdapter adapter;
        int index = dataList.indexOf(item);
        if (adapterMap.get(index) == null) {
            adapter = new VideoDownloadProgressAdapter(R.layout.layout_download_video_item, downloadVideoMidiainfos, downloadManageFragment2, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            rv_video.setLayoutManager(linearLayoutManager);
            rv_video.setAdapter(adapter);
            adapterMap.put(index, adapter);

        } else {
            adapter = adapterMap.get(index);

        }
        if (deleteVideo) {
            adapter.deleteCheckedVideo(deleteVideo, downloadManager);

        }

        check_course.setOnCheckedChangeListener(null);
        check_course.setChecked(mCheckStatusList.get(helper.getLayoutPosition()));
        adapter.checkAllItem(mCheckStatusList.get(helper.getLayoutPosition()));
        adapter.setCheckboxVisible(checkboxVisible);
        check_course.setVisibility(checkboxVisible ? View.VISIBLE : View.GONE);
        check_course.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.checkAllItem(isChecked);
                downloadManageFragment2.setDeteleClickable(isChecked);
                mCheckStatusList.set(helper.getLayoutPosition(), isChecked);
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //当checkbox可见的时候条目不可点击
                if (getCheckboxVisible(check_course)) {
                    return;
                }
                AliyunMediaInfo.DownloadVideoMidiainfo midiainfo = downloadVideoMidiainfos.get(position);
                AliyunDownloadMediaInfo.Status status = midiainfo.getmStatus();
                if (status == AliyunDownloadMediaInfo.Status.Start) {
                    //点击后暂停下载
                    downloadManager.stopDownload(transfromData(item, midiainfo));
                    downloadManager.stopDownload(transfromData(item, midiainfo));
                    midiainfo.setmStatus(AliyunDownloadMediaInfo.Status.Stop);
                    adapter.notifyItemChanged(position);

                } else if (status == AliyunDownloadMediaInfo.Status.Stop) {
                    //点击后开始下载
                    downloadManager.startDownload(transfromData(item, midiainfo));
                    midiainfo.setmStatus(AliyunDownloadMediaInfo.Status.Start);
                    adapter.notifyItemChanged(position);
                } else if (status == AliyunDownloadMediaInfo.Status.Complete) {
                    //点击后跳转播放
                    // 如果点击列表中的视频, 需要将类型改为vid
                    PlayParameter.PLAY_PARAM_TYPE = "localSource";
                    if (midiainfo != null) {
                        PlayParameter.PLAY_PARAM_URL = midiainfo.getmSavePath();
                        if (!TextUtils.isEmpty(midiainfo.getVideoId())) {
                            int id = Integer.valueOf(midiainfo.getVideoId());
                            JumpUtil.jump2VideoActivity(mContext, LandscapeVideoActivity.class, PlayParameter.PLAY_PARAM_URL, midiainfo.getmTitle(), id);
                        }

                    }

                }
            }
        });
    }

    private AliyunDownloadMediaInfo transfromData(AliyunMediaInfo item, AliyunMediaInfo.DownloadVideoMidiainfo midiainfo) {
        AliyunDownloadMediaInfo mediaInfo = new AliyunDownloadMediaInfo();
        mediaInfo.setVid(midiainfo.getmVid());
        mediaInfo.setQuality(midiainfo.getmQuality());
        mediaInfo.setTitle(midiainfo.getmTitle());
        mediaInfo.setVideoCover(midiainfo.getVideoCover());
        mediaInfo.setDuration(midiainfo.getmDuration());
        mediaInfo.setTrackInfo(midiainfo.getmTrackInfo());
        mediaInfo.setQualityIndex(midiainfo.getmQualityIndex());
        mediaInfo.setVideoId(midiainfo.getVideoId());
        mediaInfo.setFormat(midiainfo.getmFormat());
        mediaInfo.setSize(midiainfo.getmSize());
        mediaInfo.setStatus(midiainfo.getmStatus());
        mediaInfo.setmVidAuth(midiainfo.getmVidAuth());

        return mediaInfo;
    }

    private void setViewVisible(BaseViewHolder helper, boolean visible) {
        helper.getView(R.id.rv_video_download).setVisibility(visible ? View.VISIBLE : View.GONE);
        if (visible) {
            helper.getView(R.id.iv_open).setBackgroundResource(R.drawable.shouqi);
            ((TextView) helper.getView(R.id.tv_open)).setText("收起");
        } else {
            helper.getView(R.id.iv_open).setBackgroundResource(R.drawable.zhankai);
            ((TextView) helper.getView(R.id.tv_open)).setText("展开");
        }
    }

    /**
     * 全选
     */
    public void checkAllItem(boolean checkAll) {
        if (mCheckStatusList != null) {
            for (int i = 0; i < mCheckStatusList.size(); i++) {
                mCheckStatusList.set(i, checkAll);
            }
        }
        notifyDataSetChanged();
    }

    private boolean getCheckboxVisible(CheckBox checkBox) {
        int visibility = checkBox.getVisibility();
        if (visibility == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }


    private boolean checkboxVisible;

    public void setCheckboxVisible(boolean visible) {
        this.checkboxVisible = visible;
        for (int i = 0; i < mCheckStatusList.size(); i++) {
            mCheckStatusList.set(i, false);
        }
        notifyDataSetChanged();
    }

    public void deleteCheckedVideo(boolean deleteVideo) {
        this.deleteVideo = deleteVideo;
        notifyDataSetChanged();
    }

    public void notifyItemChangedVideo(int courseIndexOf, int videoIndex) {
        try {
            adapterMap.get(courseIndexOf).notifyItemChanged(videoIndex);

        } catch (Exception e) {

        }
    }


}
