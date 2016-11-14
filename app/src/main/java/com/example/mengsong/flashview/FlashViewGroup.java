package com.example.mengsong.flashview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 21150 on 2016/11/12.
 * <p/>
 * 使用说明：
 * 在布局中填控件
 * 获取实例
 * mFlashViewGroup = (FlashViewGroup) findViewById(R.id.flashviewgrop);
 * 设置数据
 * mFlashViewGroup.setSource(mImageViews);
 * 设置滚动间隔
 * mFlashViewGroup.setIntervalTime(3000);
 * 设置是否有小圆点
 * mFlashViewGroup.setHavePosition(true);
 * 开始轮播
 * mFlashViewGroup.start();
 * 是否滚动
 * mFlashViewGroup.setFly(true);
 *
 * 在activity的onDestory调用onDestory()
 *
 * 设置点击事件
 * mFlashViewGroup.setOnFlashItemClick(new FlashViewGroup.OnFlashItemClick() {
@Override
public void onItemClick(int item) {

}
});
 */
public class FlashViewGroup extends LinearLayout implements View.OnClickListener {

    private View mView;
    private FlashView mFlashView;
    private LinearLayout mLinearLayout;
    private List<CheckBox> mCheckBoxs = new ArrayList<>();
    private List<ImageView> mImageViews;
    private boolean isHavePosition = false;
    private Context mContext;
    private int currentP = 0;
    private double offset = 0;
    private OnFlashItemClick mFlashItemClick;


    public FlashViewGroup(Context context) {
        super(context);
        mContext = context;
        mView = View.inflate(context, R.layout.flashview_group, this);
    }

    public FlashViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mView = View.inflate(context, R.layout.flashview_group, this);
    }

    public void setHavePosition(boolean havePosition) {
        isHavePosition = havePosition;
    }

    public void setSource(List<ImageView> mImageViews) {
        this.mImageViews = mImageViews;
        mFlashView.setImageViews(mImageViews);
    }

    public void start() {
        if (isHavePosition) {
            initChecket();
        }
        mFlashView.startFilpper();
    }

    public void notifyDataChange() {
        mFlashView.getFlashAdapter().notifyDataSetChanged();
        mFlashView.setCurrentItem(0);
        mFlashView.setCurrentPosition(0);
        currentP = 0;
        mCheckBoxs.clear();
        initChecket();
    }

    public void onDestory() {
        mFlashView.onDestory();
    }

    public void setIntervalTime(int intervalTime) {
        mFlashView.setIntervalTime(intervalTime);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFlashView = (FlashView) mView.findViewById(R.id.flashview);
        mLinearLayout = (LinearLayout) mView.findViewById(R.id.ll_checkedbox);
    }

    private void initChecket() {

        for (int i = 0; i < mImageViews.size(); i++) {
            CheckBox checkBox = new CheckBox(mContext);
            LayoutParams params = new LayoutParams(15, 15);
            params.rightMargin = 10;
            checkBox.setLayoutParams(params);
            if (i == 0) {
                checkBox.setChecked(true);
            }
            checkBox.setBackgroundResource(R.drawable.select_flash_checkbox);
            mCheckBoxs.add(checkBox);
            mLinearLayout.addView(checkBox);
        }

        mFlashView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                offset = positionOffset;
                if (positionOffset == 0.0) {
                    mFlashView.setFly(true);
                }
            }

            @Override
            public void onPageSelected(int position) {
                int temp = position % mImageViews.size();
                mCheckBoxs.get(temp).setChecked(true);
                mCheckBoxs.get(currentP).setChecked(false);
                currentP = temp;
                mFlashView.setCurrentPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mFlashView.setFly(false);
                break;
            case MotionEvent.ACTION_UP:
                if (offset == 0.0) {
                    mFlashView.setFly(true);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    public void setOnFlashItemClick(OnFlashItemClick flashItemClick) {
        mFlashItemClick = flashItemClick;
        for (int i = 0; i < mImageViews.size(); i++) {
            mImageViews.get(i).setOnClickListener(this);
            mImageViews.get(i).setTag(i);
        }
    }

    public void setFly(boolean fly) {
        mFlashView.setFly(fly);
    }

    @Override
    public void onClick(View view) {
        int tag = (int) view.getTag();
        mFlashItemClick.onItemClick(tag);
    }

    public interface OnFlashItemClick {
        void onItemClick(int item);
    }
}
