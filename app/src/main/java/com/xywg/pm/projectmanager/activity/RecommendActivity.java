package com.xywg.pm.projectmanager.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.base.BaseActivity;
import com.example.common.base.BasePresenter;
import com.example.common.sutils.utils.GlideUtil;
import com.example.common.sutils.utils.QRCodeUtil;
import com.example.common.sutils.utils.ScreenUtil;
import com.example.common.widget.view.SquareImageView;
import com.tongju.share.util.ShareUtil;
import com.xywg.pm.projectmanager.R;
import com.xywg.pm.projectmanager.constant.Const;
import com.xywg.pm.projectmanager.constant.RouterPath;
import com.xywg.pm.projectmanager.view.MyHeadView;

import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by zhangyinlei on 2018/7/17
 */
@Route(path = RouterPath.ROUTER_RECOMMEND)
public class RecommendActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mLQQ, mLWechat;
    private SquareImageView mErCode;

    private String ShareUrl = "http://download.sdk.mob.com/web/images/2018/07/30/15/1532934002507/1024_1024_23.93.png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void findViews() {
        MyHeadView myHeadView = findViewById(R.id.head_view);
        myHeadView.setLeftIconClickListerner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mErCode = findViewById(R.id.iv_ercode);
        mLQQ = findViewById(R.id.ll_qq);
        mLQQ.setOnClickListener(this);
        mLWechat = findViewById(R.id.ll_wechat);
        mLWechat.setOnClickListener(this);
    }

    @Override
    protected void init() {
        int width = ScreenUtil.getScreenSize().x;
        Bitmap bitmap = QRCodeUtil.createQRCode(Const.Constant.shareUrl, width, width);
        GlideUtil.loadImageView(RecommendActivity.this, bitmap, mErCode);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_qq) {
            ShareUtil.showShare(this
                    , QQ.NAME
                    , "快来加入项目管理平台吧～"
                    , "项目管理平台是一款集多功能为一体的智慧管理平台"
                    , Const.Constant.shareUrl
                    , ShareUrl);
        } else if (id == R.id.ll_wechat) {
            ShareUtil.showShare(this
                    , Wechat.NAME
                    , "快来加入项目管理平台吧～"
                    , "项目管理平台是一款集多功能为一体的智慧管理平台"
                    , Const.Constant.shareUrl
                    , ShareUrl);
        }
    }

}
