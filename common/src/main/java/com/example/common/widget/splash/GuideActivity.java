package com.example.common.widget.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.common.R;
import com.example.common.adapter.ViewPagerAdapter;
import com.example.common.base.BaseActivity;
import com.example.common.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 * Created by zhangyinlei on 2018/3/20 16:51
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager inviewpager;
    private LinearLayout inll;

    private int[] imageId = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private List<View> viewList;

    //实例化原点View
    private ImageView[] ivPointArray;

    private View lastView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        setContentView(R.layout.activity_guide);
    }

    @Override
    protected void findViews() {
        this.inll = (LinearLayout) findViewById(R.id.in_ll);
        this.inviewpager = (ViewPager) findViewById(R.id.in_viewpager);
    }

    @Override
    protected void init() {
        //加载ViewPager
        initViewPager();

        //加载底部圆点
        initPoint();
    }

    public void initViewPager() {
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //循环创建View并加入到集合中
        int len = imageId.length;
        for (int i = 0; i < len; i++) {
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundResource(imageId[i]);
            //将ImageView加入到集合中
            viewList.add(imageView);
        }
        lastView = LayoutInflater.from(this).inflate(R.layout.view_guide_last, null);
        viewList.add(lastView);
        //View集合初始化好后，设置Adapter
        inviewpager.setAdapter(new ViewPagerAdapter(viewList));
        //设置滑动监听
        inviewpager.addOnPageChangeListener(this);
    }

    public void initPoint() {
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0; i < size; i++) {
            ImageView iv_point = new ImageView(this);
            iv_point.setLayoutParams(new ViewGroup.LayoutParams(70, 18));
            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                iv_point.setBackgroundResource(R.drawable.shape_guide_point_rectangle_choose);
            } else {
                iv_point.setBackgroundResource(R.drawable.shape_guide_point_rectangle_un_choose);
            }
            //将数组中的ImageView加入到ViewGroup
            inll.addView(ivPointArray[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
        int length = viewList.size();
        for (int i = 0; i < length; i++) {
            ivPointArray[position].setBackgroundResource(R.drawable.shape_guide_point_rectangle_choose);
            if (position != i) {
                ivPointArray[i].setBackgroundResource(R.drawable.shape_guide_point_rectangle_un_choose);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

}
