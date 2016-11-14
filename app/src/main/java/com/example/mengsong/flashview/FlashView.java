package com.example.mengsong.flashview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 21150 on 2016/11/12.
 */
public class FlashView extends ViewPager {

    /**
     * 滚动时间间隔
     */
    private int intervalTime = 1000;
    /**
     * 图片集合
     */
    private List<ImageView> mImageViews;
    /**
     * 当前viewPager位置
     */
    private int currentPosition = 0;
    /**
     * 定时器
     */
    private Timer timer;
    /**
     * 适配器
     */
    private FlashAdapter mFlashAdapter;
    /**
     * 是否滚动
     */
    private boolean isFly = true;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                currentPosition++;
                setCurrentItem(currentPosition);
            }
        }
    };

    public FlashView(Context context) {
        super(context);
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public FlashAdapter getFlashAdapter() {
        return mFlashAdapter;
    }

    public void setFlashAdapter(FlashAdapter flashAdapter) {
        mFlashAdapter = flashAdapter;
    }

    public FlashView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置数据
     * @param imageViews
     */
    public void setImageViews(List<ImageView> imageViews) {
        mImageViews = imageViews;
    }

    /**
     * 设置滚动间隔
     * @param intervalTime
     */
    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    /**
     * activity销毁时调用，取消定时器
     */
    public void onDestory() {
        timer.cancel();
    }

    /**
     * 是否滚动，activity到后台时可以停止滚动
     * @param fly
     */
    public void setFly(boolean fly) {
        isFly = fly;
    }

    /**
     * 开始滚动
     */
    public void startFilpper() {
        mFlashAdapter = new FlashAdapter();
        setAdapter(mFlashAdapter);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isFly) {
                    mHandler.sendEmptyMessage(1);
                }
            }
        }, 0, intervalTime);
    }

    /**
     * 适配器
     */
    class FlashAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int temp = position % mImageViews.size();
            ViewGroup parent = (ViewGroup) mImageViews.get(temp).getParent();
            if (parent != null) {
                parent.removeView(mImageViews.get(temp));
            }
            container.addView(mImageViews.get(temp));
            return mImageViews.get(temp);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }
}
