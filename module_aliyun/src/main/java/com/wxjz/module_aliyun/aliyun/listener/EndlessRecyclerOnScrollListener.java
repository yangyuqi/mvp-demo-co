package com.wxjz.module_aliyun.aliyun.listener;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/7/31
 * Time: 9:44
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    /**
     * 用来标记是否是向上滑动
     */
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        //当不滑动
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //获取最后一条完全显示的itemposition
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();
            //判断是否滑动到最后一个item
            if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                //加载更多
                onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyVisibleItemPosition <= 0) {
            firstitemNotSee();
        } else {
            firstitemCanSee();

        }
        isSlidingUpward = dy > 0;
        Log.d("LF123", "dy = " + dy);
    }

    /**
     * 加载更多回调
     */

    public abstract void onLoadMore();

    public abstract void firstitemCanSee();

    public abstract void firstitemNotSee();

}
