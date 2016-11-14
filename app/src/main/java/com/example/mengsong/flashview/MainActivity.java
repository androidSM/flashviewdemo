package com.example.mengsong.flashview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FlashViewGroup mFlashViewGroup;
    private List<ImageView> mImageViews;
    private int[] start = {R.drawable.start1, R.drawable.start2, R.drawable.start3,R.mipmap.ic_launcher};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageViews = new ArrayList<>();

        for (int i = 0; i < start.length; i++) {
            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(start[i]);
            mImageViews.add(imageView);
        }
        mFlashViewGroup = (FlashViewGroup) findViewById(R.id.flashviewgrop);
        //轮播的数据
        mFlashViewGroup.setSource(mImageViews);
        //轮播间隔时间
        mFlashViewGroup.setIntervalTime(2000);
        //是否有下面的小点
        mFlashViewGroup.setHavePosition(true);
        //开始轮播
        mFlashViewGroup.start();
        //点击事件
        mFlashViewGroup.setOnFlashItemClick(new FlashViewGroup.OnFlashItemClick() {
            @Override
            public void onItemClick(int item) {
                Toast.makeText(MainActivity.this,"" + item,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFlashViewGroup.onDestory();
    }
}
