package com.wxjz.tenms_pad.adapter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadManager;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadMediaInfo;
import com.wxjz.module_aliyun.aliyun.view.download.AlivcDownloadMediaInfo;
import com.wxjz.module_base.util.FileUtil;
import com.wxjz.module_base.util.LogUtils;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.bean.AliyunMediaInfo;
import com.wxjz.tenms_pad.fragment.mine.DownloadManageFragment2;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by a on 2019/10/1.
 */

public class VideoDownloadProgressAdapter extends BaseQuickAdapter<AliyunMediaInfo.DownloadVideoMidiainfo, BaseViewHolder> {
    /**
     * 科目下视频选中状态集合
     */
    private List<Boolean> mCheckStatusList;
    private boolean checkAll;
    private DownloadManageFragment2 downloadManageFragment2;
    private List<AliyunMediaInfo.DownloadVideoMidiainfo> dataList = new ArrayList<>();
    private CourseDownloadProgressAdapter adapter;
    public VideoDownloadProgressAdapter(int layoutResId, @Nullable List<AliyunMediaInfo.DownloadVideoMidiainfo> data, DownloadManageFragment2 downloadManageFragment2,CourseDownloadProgressAdapter adapter) {
        super(layoutResId, data);
        this.downloadManageFragment2 = downloadManageFragment2;
        this.adapter = adapter;
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
    protected void convert(final BaseViewHolder helper, final AliyunMediaInfo.DownloadVideoMidiainfo item) {
        ImageView ivPlay = helper.getView(R.id.iv_play);
        ImageView iv_cover = helper.getView(R.id.iv_cover);
        ProgressBar progressBar = helper.getView(R.id.progress_download);
        TextView tv_video_size = helper.getView(R.id.tv_video_size);
        TextView tv_download_status = helper.getView(R.id.tv_download_status);
        TextView tv_download_size = helper.getView(R.id.tv_download_size);
        TextView tv_video_name = helper.getView(R.id.tv_video_name);
        CheckBox check_video = helper.getView(R.id.check_video);
        check_video.setOnCheckedChangeListener(null);
        Glide.with(mContext).load(item.getVideoCover()).error(R.drawable.example2).apply(RequestOptions.bitmapTransform(
                new RoundedCorners(5))).into(iv_cover);
        tv_video_name.setText(item.getmTitle());

        check_video.setChecked(mCheckStatusList.get(helper.getLayoutPosition()));
        if (checkAll) {
            check_video.setChecked(checkAll);
        }
        for (int i = 0; i < mCheckStatusList.size(); i++) {
            mCheckStatusList.set(i, checkAll);
        }
        downloadManageFragment2.setDeteleClickable(checkAll);
        check_video.setVisibility(checkboxVisible ? View.VISIBLE : View.GONE);
        check_video.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckStatusList.set(helper.getLayoutPosition(), isChecked);

                for (int i = 0; i < mCheckStatusList.size(); i++) {
                    if (i == mCheckStatusList.size() - 1) {
                        downloadManageFragment2.setDeteleClickable(false);
                    }
                    if (mCheckStatusList.get(i)) {
                        //有选中的
                        //courseDownloadProgressAdapter.setCheckStatus(tag, true);
                        downloadManageFragment2.setDeteleClickable(true);
                        break;

                    }


                }
                //没有选中的
                //courseDownloadProgressAdapter.setCheckStatus(tag, false);

            }
        });
        if (item.getmStatus().equals(AliyunDownloadMediaInfo.Status.Complete)) {
            ivPlay.setVisibility(View.GONE);
            tv_download_status.setText("下载完成");
            progressBar.setVisibility(View.GONE);
            tv_video_size.setVisibility(View.VISIBLE);
            tv_download_size.setText(FileUtil.getFormatSize(item.getmSize()));
            tv_video_size.setText(FileUtil.getFormatSize(item.getmSize()));
            item.setmStatus(AliyunDownloadMediaInfo.Status.Complete);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            ivPlay.setVisibility(View.VISIBLE);
            //tv_video_size.setVisibility(View.GONE);
            tv_download_size.setText(FileUtil.getFormatSize(item.getmSize()*item.getmProgress()/100));
            tv_video_size.setText(FileUtil.getFormatSize(item.getmSize()));
            if (item.getmStatus().equals(AliyunDownloadMediaInfo.Status.Stop)) {
                tv_download_status.setText("暂停中");
               // tv_download_status.setTextColor(mContext.getResources().getColor(R.color.black));

                progressBar.setProgress(item.getmProgress());
                ivPlay.setImageResource(R.drawable.stop);
            } else if (item.getmStatus().equals(AliyunDownloadMediaInfo.Status.Start)) {
                tv_download_status.setText("下载中");
                ivPlay.setImageResource(R.drawable.video_play);
                progressBar.setProgress(item.getmProgress());
             //   tv_download_status.setTextColor(mContext.getResources().getColor(R.color.grayBBBBBB));
                if (item.getmProgress() == 100) {
                    ivPlay.setVisibility(View.GONE);
                    tv_download_status.setText("下载完成");

                    progressBar.setVisibility(View.GONE);
//                    tv_video_size.setVisibility(View.VISIBLE);
//                    tv_video_size.setText(FileUtil.getFormatSize(item.getmSize()));
                    item.setmStatus(AliyunDownloadMediaInfo.Status.Complete);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();

                        }
                    },500);
                }
            }
        }


    }

    /**
     * 全选
     */
    public void checkAllItem(boolean checkAll) {
        this.checkAll = checkAll;
        if (mCheckStatusList != null) {
            for (int i = 0; i < mCheckStatusList.size(); i++) {
                mCheckStatusList.set(i, checkAll);
            }
        }
        notifyDataSetChanged();
    }

    private boolean checkboxVisible;

    public void setCheckboxVisible(boolean checkboxVisible) {
        this.checkboxVisible = checkboxVisible;
        notifyDataSetChanged();
    }

    public void deleteCheckedVideo(boolean deleteVideo, AliyunDownloadManager downloadManager) {
        if (!deleteVideo) {
            return;
        }

        for (int i = 0; i < mCheckStatusList.size(); i++) {
            if (mCheckStatusList.get(i)) {
                AliyunDownloadMediaInfo info = new AliyunDownloadMediaInfo();
                AliyunMediaInfo.DownloadVideoMidiainfo midiainfo = dataList.get(i);
                info.setVid(midiainfo.getmVid());
                info.setFormat(midiainfo.getmFormat());
                info.setQualityIndex(midiainfo.getmQualityIndex());
                info.setQuality(midiainfo.getmQuality());
                info.setSavePath(midiainfo.getmSavePath());
                downloadManager.deleteFile(info);
                Log.e("ql", "删一次" + midiainfo.getmTitle());
            }

        }
        downloadManageFragment2.deleteVideoFinish();

    }
}
